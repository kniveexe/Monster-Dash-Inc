package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;

/**
 * MultiTasker – Slow but energy-efficient monster.
 *
 * Passive  : Dice roll is HALVED (rounded down) for movement.
 *            Every energy gain or loss receives a +200 bonus (MULTITASKER_BONUS).
 * PowerUp  : Focus Mode – moves at NORMAL speed (no halving) for 2 turns.
 *            The +200 energy bonus is KEPT during Focus Mode.
 *            normalSpeedTurns counts down each turn.
 */
public class MultiTasker extends Monster {

    private int normalSpeedTurns;   // READ AND WRITE

    // ─── Constructor ─────────────────────────────────────────────────────────
    public MultiTasker(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
        this.normalSpeedTurns = 0;
    }

    // ─── Getter / Setter ─────────────────────────────────────────────────────
    public int getNormalSpeedTurns()                   { return normalSpeedTurns; }
    public void setNormalSpeedTurns(int normalSpeedTurns) {
        this.normalSpeedTurns = Math.max(0, normalSpeedTurns);
    }

    /**
     * Returns the actual steps to move given a raw dice roll.
     *   - Focus Mode active  → full dice value (normal speed)
     *   - Normal (passive)   → dice / 2  (halved, integer division)
     */
    public int applySpeedModifier(int diceRoll) {
        return (normalSpeedTurns > 0) ? diceRoll : diceRoll / 2;
    }

    /**
     * Returns the energy bonus that should be added to every energy change
     * (positive or negative). Always +200 regardless of Focus Mode.
     */
    public int getEnergyBonus() {
        return Constants.MULTITASKER_BONUS;
    }
}