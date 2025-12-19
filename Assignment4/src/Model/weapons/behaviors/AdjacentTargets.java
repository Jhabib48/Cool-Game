/*
   Weapon behavior that hits the main target plus adjacent opponents.
   Used by Fire Staff and Diamond Sword to attack left/right neighbors.
*/
package Model.weapons.behaviors;
import Model.OpponentTeam;
import Model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdjacentTargets implements TargetingBehavior{
    @Override
    public List<Position.PositionType> getTargets(Position.PositionType mainTarget, OpponentTeam opponents) {
        List<Position.PositionType> targets = new ArrayList<>();
        targets.add(mainTarget);

        // Get left
        getLeftPosition(mainTarget).ifPresent(targets::add);

        // Get right
        getRightPosition(mainTarget).ifPresent(targets::add);
        return targets;
    }

    private Optional<Position.PositionType> getRightPosition(Position.PositionType mainPosition) {
        return switch (mainPosition){
            case LEFT -> Optional.of(Position.PositionType.MIDDLE);
            case MIDDLE -> Optional.of(Position.PositionType.RIGHT);
            case RIGHT -> Optional.empty();
        };
    }

    private Optional<Position.PositionType> getLeftPosition(Position.PositionType mainPosition) {
        return switch (mainPosition){
            case RIGHT -> Optional.of(Position.PositionType.MIDDLE);
            case MIDDLE -> Optional.of(Position.PositionType.LEFT);
            case LEFT -> Optional.empty();
        };
    }
}
