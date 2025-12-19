/*
    Checks if fill strength meets a minimum or maximum threshold.
    Used by "The Big One" (≥160) and The Little One (≤90) rings.
*/
package Model.rings;
import Model.Fill;

public class StrengthCondition implements RingCondition {
    private final int threshold;
    private final boolean isMinimum;

    public StrengthCondition(int threshold, boolean isMinimum) {
        this.threshold = threshold;
        this.isMinimum = isMinimum;
    }

    @Override
    public boolean isActivated(Fill fill) {
        return isMinimum ?
                fill.getStrength() >= threshold:
                fill.getStrength() <= threshold;
    }
}
