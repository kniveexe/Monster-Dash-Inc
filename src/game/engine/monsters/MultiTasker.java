package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;

/**
 * MultiTasker – Slow but energy-efficient monster.
 *
 * Passive  : Dice roll is HALVED (rounded down via integer division) for movement.
 *            Every energy gain or loss receives a +200 bonus (MULTITASKER_BONUS).
 * PowerUp  : Focus Mode – moves at NORMAL speed (no halving) for the next 2 turns.
 *            The +200 energy bonus is KEPT during Focus Mode.
 *            normalSpeedTurns counts down by 1 each turn until it reaches 0.
 */
public class MultiTasker extends Monster {

    private int normalSpeedTurns;   // READ AND WRITE

    // ─── Constructor ─────────────────────────────────────────────────────────

    /**
     * Creates a MultiTasker. normalSpeedTurns starts at 0.
     *
     * @param name        monster's name
     * @param description special power description
     * @param role        starting role
     * @param energy      starting energy
     */
    public MultiTasker(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
        this.normalSpeedTurns = 0;
    }

    // ─── Getter / Setter ─────────────────────────────────────────────────────

    /**
     * Returns how many Focus Mode turns are remaining.
     *
     * @return normalSpeedTurns
     */
    public int getNormalSpeedTurns() {
        return normalSpeedTurns;
    }

    /**
     * Sets the normal speed turns. Cannot go below 0.
     *
     * @param normalSpeedTurns number of focus mode turns to set
     */
    public void setNormalSpeedTurns(int normalSpeedTurns) {
        this.normalSpeedTurns = Math.max(0, normalSpeedTurns);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    /**
     * Returns the actual steps to move given a raw dice roll.
     * Used internally by getSpeedModifier.
     *   - Focus Mode active  → full dice value (normal speed)
     *   - Normal (passive)   → dice / 2  (halved via integer division)
     *
     * @param diceRoll the number rolled on the dice
     * @return steps to move after applying the speed rule
     */
    public int applySpeedModifier(int diceRoll) {
        return (normalSpeedTurns > 0) ? diceRoll : diceRoll / 2;
    }

    /**
     * Returns the energy bonus that must be added to every energy change.
     * Always +200 (MULTITASKER_BONUS) regardless of whether Focus Mode is active.
     * The game engine calls this when processing any energy gain or loss.
     *
     * @return Constants.MULTITASKER_BONUS (200)
     */
    public int getEnergyBonus() {
        return Constants.MULTITASKER_BONUS;
    }

    // ─── Abstract method implementations ─────────────────────────────────────

    /**
     * Returns the actual steps the MultiTasker will move this turn.
     * Delegates to applySpeedModifier so the halving/normal logic stays in one place.
     * This method is required by the Monster abstract class so the game
     * engine can call it on any monster without knowing its specific type.
     *
     * @param diceRoll the number rolled on the dice
     * @return steps to move after applying the MultiTasker's speed rule
     */
    @Override
    public int getSpeedModifier(int diceRoll) {
        return applySpeedModifier(diceRoll);
    }

    /**
     * Activates Focus Mode by setting normalSpeedTurns to 2.
     * For the next 2 turns the MultiTasker moves at normal speed instead of half.
     * The +200 energy bonus remains active during Focus Mode.
     * This method is required by the Monster abstract class.
     */
    @Override
    public void performPowerUp() {
        this.normalSpeedTurns = 2;
    }
}