/*
    Manages the team of three opponent characters.
    Provides methods to get opponents by position, check if all are defeated,
    get random living opponents, and apply damage to specific positions.
    Uses both list and map structures for efficient position-based lookups and team operations.
*/

package Model;
import java.util.*;

public class OpponentTeam {
    private List <Opponent> opponents;
    private Map <Position.PositionType, Opponent> opponentMap;

    public OpponentTeam(List<Opponent> opponents, Map<Position.PositionType, Opponent> opponentMap) {
        this.opponents = opponents;
        this.opponentMap = opponentMap;
    }

    public Optional<Opponent > getTarget(Position.PositionType position) {
        Opponent opponent = opponentMap.get(position);
        return Optional.ofNullable(opponent);
    }

    public boolean isDefeated() {
        for(Opponent opponent : opponents) {
            if(!opponent.isDefeated()) {
                return false;
            }
        }
        return true;
    }

    public Optional<Opponent> getRandomOpponent() {
        List<Opponent> livingOpponents = opponents.stream()
                                        .filter(opponent -> !opponent.isDefeated())
                                        .toList();

        if(livingOpponents.isEmpty()) {
            return Optional.empty();
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(livingOpponents.size());
        return Optional.of(livingOpponents.get(randomIndex));
    }

    public boolean applyDamage(Position.PositionType position, int damageAmount) {
        if(position == null) return false;

        Opponent opponent = opponentMap.get(position);
        if(opponent == null || opponent.isDefeated()) {
            return false;
        }

        opponent.takeDamage(damageAmount);
        return true;
    }

    public List<Opponent> getOpponents() {return opponents;}
}
