/*
    Interface for modifying weapon damage.
    Calculates final damage based on target position and main target.
*/
package Model.weapons.behaviors;
import Model.Position;

public interface DamageModifier {
    int getDamageMultiplier(int baseDamage, Position.PositionType target, Position.PositionType mainTarget);
}
