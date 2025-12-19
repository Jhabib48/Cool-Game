/*
    Targeting behavior that only hits the main target.
    Basic attack pattern without extra targets.
*/
package Model.weapons.behaviors;
import Model.OpponentTeam;
import Model.Position;
import java.util.List;

public class SingleTarget implements TargetingBehavior{
    @Override
    public List<Position.PositionType> getTargets(Position.PositionType mainTarget, OpponentTeam opponents) {
        return List.of(mainTarget);
    }
}
