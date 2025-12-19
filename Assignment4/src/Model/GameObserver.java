/*
    Observer pattern interface for receiving game events.
    Defines callback methods for attacks, fill completion,
    enemy attacks, game messages, and equipment choices.
    Allows UI components to react to game state changes
    without tight coupling to the game model.
*/
package Model;
import Model.rings.Ring;
import Model.weapons.Weapon;
import java.util.List;

public interface GameObserver {
    void onAttack(AttackResult result);
    void onEnemyAttack(Opponent opponent, int damage);
    void onGameMessage(String message);
    void onWeaponChoice(Weapon currentWeapon, Weapon newWeapon);
    void onRingChoice(Ring ring, List<Ring> currentRings);
    void onEquipmentActivation(String equipmentName);
}
