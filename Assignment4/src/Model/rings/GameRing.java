/*
    A ring item that gives damage bonuses.
    Each ring has a condition (like strength being prime or divisible by 10) and a damage multiplier.
    Checks if its condition is met during attacks and applies bonus damage if activated.
 */
package Model.rings;
import Model.Fill;
import Model.Player;

public class GameRing implements Ring{
    private String name;
    private RingCondition condition;
    private double multiplier;

    public GameRing(String name, RingCondition condition, double multiplier) {
        this.name = name;
        this.condition = condition;
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {return name;}

    @Override
    public String getDescription() {return "";}

    @Override
    public double getDamageMultiplier(Fill fill) {
        return condition.isActivated(fill) ? multiplier : 1;
    }

}
