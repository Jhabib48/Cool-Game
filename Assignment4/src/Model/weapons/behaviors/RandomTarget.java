/*
    Targeting behavior that adds a random living opponent to the attack.
    Used by Lightning Wand and Sparkle Dagger for extra targets.
*/
package Model.weapons.behaviors;
import Model.OpponentTeam;
import Model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTarget implements TargetingBehavior {
    private final Random random = new Random();

    @Override
    public List<Position.PositionType> getTargets(Position.PositionType mainTarget, OpponentTeam opponents) {
        List<Position.PositionType> targets = new ArrayList<>();
        targets.add(mainTarget);

        List<Position.PositionType> livingPosition = new ArrayList<>();
        for(Position.PositionType position : Position.PositionType.values()){
            if(opponents.getTarget(position).isPresent() &&
                    !opponents.getTarget(position).get().isDefeated()){
                livingPosition.add(position);
            }
        }

        if(!livingPosition.isEmpty()) {
            Position.PositionType randomTarget =
                    livingPosition.get(
                    random.nextInt(livingPosition.size()));
            targets.add(randomTarget);
        }
        return targets;
    }
}
