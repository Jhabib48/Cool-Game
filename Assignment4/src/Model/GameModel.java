/*
    The core game engine that manages all game logic and state.
    Handles player moves, enemy attacks, fill completion, equipment
    systems, and cheat commands. Implements the Observer pattern
    to notify UI of game events. Coordinates between player, opponents,
    game board, and fill mechanics.
*/
package Model;
import Model.rings.*;
import Model.weapons.GameWeapon;
import Model.weapons.NullWeapon;
import Model.weapons.Weapon;
import Model.weapons.behaviors.*;
import java.util.*;

public class GameModel {
    private List<GameObserver> observers = new ArrayList<>();
    private Player player;
    private OpponentTeam opponents;
    private GameBoard board;
    private Fill fill;
    private java.util.concurrent.ThreadLocalRandom random = java.util.concurrent.ThreadLocalRandom.current();

    // Position
    private final Position.PositionType LEFT_POSITION = Position.PositionType.LEFT;
    private final Position.PositionType RIGHT_POSITION = Position.PositionType.RIGHT;
    private final Position.PositionType MIDDLE_POSITION = Position.PositionType.MIDDLE;

    // Health
    private final int OPPONENTS_HEALTH = 50;
    private final int OPPONENTS_DAMAGE= 15;

    private static final boolean INITIAL_DEFEAT_STATUS = false;

    // Cheat health
    private final int LOW_HEALTH = 10;
    private final int HIGH_HEALTH = 200;

    // keep track of turns
    private int turnCount = 0;

    // random turn attacks
    private static final int MIN_ATTACK_TURNS = 3;
    private static final int MAX_ATTACK_TURNS = 5;

    private StatisticTracker stats;
    private boolean alternate = false;

    public Player getPlayer() {return player;}
    public OpponentTeam getOpponents() {return opponents;}
    public GameBoard getBoard() {return board;}
    public Fill getFill() {return fill;}

    public GameModel() {
        this.board = new GameBoard();
        this.fill = new Fill();
        this.player = new Player(200, new NullWeapon());
        this.stats = new StatisticTracker();
        this.addObserver(stats);
        initOpponents();
    }

    private void initOpponents() {
        List<Opponent> opponentsList = Arrays.asList(
                new Opponent(OPPONENTS_HEALTH, OPPONENTS_DAMAGE, INITIAL_DEFEAT_STATUS, LEFT_POSITION),
                new Opponent(OPPONENTS_HEALTH, OPPONENTS_DAMAGE, INITIAL_DEFEAT_STATUS, MIDDLE_POSITION),
                new Opponent(OPPONENTS_HEALTH, OPPONENTS_DAMAGE, INITIAL_DEFEAT_STATUS, RIGHT_POSITION)
        );

        // Create position map
        Map<Position.PositionType, Opponent> opponentMap = new HashMap<>();
        for (Opponent opponent : opponentsList) {
            opponentMap.put(opponent.getPosition(), opponent);
        }
        this.opponents = new OpponentTeam(opponentsList, opponentMap);
    }

    public void handlePlayerMove(int sum) {
        turnCount++;

        if (shouldEnemyAttack()) {
            notifyGameMessage(GameText.RANDOM_ENEMY_ATTACK);
            handleFailedMove();
        }

        if (board.isValidMove(sum, fill)) {
            handleSuccessfulMove(sum);
        }
    }

    // Attack every 3-5 turns randomly
    private boolean shouldEnemyAttack() {
        int attackInterval = randomBetween();
        return turnCount > 0 && turnCount % attackInterval == 0;
    }

    private int randomBetween() {
        return random.nextInt(MAX_ATTACK_TURNS
                                    - MIN_ATTACK_TURNS + 1)
                                    + MIN_ATTACK_TURNS;
    }

    private void handleFailedMove() {
        Optional<Opponent> opponent = opponents.getRandomOpponent();

        if(opponent.isPresent()){
            int damage = opponent.get().attackDamage();
            player.takeDamage(damage);
            notifyEnemyAttack(opponent.get(), damage);
            notifyGameMessage( GameText.ENEMY_ATTACKED_FOR + damage);
        }

        if(player.isDefeated()){
            notifyGameMessage(GameText.PLAYER_DEFEATED);
        }
    }

    private void handleSuccessfulMove(int sum) {
        int center = board.getCenterValue();

        for(int i = 0; i < 8; i++){
            if(!fill.isCellFilled(i)
                    && (center + board.getOuterValue(i) == sum)){
                handelMatchedCell(i, board.getOuterValue(i));
                return;
            }
        }
        notifyGameMessage(GameText.NO_MATCHING_CELL);
    }

    private void handelMatchedCell(int cellIndex, int outerValue) {
        fill.addCell(cellIndex, outerValue);
        board.updateCenterCell(outerValue);
        board.updateCell(cellIndex, board.getRandomValue());

        if(fill.isComplete()){
            handelFillComplete();
        }

        notifyGameMessage( GameText.SUCCESSFULLY_HANDLED_CELL + (cellIndex + 1) + GameText.FILLED);
    }

    private void handelFillComplete() {
        int baseDamage = fill.getStrength();

        //Ring damage
        double ringMultiplier = 1.0;
        List<String> ringActivationMessages = new ArrayList<>();
        for (Ring ring : player.getRings()) {
            double ringBonus = ring.getDamageMultiplier(fill);
            if (ringBonus > 1.0) {
                ringMultiplier *= ringBonus;
                ringActivationMessages.add(ring.getName() +
                        GameText.ADDS
                        + ((int)((ringBonus-1)*100))
                        + GameText.BONUS_DAMAGE);
                notifyEquipmentActivation(ring.getName());
            }
        }

        int modifiedBaseDamage = (int) Math.round(baseDamage * ringMultiplier);
        for (String message : ringActivationMessages) {
            notifyGameMessage(message);
        }

        Weapon currentWeapon = player.getEquippedWeapon();
        AttackResult attack = player.getEquippedWeapon().calculateAttack(fill, modifiedBaseDamage, opponents);
        boolean weaponActivated = attack.getMessages()
                                        .stream()
                                        .anyMatch(msg -> msg.contains(GameText.ACTIVATED));
        if (weaponActivated) {
            notifyEquipmentActivation(currentWeapon.getName());
        }

        if (attack.getTotalDamage() == 0) {
            attack = new AttackResult();
            Optional<Position.PositionType> mainTarget = fill.getFinalCellPosition();
            if (mainTarget.isPresent()) {
                attack.addDamage(mainTarget, modifiedBaseDamage, GameText.BASIC_ATTACK);
            }
        }

        applyDamageToOpponent(attack);

        notifyAttack(attack);
        notifyGameMessage(GameText.FILL_COMPLETE_STRENGTH + fill.getStrength() + ".");

        if(opponents.isDefeated()){
            handleMatchWin();
        }

        fill = new Fill();
    }

    private void handleMatchWin() {
        notifyGameMessage(GameText.MATCH_WIN);

        alternate = !alternate;

        // Change from Object to Equipment
        Equipment newItem = alternate ? generateRandomWeapon() : generateRandomRing();

        offerEquipmentToPlayer(newItem);
        startNewMatch();
    }

    private void startNewMatch() {
        initOpponents();
        board = new GameBoard();
        fill = new Fill();
    }

    private Weapon generateRandomWeapon() {
        return createWeaponById(random.nextInt(6));
    }

    private Weapon createWeaponById(int weaponIndex) {
        return switch (weaponIndex) {
            case 0 -> new GameWeapon("Lightning Wand", new TimeBasedCondition(10000), new RandomTarget(), new PercentageDamage(1.0));
            case 1 -> new GameWeapon("Fire Staff", new SizeBasedCondition(15), new AdjacentTargets(), new PercentageDamage(1.0));
            case 2 -> new GameWeapon("Frost Bow", new OrderBasedCondition(true), new AllTargets(), new PercentageDamage(1.0));
            case 3 -> new GameWeapon("Stone Hammer", new SizeBasedCondition(10), new AllTargets(), new PercentageDamage(0.8));
            case 4 -> new GameWeapon("Diamond Sword", new OrderBasedCondition(false), new AdjacentTargets(), new MainPlusSideDamage());
            case 5 -> new GameWeapon("Sparkle Dagger", new TimeBasedCondition(20000), new RandomTarget(), new PercentageDamage(0.5));
            default -> throw new IllegalArgumentException("Invalid weapon ID: " + (weaponIndex + 1));
        };
    }

    public void cheatWeapon(int weaponId) {
        Weapon weapon = createWeaponById(weaponId - 1);
        player.setEquippedWeapon(weapon);
        notifyGameMessage(GameText.WEAPON_EQUIPPED + weapon.getName());
    }

    private Ring generateRandomRing() {
        return createRingById(random.nextInt(6));  // Use helper
    }

    private Ring createRingById(int ringIndex) {
        return switch (ringIndex) {
            case 0 -> new GameRing("The Big One", new StrengthCondition(160, true), 1.5);
            case 1 -> new GameRing("The Little One", new StrengthCondition(90, false), 1.5);
            case 2 -> new GameRing("Ring of Ten-acity", new ModuloCondition(10), 1.5);
            case 3 -> new GameRing("Ring of Meh", new ModuloCondition(5), 1.1);
            case 4 -> new GameRing("The Prime Directive", new PrimeCondition(), 2.0);
            case 5 -> new GameRing("The Two Ring", new PowerOfTwoCondition(), 11.0);
            default -> throw new IllegalArgumentException("Invalid ring ID: " + (ringIndex + 1));
        };
    }

    public void cheatRings(int ring1, int ring2, int ring3) {
        List<Ring> newRings = new ArrayList<>();
        List<String> ringNames = new ArrayList<>();

        if (ring1 != 0) {
            Ring ring = createRingById(ring1 - 1);
            newRings.add(ring);
            ringNames.add(ring.getName());
        }
        if (ring2 != 0) {
            Ring ring = createRingById(ring2 - 1);
            newRings.add(ring);
            ringNames.add(ring.getName());
        }
        if (ring3 != 0) {
            Ring ring = createRingById(ring3 - 1);
            newRings.add(ring);
            ringNames.add(ring.getName());
        }

        player.setRings(newRings);
        if (ringNames.isEmpty()) {
            notifyGameMessage(GameText.ALL_RINGS_REMOVE);
        } else {
            notifyGameMessage(GameText.RINGS_EQUIPPED + String.join(", ", ringNames));
        }
    }

    private void applyDamageToOpponent(AttackResult attack) {
        List<TargetDamage> damageEvents = new ArrayList<>(attack.getDamageResult());

        for(TargetDamage damageEvent : damageEvents){
            if(damageEvent.getPosition().isPresent()){
                Position.PositionType target = damageEvent.getPosition().get();
                boolean hit = opponents.applyDamage(target, damageEvent.getDamageAmount());

                if (!hit) {
                    attack.addMiss(target, String.format(GameText.MISSED_CHARACTER_ALREADY_DEFEATED, target));
                }
            }
        }
    }

    public void cheatLowHealth() {
        for(Opponent opponent: opponents.getOpponents()){
            opponent.setHealth(LOW_HEALTH);
        }
        notifyGameMessage(GameText.ENEMIES_LOW_HEALTH_MSG + LOW_HEALTH);
    }

    public void cheatHighHealth() {
        if(opponents.getOpponents().size() < 2) return;
        for(Opponent opponent: opponents.getOpponents()){
            opponent.setHealth(HIGH_HEALTH);
        }
        notifyGameMessage(GameText.ENEMIES_HIGH_HEALTH_MSG + HIGH_HEALTH);
    }

    public void cheatMaxValue(int max) {
        if(max < 1){
            notifyGameMessage(GameText.MAX_VALUE_LESS_THAN_1);
            return;
        }
        board.setMaxValue(max);
        notifyGameMessage(GameText.MAX_GRID_VALUE_SET + max);
    }

    public void cheatNewMatch() {
        notifyGameMessage(GameText.MATCH_ENDED_CHEAT);
        startNewMatch();
        notifyGameMessage(GameText.NEW_MATCH_START);
    }

    public void equipWeapon(Weapon newWeapon) {
        player.setEquippedWeapon(newWeapon);
        notifyGameMessage(GameText.EQUIPPED_WEAPON + newWeapon.getName());
    }

    private void offerEquipmentToPlayer(Equipment newEquipment) {
        EquipmentChoiceHandler choiceHandler = new EquipmentChoiceHandler(this);
        newEquipment.accept(choiceHandler, player);
    }

    public void equipRing(Ring newRing, int slotIndex) {
        List<Ring> rings = player.getRings();
        if (slotIndex == -1) {
            rings.add(newRing);
        } else {
            rings.set(slotIndex, newRing);
        }

        notifyGameMessage(GameText.EQUIPPED_RING + newRing.getName());
    }

    public void discardRing() {
        notifyGameMessage(GameText.DISCARDED_RING);
    }

    public void keepCurrentWeapon() {notifyGameMessage(GameText.KEPT_CURRENT_WEAPON);}

    public boolean isGameOver(){return opponents.isDefeated() || player.isDefeated();}

    public void addObserver(GameObserver observer){
        observers.add(observer);
    }

    public void notifyAttack(AttackResult result){
        observers.forEach(observer -> observer.onAttack(result));
    }

    public void notifyEnemyAttack(Opponent opponent, int damage){observers.forEach(observer -> observer.onEnemyAttack(opponent, damage));}

    public void notifyGameMessage(String messages){observers.forEach(observer -> observer.onGameMessage(messages));}
    
    public StatisticTracker getStatisticTracker() {
        return this.stats;
    }

    public void notifyWeaponChoice(Weapon equippedWeapon, Weapon weapon) {observers.forEach(observer -> observer.onWeaponChoice(equippedWeapon, weapon));}

    public void notifyRingChoice(Ring ring, List<Ring> rings) {observers.forEach(observer -> observer.onRingChoice(ring, rings));}

    private void notifyEquipmentActivation(String name) {observers.forEach(observer -> observer.onEquipmentActivation(name));}
}
