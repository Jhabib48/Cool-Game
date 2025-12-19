/*
    interface for ring activation conditions.
    Just checks if a ring should activate based on the current fill's properties.
*/
package Model.rings;
import Model.Fill;

public interface RingCondition {
    boolean isActivated(Fill fill);
}
