package game.engine.monsters;

import game.engine.Role;

/**
 * Dynamo – Energy-amplification monster.
 *
 * Passive  : ALL energy gains are doubled AND all energy losses are doubled
 *            (double-edged sword).
 * PowerUp  : Energy Freeze – freezes the opponent for 1 full turn (they skip
 *            their next turn entirely).
 */
public class Dynamo extends Monster {

    // ─── Constructor ─────────────────────────────────────────────────────────
    public Dynamo(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
    }

    /**
     * Applies Dynamo's passive energy amplification.
     * Gains   → doubled (positive amount * 2)
     * Losses  → doubled (negative amount * 2, i.e. more negative)
     *
     * @param rawAmount the base energy change before amplification
     * @return the amplified energy change
     */
    public int amplify(int rawAmount) {
        return rawAmount * 2;
    }
}