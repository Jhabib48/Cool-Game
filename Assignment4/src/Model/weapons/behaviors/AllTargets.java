/*
    Weapon behavior that hits all three opponents.
    Used by Frost Bow and Stone Hammer to attack every enemy.
*/
package Model.weapons.behaviors;
import Model.OpponentTeam;
import Model.Position;
import java.util.List;

public class AllTargets implements TargetingBehavior {
    @Override
    public List<Position.PositionType> getTargets(Position.PositionType mainTarget, OpponentTeam opponents) {
       return List.of(Position.PositionType.LEFT, Position.PositionType.MIDDLE, Position.PositionType.RIGHT);
    }
}
