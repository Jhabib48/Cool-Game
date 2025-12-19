/*
    This class handel the class of Module condition for the ring.
    The ring is activated if the fill strength mod is 0
*/
package Model.rings;
import Model.Fill;

public class ModuloCondition implements RingCondition {
    private final int MODULO;

    public ModuloCondition(int modulo) {this.MODULO = modulo;}

    @Override
    public boolean isActivated(Fill fill) {
        if(fill == null)return false;
        return fill.getStrength() % MODULO == 0;
    }
}
