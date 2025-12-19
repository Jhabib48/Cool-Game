/*
    stores the complete results of a weapon attack.
    Tracks damage dealt to specific opponent positions, attack messages for display,
    and calculates total damage. Maintains separate collections for individual damage
    events (TargetDamage objects)
*/
package Model;
import java.util.*;

public class AttackResult {
    private List<TargetDamage> damageResult;
    private Map<Position.PositionType, Integer> damageByPosition;
    private List<String> messages;

    public AttackResult() {
        this.damageResult = new ArrayList<>();
        this.damageByPosition = new HashMap<Position.PositionType, Integer>();
        this.messages = new ArrayList<>();
    }

    public void addDamage(Optional<Position.PositionType> target, int damage, String message) {
        damageResult.add(new TargetDamage(target, damage, message));
        target.ifPresent(position -> damageByPosition.merge(position, damage, Integer::sum));
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void addMiss(Position.PositionType target, String message){
        damageResult.add(TargetDamage.createMiss(target, message));
    }

    public List<TargetDamage> getDamageResult() {
        return damageResult;
    }

    public int getTotalDamage(){
        return damageResult.stream()
                .mapToInt(TargetDamage::getDamageAmount)
                .sum();
    }

    public List<String> getMessages() {
        return messages;
    }
}
