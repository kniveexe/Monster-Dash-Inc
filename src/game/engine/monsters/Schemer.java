package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;
import java.util.List;

/**
 * Schemer – Energy-manipulation monster.
 *
 * Passive  : All energy changes (gain or loss) receive a +10 bonus
 *            (SCHEMER_STEAL). Gains become slightly larger; losses become
 *            slightly smaller.
 * PowerUp  : Chain Attack – steals SCHEMER_STEAL (10) energy from EVERY other
 *            monster on the board (teammates AND opponents).
 *            If a target has less than 10 energy, steal all of it.
 *            The Schemer receives the SUM of all stolen amounts at once.
 *            This steal is NOT blocked by a shield.
 */
public class Schemer extends Monster {

    // ─── Constructor ─────────────────────────────────────────────────────────
    public Schemer(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
    }

    /**
     * Applies the Schemer's passive +10 bonus to any raw energy change.
     * e.g. rawAmount = -50  →  returns -40  (loss is softened by 10)
     *      rawAmount = +100 →  returns +110 (gain is boosted by 10)
     *
     * @param rawAmount base energy change before the Schemer bonus
     * @return adjusted energy change
     */
    public int applySchemeBonus(int rawAmount) {
        return rawAmount + Constants.SCHEMER_STEAL;
    }

    /**
     * Executes the Chain Attack powerup against a list of targets.
     * Steals up to SCHEMER_STEAL energy from each target and adds the
     * total stolen amount to this Schemer's own energy.
     * The shield does NOT block this steal.
     *
     * @param targets all other monsters (teammates + opponents) currently in the game
     */
    public void chainAttack(List<Monster> targets) {
        int totalStolen = 0;
        for (Monster target : targets) {
            if (target == this) continue;                      // skip self
            int steal = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
            target.setEnergy(target.getEnergy() - steal);
            totalStolen += steal;
        }
        this.setEnergy(this.getEnergy() + totalStolen);
    }
}