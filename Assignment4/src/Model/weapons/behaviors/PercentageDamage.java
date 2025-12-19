/*
    Damage modifier that applies a fixed percentage to all targets.
    Used by Stone Hammer (80%) and Sparkle Dagger (50%).
*/
package Model.weapons.behaviors;
import Model.Position;

public class PercentageDamage implements DamageModifier{
    private final double percentage;

    public PercentageDamage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public int getDamageMultiplier(int baseDamage, Position.PositionType target, Position.PositionType mainTarget) {
        return (int)Math.round(baseDamage * percentage );

    }
}
