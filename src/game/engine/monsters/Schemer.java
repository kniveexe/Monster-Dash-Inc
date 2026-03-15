package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;
import java.util.List;

/**
 * Schemer – Energy-manipulation monster.
 *
 * Passive  : All energy changes (gain or loss) receive a +10 bonus
 *            (SCHEMER_STEAL). Gains become slightly larger; losses become
 *            slightly smaller. The game engine calls applySchemeBonus()
 *            before applying any energy change to this monster.
 * PowerUp  : Chain Attack – steals SCHEMER_STEAL (10) energy from EVERY other
 *            monster on the board (teammates AND opponents).
 *            If a target has less than 10 energy, steal all of it.
 *            The Schemer receives the SUM of all stolen amounts at once.
 *            This steal is NOT blocked by a shield.
 */
public class Schemer extends Monster {

    // ─── Constructor ─────────────────────────────────────────────────────────

    /**
     * Creates a Schemer.
     *
     * @param name        monster's name
     * @param description special power description
     * @param role        starting role
     * @param energy      starting energy
     */
    public Schemer(String name, String description, Role role, int energy) {
        super(name, description, role, energy);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    /**
     * Applies the Schemer's passive +10 bonus to any raw energy change.
     * The game engine calls this before applying any energy change to this monster.
     *   rawAmount = -50  →  returns -40  (loss is softened by 10)
     *   rawAmount = +100 →  returns +110 (gain is boosted by 10)
     *
     * @param rawAmount base energy change before the Schemer bonus
     * @return adjusted energy change (rawAmount + SCHEMER_STEAL)
     */
    public int applySchemeBonus(int rawAmount) {
        return rawAmount + Constants.SCHEMER_STEAL;
    }

    /**
     * Executes the Chain Attack powerup against a list of targets.
     * Steals up to SCHEMER_STEAL (10) energy from each monster in the list,
     * then adds the total stolen amount to this Schemer's own energy.
     * Skips itself if it appears in the list.
     * The shield does NOT block this steal.
     *
     * @param targets all other monsters (teammates + opponents) in the game
     */
    public void chainAttack(List<Monster> targets) {
        int totalStolen = 0;
        for (Monster target : targets) {
            if (target == this) continue;   // skip self
            int steal = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());
            target.setEnergy(target.getEnergy() - steal);
            totalStolen += steal;
        }
        this.setEnergy(this.getEnergy() + totalStolen);
    }

    // ─── Abstract method implementations ─────────────────────────────────────

    /**
     * Schemer has no movement passive so the dice roll is returned unchanged.
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
     * Signals that Chain Attack has been activated.
     * The game engine is responsible for collecting all other monsters into
     * a list and passing it to chainAttack() after this method is called.
     * This method is required by the Monster abstract class.
     */
    @Override
    public void performPowerUp() {
        // the game engine handles the chain steal:
        // schemer.chainAttack(allOtherMonsters)
    }
}