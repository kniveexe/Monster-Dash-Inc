package game.engine.monsters;

import game.engine.Role;

/**
 * Dasher – Speed-focused monster.
 *
 * Passive  : Base dice movement is DOUBLED (2x speed).
 * PowerUp  : Momentum Rush – move at 3x speed for the next 3 turns.
 *            During powerup, the 3x speed REPLACES the passive 2x.
 *            momentumTurns counts down each turn until it hits 0.
 */
public class Dasher extends Monster {

    private int momentumTurns;   // READ AND WRITE

    // ─── Constructor ─────────────────────────────────────────────────────────
    public Dasher(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
        this.momentumTurns = 0;
    }

    // ─── Getter / Setter ─────────────────────────────────────────────────────
    public int getMomentumTurns()              { return momentumTurns; }
    public void setMomentumTurns(int momentumTurns) {
        this.momentumTurns = Math.max(0, momentumTurns);
    }

    /**
     * Returns the movement multiplier that should be applied to the dice roll.
     *   - Momentum active  → 3x
     *   - Normal (passive) → 2x
     */
    public int getSpeedMultiplier() {
        return (momentumTurns > 0) ? 3 : 2;
    }
}