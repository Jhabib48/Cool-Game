/*
    Represents the player character with health, equipped weapon, and rings.
    Handles taking damage and tracks defeat status. Manages equipment including
    weapon and up to three rings. Provides access to player state for game mechanics
    and UI display.
*/
package Model;
import Model.rings.Ring;
import Model.weapons.Weapon;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Ring> rings;
    private int health;
    private Weapon equippedWeapon;
    private StatisticTracker stats;
    private boolean isDefeated;

    public Player(int health, Weapon weapon) {
        this.health = health;
        this.equippedWeapon = weapon;
        this.isDefeated = false;
        this.rings = new ArrayList<>();
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        if(this.health <= 0) {
            isDefeated = true;
        }
    }
    public boolean isDefeated() {return isDefeated;}
    public List<Ring> getRings() {return rings;}
    public int getHealth() {return health;}
    public Weapon getEquippedWeapon() {return equippedWeapon;}
    public void setEquippedWeapon(Weapon equippedWeapon) {this.equippedWeapon = equippedWeapon;}
    public void setRings(List<Ring> rings) {this.rings = rings;}
}
