/*
    Implements the Null Object pattern for when no weapon is equipped.
    Provides basic single-target attack behavior without special effects.
    Returns "No Weapon" as its name and handles basic attack calculations
    by targeting only the main opponent position with unmodified base damage.
    Ensures the game functions normally even without an equipped weapon.
*/
package Model.weapons;
import Model.*;
import java.util.Optional;

public class NullWeapon implements Weapon {
    @Override
    public String getName() {
        return "No Weapon";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean hasWeaponEquipped() {
        return false;
    }

    @Override
    public AttackResult calculateAttack(Fill fill, int baseDamage, OpponentTeam opponents) {
        AttackResult result = new AttackResult();

        Optional<Position.PositionType> mainTarget = fill.getFinalCellPosition();
        if (mainTarget.isPresent()) {
            result.addDamage(mainTarget, baseDamage, "Basic attack");
        } else {
            result.addMessage("No valid target for attack");
        }

        return result;
    }
}
