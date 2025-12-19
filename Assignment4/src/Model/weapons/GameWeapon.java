/*
    A weapon composed of condition, targeting, and damage behaviors.
    Calculates attacks by checking activation, determining targets, and applying damage modifiers.
*/
package Model.weapons;
import Model.*;
import Model.weapons.behaviors.DamageModifier;
import Model.weapons.behaviors.TargetingBehavior;
import Model.weapons.behaviors.WeaponCondition;
import java.util.List;
import java.util.Optional;

public class GameWeapon implements Weapon {
    private String name;
    private WeaponCondition condition;
    private TargetingBehavior  targetingBehavior;
    private DamageModifier damageModifier;

    public GameWeapon(String name, WeaponCondition  condition, TargetingBehavior targetingBehavior, DamageModifier damageModifier) {
        this.name = name;
        this.condition = condition;
        this.targetingBehavior = targetingBehavior;
        this.damageModifier = damageModifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public AttackResult calculateAttack(Fill fill, int baseDamage, OpponentTeam opponents) {
        AttackResult result = new AttackResult();

        if (condition.isActivated(fill)) {
            Optional<Position.PositionType> mainTargetOpt = fill.getFinalCellPosition();
            if (mainTargetOpt.isPresent()) {
                Position.PositionType mainTarget = mainTargetOpt.get();
                List<Position.PositionType> targets = targetingBehavior.getTargets(mainTarget, opponents);

                for (Position.PositionType target : targets) {
                    int damage = damageModifier.getDamageMultiplier(baseDamage, target, mainTarget);
                    result.addDamage(Optional.ofNullable(target), damage, name + " activates!");
                }

                result.addMessage(name + " activated!");
            }
        }

        return result;
    }
}

