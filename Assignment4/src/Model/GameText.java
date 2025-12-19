/*
    Holds the simple printing/System for the app
*/
package Model;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameText {
    public static final String WEAPON_EQUIPPED = "Weapon equipped: ";
    public static final String RINGS_EQUIPPED = "Rings equipped: ";
    public static final String GAME_OVER = "Game Over!";
    public static final String INVALID_COMMAND = "Invalid command. Use: number, gear, cheat, stats, or quit";
    public static final String UNKNOWN_CHEAT = "Unknown cheat command: ";
    public static final String CHEAT_ERROR = "Error executing cheat: ";

    // Error Messages
    public static final String WEAPON_ID_REQUIRED = "Weapon ID required";
    public static final String THREE_RING_IDS_REQUIRED = "Three ring IDs required";
    public static final String MAX_VALUE_REQUIRED = "Max value required";

    // Equipment Display
    public static final String WEAPON_LABEL = "Weapon: ";
    public static final String RINGS_LABEL = "Rings (";
    public static final String RINGS_SLASH = "/3):";
    public static final String NO_RINGS = "  None";

    // game model strings
    public static final String RANDOM_ENEMY_ATTACK = "Random enemy attack!";
    public static final String ENEMY_ATTACKED_FOR = "Enemy attacked for ";
    public static final String PLAYER_DEFEATED = "Player is defeated! Game over";
    public static final String NO_MATCHING_CELL = "ERROR: No matching cell Found";
    public static final String SUCCESSFULLY_HANDLED_CELL = "Successfully handled cell ";
    public static final String FILLED = " Filled.";
    public static final String BONUS_DAMAGE = "% bonus damage";
    public static final String FILL_COMPLETE_STRENGTH = "Fill complete! Strength is: ";
    public static final String MATCH_WIN = "Match win!";
    public static final String NEW_MATCH_START = "new match started!";
    public static final String ENEMIES_LOW_HEALTH_MSG = "All Enemies now have low health: ";
    public static final String ENEMIES_HIGH_HEALTH_MSG = "All Enemies now have high health: ";
    public static final String MAX_VALUE_LESS_THAN_1 = "Max value is less than 1";
    public static final String MAX_GRID_VALUE_SET = "Max grid value is set to: ";
    public static final String MATCH_ENDED_CHEAT = "Match ended via cheat - counts as loss";
    public static final String EQUIPPED_WEAPON = "Equipped: ";
    public static final String EQUIPPED_RING = "Equipped ring: ";
    public static final String DISCARDED_RING = "Discarded the ring";
    public static final String KEPT_CURRENT_WEAPON = "Kept current weapon";
    public static final String ADDS = " adds ";
    public static final String ACTIVATED = "activated";
    public static final String MISSED_CHARACTER_ALREADY_DEFEATED = "Missed %s character (already defeated)";
    public static final String BASIC_ATTACK = "Basic attack";
    public static final String ALL_RINGS_REMOVE = "All rings removed";

    ///  Empty string used to prevent duplicate messageas
    public static final String DO_NOTHING = "";


    public static String getWeaponDescription(String weaponName) {
        return switch (weaponName) {
            case "Lightning Wand" -> "Activates if fill completed in <10s - targets additional random opponent";
            case "Fire Staff" -> "Activates if fill has 15+ cells - hits main + adjacent targets";
            case "Frost Bow" -> "Activates if numbers selected in ascending order - hits all opponents";
            case "Stone Hammer" -> "Activates if fill has 10+ cells - hits all opponents at 80%";
            case "Diamond Sword" -> "Activates if numbers selected in descending order - hits main + sides at 75%";
            case "Sparkle Dagger" -> "Activates if fill completed in <20s - targets additional random opponent at 50%";
            default -> "No description available";
        };
    }

    public static String getRingDescription(String ringName) {
        return switch (ringName) {
            case "The Big One" -> "50% bonus if strength ≥ 160";
            case "The Little One" -> "50% bonus if strength ≤ 90";
            case "Ring of Ten-acity" -> "50% bonus if strength % 10 == 0";
            case "Ring of Meh" -> "10% bonus if strength % 5 == 0";
            case "The Prime Directive" -> "100% bonus if strength is prime";
            case "The Two Ring" -> "1000% bonus if strength is power of 2";
            default -> "No description available";
        };
    }

    public static final List<String> WEAPONS = Arrays.asList(
            "Lightning Wand", "Fire Staff", "Frost Bow",
            "Stone Hammer", "Diamond Sword", "Sparkle Dagger"
    );

    public static final List<String> RINGS = Arrays.asList(
            "The Big One", "The Little One", "Ring of Ten-acity",
            "Ring of Meh", "The Prime Directive", "The Two Ring"
    );

    public static String formatMatches(int won, int lost) {
        return String.format(
                "Matches%n%-20s %3d%n%-20s %3d",
                "Won",
                won,
                "Lost",
                lost);
    }

    public static String formatTotalDamage(int dealt, int received) {
        return String.format(
                "Total Damage%n%-20s %,d%n%-20s %,d",
                "Done",
                dealt,
                "Received",
                received
        );
    }
    public static String formatFillsCompleted(int fills) {
        return String.format("Fills Completed: %d", fills);
    }

    public static String formatEquipmentActivations(Map<String, Integer> activations) {
        StringBuilder sb = new StringBuilder("Equipment Activations\n");
        activations.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> sb.append(String.format("%-20s %3d%n",
                        entry.getKey(), entry.getValue())));
        return sb.toString();
    }

    public static String formatFullStatistics(StatisticTracker stats) {
        return formatEquipmentActivations(stats.getEquipmentActivations()) + "\n" +
                formatMatches(stats.getMatchesWon(), stats.getMatchesLost()) + "\n" +
                formatTotalDamage(stats.getTotalDamageDealt(), stats.getTotalDamageReceived()) + "\n" +
                formatFillsCompleted(stats.getFillsCompleted());
    }

    public static String getStatisticsErrorMessage() {
        return "Statistics not available";
    }

    public static String formatMessage(String message) {
        return message;
    }
}