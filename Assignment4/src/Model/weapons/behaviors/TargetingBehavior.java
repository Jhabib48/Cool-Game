/*
    Interface for weapon targeting patterns.
    Determines which opponent positions get hit by an attack.
*/
package Model.weapons.behaviors;
import Model.OpponentTeam;
import Model.Position;
import java.util.List;

public interface TargetingBehavior {
    List<Position.PositionType> getTargets(Position.PositionType mainTarget, OpponentTeam opponents);

}
