/*
    The main UI class that displays the game to the player.
    It shows the game board, player/opponent health, and handles equipment choices.
    It receives game events like attacks and fill completions and displays them as text.
 */
package UI;
import Model.*;
import Model.rings.Ring;
import Model.weapons.Weapon;
import java.util.List;
import java.util.Scanner;

public class TextView  implements GameObserver {
    private GameModel model;

    public TextView(GameModel model) {
        this.model = model;
    }

    @Override
    public void onAttack(AttackResult result) {
        for (TargetDamage damage : result.getDamageResult()) {
            if (damage.isWasMiss()) {
                System.out.println(damage.getMessage());
            } else {
                System.out.println(damage.getMessage()
                                    + " - "
                                    + damage.getDamageAmount()
                                    + " damage");
            }
        }

        for (String message : result.getMessages()) {
            System.out.println(message);
        }
    }

    @Override
    public void onEnemyAttack(Opponent opponent, int damage) {
        System.out.println(opponent.getPosition()
                + " Opponent attacks for "
                +  damage
                + " Damage"
        );
    }

    @Override
    public void onGameMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onWeaponChoice(Weapon currentWeapon, Weapon newWeapon) {
        System.out.println("You found: " + newWeapon.getName());
        System.out.println("   " + newWeapon.getDescription());

        Scanner scanner = new Scanner(System.in);
        if (currentWeapon.hasWeaponEquipped()) {
            System.out.println("Current: " + currentWeapon.getName());
            System.out.println("   " + currentWeapon.getDescription());
            System.out.print("Replace current weapon? (y/n): ");
        } else {
            System.out.print("Equip this weapon? (y/n): ");
        }

        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
            model.equipWeapon(newWeapon);
        } else {
            model.keepCurrentWeapon();
        }
    }

    @Override
    public void onRingChoice(Ring newRing, List<Ring> currentRings) {
        System.out.println("You found: " + newRing.getName());
        System.out.println("   " + newRing.getDescription());

        Scanner scanner = new Scanner(System.in);
        if (currentRings.size() < 3) {
            System.out.println("You have " + currentRings.size() + "/3 rings equipped");
            System.out.print("Equip this ring? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y") || choice.equals("yes")) {
                model.equipRing(newRing, -1);
            } else {
                model.discardRing();
            }
        } else {
            System.out.println("Your ring slots are full. Choose which ring to replace:");
            for (int i = 0; i < currentRings.size(); i++) {
                System.out.println((i+1) + ". " + currentRings.get(i).getName() +
                        " - " + currentRings.get(i).getDescription());
            }

            System.out.println("4. Discard the new ring");
            System.out.print("Choose (1-4): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 3) {
                    model.equipRing(newRing, choice - 1);
                } else if (choice == 4) {
                    model.discardRing();
                } else {
                    System.out.println("Invalid choice, discarding ring");
                    model.discardRing();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, discarding ring");
                model.discardRing();
            }
        }
    }

    @Override
    public void onEquipmentActivation(String equipmentName) {

    }

    public void displayGameState(Player player, OpponentTeam opponents, GameBoard gameBoard, Fill fill) {
        // Opponent health row — shifted right by 2 spaces
        System.out.printf(
                "       [%3d]   [%3d]   [%3d]%n%n",
                getOpponentHealth(opponents, Position.PositionType.LEFT),
                getOpponentHealth(opponents, Position.PositionType.MIDDLE),
                getOpponentHealth(opponents, Position.PositionType.RIGHT)
        );

        // TOP ROW
        System.out.printf(
                "        %-7s %-7s %-7s%n",
                formatCell(gameBoard, fill, 0),
                formatCell(gameBoard, fill, 1),
                formatCell(gameBoard, fill, 2)
        );

        // MIDDLE ROW (center perfectly aligned)
        System.out.printf(
                "        %-7s %-7s %-7s%n",
                formatCell(gameBoard, fill, 7),
                formatCenter(gameBoard.getCenterValue()),
                formatCell(gameBoard, fill, 3)
        );

        // BOTTOM ROW
        System.out.printf(
                "        %-7s %-7s %-7s%n",
                formatCell(gameBoard, fill, 6),
                formatCell(gameBoard, fill, 5),
                formatCell(gameBoard, fill, 4)
        );

        System.out.println();

        // Bottom health line — shifted right
        System.out.printf(
                "               [%3d]      Fill Strength: %d%n",
                player.getHealth(),
                fill.getStrength()
        );

        System.out.print("Enter a sum: ");
    }

    private int getOpponentHealth(OpponentTeam opponents, Position.PositionType type) {
        return opponents.getTarget(type)
                .map(Opponent::getHealth)
                .orElse(0);
    }

    private String formatCenter(int value) {
        return String.format("%4d", value);
    }

    private String formatCell(GameBoard gameBoard, Fill fill, int index) {
        String raw = String.valueOf(gameBoard.getOuterValue(index));

        if (fill.isCellFilled(index)) {
            raw = "_" + raw + "_";
        }

        if (raw.length() >= 4) return raw;
        return String.format("%4s", raw);
    }
}
