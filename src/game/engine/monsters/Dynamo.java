package game.engine.monsters;

import game.engine.Role;

/**
 * Dynamo – Energy-amplification monster.
 *
 * Passive  : ALL energy gains are doubled AND all energy losses are doubled
 *            (double-edged sword). This is applied by the game engine using
 *            the amplify() helper method.
 * PowerUp  : Energy Freeze – freezes the opponent for 1 full turn so they
 *            skip their next turn entirely.
 *            The game engine calls setFrozen(true) on the opponent after
 *            performPowerUp() is called.
 */
public class Dynamo extends Monster {

    // ─── Constructor ─────────────────────────────────────────────────────────

    /**
     * Creates a Dynamo.
     *
     * @param name        monster's name
     * @param description special power description
     * @param role        starting role
     * @param energy      starting energy
     */
    public Dynamo(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
    }

    // ─── Helper ──────────────────────────────────────────────────────────────

    /**
     * Applies Dynamo's passive energy amplification.
     * The game engine calls this before applying any energy change to
     * make sure gains and losses are both doubled.
     *   - Gains  (positive rawAmount) → doubled
     *   - Losses (negative rawAmount) → doubled (more negative)
     *
     * @param rawAmount the base energy change before amplification
     * @return the amplified energy change (rawAmount * 2)
     */
    public int amplify(int rawAmount) {
        return rawAmount * 2;
    }

    // ─── Abstract method implementations ─────────────────────────────────────

    /**
     * Dynamo has no movement passive so the dice roll is returned unchanged.
     * This method is required by the Monster abstract class so the game
     * engine can call it on any monster without knowing its specific type.
     *
     * @param diceRoll the number rolled on the dice
     * @return diceRoll unchanged
     */
    @Override
    public int getSpeedModifier(int diceRoll) {
        return diceRoll;
    }

    /**
     * Signals that Energy Freeze has been activated.
     * The game engine is responsible for finding the opponent and calling
     * setFrozen(true) on them after this method is called.
     * This method is required by the Monster abstract class.
     */
    @Override
    public void performPowerUp() {
        // the game engine handles freezing the opponent:
        // opponent.setFrozen(true)
    }
}