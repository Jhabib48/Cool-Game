/*
    Routes weapon choices to weapon-specific notification and ring choices to ring-specific
    notification in the game model. Acts as a bridge between equipment discovery and the UI
    presentation for equipment selection decisions.
*/
package Model;
import Model.rings.Ring;
import Model.weapons.Weapon;

public class EquipmentChoiceHandler implements EquipmentVisitor {
    private final GameModel model;

    public EquipmentChoiceHandler(GameModel model) {
        this.model = model;
    }

    @Override
    public void visit(Weapon weapon, Player player) {
        model.notifyWeaponChoice(player.getEquippedWeapon(), weapon);
    }

    @Override
    public void visit(Ring ring, Player player) {
        model.notifyRingChoice(ring, player.getRings());
    }
}
