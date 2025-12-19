/*
    Represents an enemy character with health, damage, and position.
    Handles taking damage and attacking the player. Tracks defeat status
    and position (left, middle, right) for targeting. Used by OpponentTeam
    to manage the three opponent characters.
*/
package Model;

public class Opponent {
    private int health;
    private int baseDamage;
    private boolean isDefeated;
    private Position.PositionType position;/// LEFT MIDDLE RIGHT
    private final int OPPONENT_DAMAGE = 5;

    public Opponent(int health, int baseDamage, boolean isDefeated, Position.PositionType position) {
        this.health = health;
        this.baseDamage = baseDamage;
        this.isDefeated = false;
        this.position = position;
    }

    public void takeDamage(int damage){
        this.health = Math.max(0, this.health - damage);
        if(this.health == 0){
            isDefeated = true;
        }
    }
    public int attackDamage(){
        return OPPONENT_DAMAGE;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        this.isDefeated = (health <= 0);
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public Position.PositionType getPosition() {
        return position;
    }

    public void setPosition(Position.PositionType position) {
        this.position = position;
    }
}
