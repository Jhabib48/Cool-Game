/*
    Interface for weapon activation conditions.
    Checks if a weapon's special effect should trigger.
*/
package Model.weapons.behaviors;
import Model.Fill;

public interface WeaponCondition {
    boolean isActivated(Fill fill);
}
