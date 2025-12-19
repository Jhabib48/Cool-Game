/*
    Tracks game statistics using the Observer pattern.
    Monitors equipment activations, matches won/lost, damage dealt/received,
    and fills completed. Initializes counters for all weapons and rings and extracts
    equipment names from game messages to track activations. Provides getters for statistic display.
*/
package Model;
import Model.rings.Ring;
import Model.weapons.Weapon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticTracker implements GameObserver{
    private Map<String, Integer> equipmentActivations = new HashMap<>();
    private int matchesWon = 0;
    private int matchesLost = 0;
    private int totalDamageDealt = 0;
    private int totalDamageReceived = 0;
    private int fillsCompleted = 0;

    public StatisticTracker() {initEquipmentActivations();}

    private void initEquipmentActivations() {
        // Initialize all weapons/rings
        for (String weapon : GameText.WEAPONS) {
            equipmentActivations.put(weapon, 0);
        }

        for (String ring : GameText.RINGS) {
            equipmentActivations.put(ring, 0);
        }
    }

    @Override
    public void onAttack(AttackResult result) {
        totalDamageDealt += result.getTotalDamage();
    }

    @Override
    public void onEnemyAttack(Opponent opponent, int damage) {totalDamageReceived += damage;}

    @Override
    public void onGameMessage(String message) {
        if (message.contains("Match win!")) {
            matchesWon++;
        } else if (
                message.contains("defeated") ||
                message.contains("Game over") ||
                message.contains("counts as loss")) {
            matchesLost++;
        }
    }

    @Override
    public void onWeaponChoice(Weapon currentWeapon, Weapon newWeapon) {}

    @Override
    public void onRingChoice(Ring ring, List<Ring> currentRings) {}

    @Override
    public void onEquipmentActivation(String equipmentName) {equipmentActivations.merge(equipmentName, 1, Integer::sum);}

    public Map<String, Integer> getEquipmentActivations() { return equipmentActivations; }
    public int getMatchesWon() { return matchesWon; }
    public int getMatchesLost() { return matchesLost; }
    public int getTotalDamageDealt() { return totalDamageDealt; }
    public int getTotalDamageReceived() { return totalDamageReceived; }
    public int getFillsCompleted() {return fillsCompleted;}
}
