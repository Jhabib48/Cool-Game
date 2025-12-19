/*
    Represents a single damage event with target position, damage amount, and message.
    Supports both hits and misses, with methods to create miss events for already defeated
    opponents. Used by AttackResult to track individual damage instances.
*/
package Model;
import java.util.Optional;

public class TargetDamage {
    private Optional<Position.PositionType> position;
    private int damageAmount;
    private String message;
    private boolean wasMiss;

    public TargetDamage(Optional<Position.PositionType> position, int damageAmount, String message){
        this.position = position;
        this.damageAmount = damageAmount;
        this.wasMiss = false;
        this.message = message;
    }
    public static TargetDamage createMiss(Position.PositionType target, String message) {
        TargetDamage miss = new TargetDamage(Optional.of(target), 0, message);
        miss.setWasMiss(true);
        return miss;
    }

    public boolean isWasMiss() {return wasMiss;}
    public void setWasMiss(boolean wasMiss) {this.wasMiss = wasMiss;}
    public String getMessage() {return message;}
    public int getDamageAmount() {return damageAmount;}
    public Optional<Position.PositionType> getPosition() {return position;}
}
