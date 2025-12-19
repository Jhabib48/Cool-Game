/*
    The class handles the case where a ring is activated
    by the condition that the fill strenght is the power of 2
*/
package Model.rings;
import Model.Fill;

public class PowerOfTwoCondition implements RingCondition {
    @Override
    public boolean isActivated(Fill fill) {
        if (fill == null) return false;
        int strength = fill.getStrength();
        return strength > 0 && (strength & (strength - 1)) == 0;
    }
}
