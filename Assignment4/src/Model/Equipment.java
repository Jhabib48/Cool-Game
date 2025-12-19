/*
    Base interface for all game equipment items including weapons and rings.
    Defines the common contract that all equipment must implement: providing a name,
    equipment logic when equipped on a player, descriptive text
*/
package Model;

public interface Equipment {
    String getName();
    String getDescription();
    void accept(EquipmentVisitor visitor, Player player);
}
