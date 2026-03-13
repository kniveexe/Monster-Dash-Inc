package game.engine.monsters;

import game.engine.Role;

public abstract class Monster implements Comparable<Monster> {

    private final String name;
    private final String description;
    private Role role;
    private final Role originalRole;
    private int energy;
    private int position;
    private boolean frozen;
    private boolean shielded;
    private int confusionTurns;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Monster(String name, String description, Role originalRole, int energy) {
        this.name           = name;
        this.description    = description;
        this.originalRole   = originalRole;
        this.role           = originalRole;   // role starts equal to originalRole
        this.energy         = energy;
        this.position       = 0;
        this.confusionTurns = 0;
        this.frozen         = false;
        this.shielded       = false;
    }

    // ─── READ-ONLY getters (no setters) ───────────────────────────────────────
    public String getName()        { return name; }
    public String getDescription() { return description; }
    public Role getOriginalRole()  { return originalRole; }

    // ─── role  (READ AND WRITE) ────────────────────────────────────────────────
    public Role getRole()             { return role; }
    public void setRole(Role role)    { this.role = role; }

    // ─── energy  (READ AND WRITE, >= 0) ──────────────────────────────────────
    public int getEnergy() { return energy; }
    public void setEnergy(int energy) {
        this.energy = Math.max(0, energy);   // cannot fall below MIN_ENERGY (0)
    }

    // ─── position  (READ AND WRITE, 0-99) ────────────────────────────────────
    public int getPosition() { return position; }
    public void setPosition(int position) {
        this.position = Math.max(0, Math.min(99, position));
    }

    // ─── frozen  (READ AND WRITE) ─────────────────────────────────────────────
    public boolean isFrozen()               { return frozen; }
    public void setFrozen(boolean frozen)   { this.frozen = frozen; }

    // ─── shielded  (READ AND WRITE) ───────────────────────────────────────────
    public boolean isShielded()                 { return shielded; }
    public void setShielded(boolean shielded)   { this.shielded = shielded; }

    // ─── confusionTurns  (READ AND WRITE) ────────────────────────────────────
    public int getConfusionTurns()                        { return confusionTurns; }
    public void setConfusionTurns(int confusionTurns) {
        this.confusionTurns = Math.max(0, confusionTurns);
    }

    // ─── Comparable: order by board position (closer to end = greater) ────────
    @Override
    public int compareTo(Monster o) {
        return this.position - o.position;
    }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "%s [%s] | Pos: %d | Energy: %d | Frozen: %b | Shielded: %b | ConfusionTurns: %d",
            name, role, position, energy, frozen, shielded, confusionTurns
        );
    }
}