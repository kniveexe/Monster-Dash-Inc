package game.engine.cells;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

/**
 * A door cell where monsters gain or lose energy based on role.
 * Can only be used once - becomes activated after first landing.
 */
public class DoorCell extends Cell implements CanisterModifier {

    private Role role;          // READ ONLY
    private int energy;         // READ ONLY
    private boolean activated;  // READ AND WRITE

    /**
     * Creates a DoorCell. Starts as not activated.
     *
     * @param name   cell name
     * @param role   the role this door supports
     * @param energy energy this door provides or takes
     */
    public DoorCell(String name, Role role, int energy) {
        super(name);
        this.role      = role;
        this.energy    = energy;
        this.activated = false;
    }

    public Role getRole() {
        return role;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Gives energy to the monster if roles match, takes it away if they don't.
     *
     * @param monster the monster landing on this door
     */
    @Override
    public void modifyEnergy(Monster monster) {
        if (monster.getRole() == this.role) {
            monster.setEnergy(monster.getEnergy() + this.energy);
        } else {
            monster.setEnergy(monster.getEnergy() - this.energy);
        }
    }

    @Override
    public String toString() {
        return "DoorCell[" + getName() + ", " + role + ", energy=" + energy + ", activated=" + activated + "]";
    }
}