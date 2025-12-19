/*
    Weapon condition that checks fill completion time.
    Used by Lightning Wand (10s) and Sparkle Dagger (20s).
*/
package Model.weapons.behaviors;
import Model.Fill;

public class TimeBasedCondition implements WeaponCondition {
    private final int fillTime;

    public TimeBasedCondition(int fillTime) {
        this.fillTime = fillTime;
    }

    @Override
    public boolean isActivated(Fill fill) {
        return fill.getFillTime() >= fillTime;
    }
}
