/*
    Weapon condition that checks if fill has minimum cell count.
    Used by Fire Staff (15+) and Stone Hammer (10+).
*/
package Model.weapons.behaviors;
import Model.Fill;

public class SizeBasedCondition implements WeaponCondition {
    private final int size;

    public SizeBasedCondition(int size) {
        this.size = size;
    }

    @Override
    public boolean isActivated(Fill fill) {
        return fill.getCellsAdded() >= size;
    }
}
