/*
    The program starter that runs the game loop. It handles user commands like numbers, "gear", "cheat", and "stats".
    Processes cheat commands and connects the game model with the text view.
    Contains the main game loop that keeps the game running until it's over
 */
package UI;
import Model.GameModel;
import Model.GameText;
import Model.StatisticTracker;
import Model.rings.Ring;
import Model.weapons.Weapon;
import java.util.*;

public class Main {
    private static final Map<String, CheatHandler> CHEAT_HANDLERS;
    private static StatisticTracker stats;

    /// do nothing is a placeholder to String that prints nothing
    ///  it's to prevent duplicate string returns
    static {
        CHEAT_HANDLERS = Map.of("lowhealth", (model, parts) -> {
            model.cheatLowHealth();
            return GameText.DO_NOTHING;

        }, "highhealth", (model, parts) -> {
            model.cheatHighHealth();
            return GameText.DO_NOTHING;

        }, "weapon", Main::handleWeaponCheat,
                "rings", Main::handleRingsCheat,
                "max", Main::handleMaxCheat,
                "stats", (model, parts) -> {
            handleStatsCommand(model);
            return GameText.DO_NOTHING;

        }, "new", (model, parts) -> {
            model.cheatNewMatch();
            return GameText.DO_NOTHING;
        });
    }

    public static void main(String[] args) {
        GameModel model = new GameModel();
        TextView view = new TextView(model);

        stats = model.getStatisticTracker();
        model.addObserver(view);

        Scanner scanner = new Scanner(System.in);
        while (!model.isGameOver()) {
            view.displayGameState(model.getPlayer(), model.getOpponents(),
                    model.getBoard(), model.getFill());

            String input = scanner.nextLine().trim();

            if (input.equals("quit")) break;
            else if (input.equals("gear")) handleGearCommand(model);
            else if (input.startsWith("cheat ")) handleCheatCommand(model, input.substring(6));
            else if (input.equals("stats")) handleStatsCommand(model);
            else if (input.equals("new")) {
                model.cheatNewMatch();
            }
            else {
                try {
                    int sum = Integer.parseInt(input);
                    model.handlePlayerMove(sum);
                } catch (NumberFormatException e) {
                    System.out.println(GameText.INVALID_COMMAND);
                }
            }
        }

        System.out.println(GameText.GAME_OVER);
        scanner.close();
    }

    // Cheat Command Handler
    private static void handleCheatCommand(GameModel model, String cheatCommand) {
        String[] parts = cheatCommand.split(" ");
        CheatHandler handler = CHEAT_HANDLERS.get(parts[0]);

        if (handler != null) {
            try {
                String result = handler.handle(model, parts);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(GameText.CHEAT_ERROR + e.getMessage());
            }
        } else {
            System.out.println(GameText.UNKNOWN_CHEAT + parts[0]);
        }
    }

    private static String handleWeaponCheat(GameModel model, String[] parts) {
        if (parts.length < 2) throw new IllegalArgumentException(GameText.WEAPON_ID_REQUIRED);
        int weaponId = Integer.parseInt(parts[1]);
        model.cheatWeapon(weaponId);
        ///  prevent duplicate code
        return GameText.DO_NOTHING;
    }

    private static String handleRingsCheat(GameModel model, String[] parts) {
        if (parts.length < 4)
            throw new IllegalArgumentException(GameText.THREE_RING_IDS_REQUIRED);

        int r1 = Integer.parseInt(parts[1]);
        int r2 = Integer.parseInt(parts[2]);
        int r3 = Integer.parseInt(parts[3]);

        model.cheatRings(r1, r2, r3);
        ///  return no String to prevent duplicated code.
        return GameText.DO_NOTHING;
    }

    private static String handleMaxCheat(GameModel model, String[] parts) {
        if (parts.length < 2) throw new IllegalArgumentException(GameText.MAX_VALUE_REQUIRED);
        int max = Integer.parseInt(parts[1]);
        model.cheatMaxValue(max);
        return "Max grid value set to " + max;

    }

    private static void handleStatsCommand(GameModel model) {
        StatisticTracker stats = model.getStatisticTracker();
        if (stats == null) {
            System.out.println( GameText.getStatisticsErrorMessage());
            return;
        }

        // statistic
        System.out.println(GameText.formatFullStatistics(stats));
    }

    private static void handleGearCommand(GameModel model) {
        Weapon weapon = model.getPlayer().getEquippedWeapon();
        if (weapon != null) {
            System.out.println(GameText.WEAPON_LABEL + weapon.getName());
            System.out.println("  " + GameText.getWeaponDescription(weapon.getName()));
        } else {
            System.out.println(GameText.formatMessage("Weapon: None"));
        }

        List<Ring> rings = model.getPlayer().getRings();
        System.out.println(GameText.RINGS_LABEL + rings.size() + GameText.RINGS_SLASH);
        if (rings.isEmpty()) {
            System.out.println(GameText.NO_RINGS);
        } else {
            for (int i = 0; i < rings.size(); i++) {
                Ring ring = rings.get(i);
                System.out.println("  " + (i + 1) + ". " + ring.getName() +
                        " - " + GameText.getRingDescription(ring.getName()));
            }
        }
    }
}