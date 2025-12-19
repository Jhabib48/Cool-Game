/*
    interface for handling different types of equipment.
    Defines separate visit methods for weapons and rings, allowing type-specific
    processing when players interact with equipment. Enables polymorphic equipment
    handling without type checking or casting in client code.
*/
package Model;
import Model.rings.Ring;
import Model.weapons.Weapon;

public interface EquipmentVisitor {
    void visit(Weapon weapon, Player player);
    void visit(Ring ring, Player player);
}
