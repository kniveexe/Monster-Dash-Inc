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

    /**
     * Creates a Dasher. momentumTurns starts at 0.
     *
     * @param name        monster's name
     * @param description special power description
     * @param role        starting role
     * @param energy      starting energy
     */
    public Dasher(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
        this.momentumTurns = 0;
    }

    // ─── Getter / Setter ─────────────────────────────────────────────────────

    /**
     * Returns how many turns of Momentum Rush are left.
     *
     * @return momentumTurns
     */
    public int getMomentumTurns() {
        return momentumTurns;
    }

    /**
     * Sets the momentum turns. Cannot go below 0.
     *
     * @param momentumTurns number of turns to set
     */
    public void setMomentumTurns(int momentumTurns) {
        this.momentumTurns = Math.max(0, momentumTurns);
    }

    // ─── Helper ──────────────────────────────────────────────────────────────

    /**
     * Returns the raw speed multiplier for this turn.
     * Used internally by getSpeedModifier.
     *   - Momentum active  → 3
     *   - Normal (passive) → 2
     *
     * @return 3 if momentum is active, 2 otherwise
     */
    public int getSpeedMultiplier() {
        return (momentumTurns > 0) ? 3 : 2;
    }

    // ─── Abstract method implementations ─────────────────────────────────────

    /**
     * Returns the actual steps the Dasher will move this turn.
     * Applies 3x if Momentum Rush is active, 2x otherwise.
     * This method is required by the Monster abstract class so the game
     * engine can call it on any monster without knowing its specific type.
     *
     * @param diceRoll the number rolled on the dice
     * @return steps to move after applying speed modifier
     */
    @Override
    public int getSpeedModifier(int diceRoll) {
        return diceRoll * getSpeedMultiplier();
    }

    /**
     * Activates Momentum Rush by setting momentumTurns to 3.
     * For the next 3 turns the Dasher moves at 3x speed instead of 2x.
     * This method is required by the Monster abstract class.
     */
    @Override
    public void performPowerUp() {
        this.momentumTurns = 3;
    }
}