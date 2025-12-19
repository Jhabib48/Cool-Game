/*
    Interface for all rings.
    Defines that rings must provide a damage multiplier based on the
    current fill and implements visitor pattern for equipment handling.
*/
package Model.rings;
import Model.Equipment;
import Model.EquipmentVisitor;
import Model.Fill;
import Model.Player;

public interface Ring extends Equipment {
    String getName();
    double getDamageMultiplier(Fill fill);

    @Override
    default String getDescription() {
        return Model.GameText.getRingDescription(getName());
    }

    @Override
    default void accept(EquipmentVisitor visitor, Player player) {
        visitor.visit(this, player);
    }
}
