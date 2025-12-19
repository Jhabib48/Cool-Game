/*
    A simple interface for cheat commands.
    Each cheat (like "lowhealth", "weapon", "stats") has its own handler
    method that takes the game model and command parts, and returns a result message.
 */
package UI;
import Model.GameModel;

@FunctionalInterface
public interface CheatHandler {
    String handle(GameModel model, String[] parts) throws Exception;
}
