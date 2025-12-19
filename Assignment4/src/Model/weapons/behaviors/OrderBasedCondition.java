/*
    Weapon condition that checks if numbers were selected in ascending or descending order.
    Used by Frost Bow (ascending) and Diamond Sword (descending).
*/
package Model.weapons.behaviors;
import Model.Fill;

public class OrderBasedCondition implements WeaponCondition {
    private final boolean checkAscedning;

    public OrderBasedCondition(boolean checkAscedning) {
        this.checkAscedning = checkAscedning;
    }

    @Override
    public boolean isActivated(Fill fill) {
        return checkAscedning ? fill.isAscendingOrder() : fill.isDescendingOrder();
    }
}
