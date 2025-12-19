/*
    Damage modifier that gives 100% damage to main target and 75% to side targets.
    Used by Diamond Sword.
*/
package Model.weapons.behaviors;
import Model.Position;

public class MainPlusSideDamage implements DamageModifier{
    @Override
    public int getDamageMultiplier(int baseDamage, Position.PositionType target, Position.PositionType mainTarget) {
        // increase based on position
        double multiplier = (target == mainTarget) ? 1.0 : 0.75;
        return (int) Math.round(baseDamage * multiplier);
    }
}
