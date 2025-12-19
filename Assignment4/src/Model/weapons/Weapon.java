/*
    Interface for all weapons in the game, extending the Equipment base interface.
    Defines the core weapon behavior including attack calculation that takes the current
    fill state, base damage, and opponent team to produce an AttackResult. Provides default
    implementations for equipment description retrieval via GameText.
    Includes a default method to indicate a weapon is equipped, which is overridden by NullWeapon for the no-weapon state.
*/
package Model.weapons;
import Model.*;

public interface Weapon  extends Equipment {
    String getName();
    AttackResult calculateAttack(Fill fill, int baseDamage, OpponentTeam opponents );

    @Override
    default String getDescription() {
        return Model.GameText.getWeaponDescription(getName());
    }

    @Override
    default void accept(EquipmentVisitor visitor, Player player) {
        visitor.visit(this, player);
    }

    default boolean hasWeaponEquipped() {
        return true;
    }
}
