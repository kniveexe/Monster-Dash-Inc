package game.engine.cells;

import game.engine.Constants;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

/**
 * Moves the monster backward AND drains 100 energy.
 * The energy loss can be blocked by a shield but the movement still happens.
 * Effect is always negative.
 */
public class ContaminationSock extends TransportCell implements CanisterModifier {

    /**
     * Creates a ContaminationSock. Effect is forced to be negative.
     *
     * @param name   cell name
     * @param effect steps to move backward (will be made negative if not already)
     */
    public ContaminationSock(String name, int effect) {
        super(name, -Math.abs(effect));
    }

    /**
     * Drains 100 energy from the monster. Can be blocked by a shield.
     *
     * @param monster the monster that landed here
     */
    @Override
    public void modifyEnergy(Monster monster) {
        monster.setEnergy(monster.getEnergy() - Constants.SLIP_PENALTY);
    }

    @Override
    public String toString() {
        return "ContaminationSock[" + getName() + ", effect=" + getEffect() + "]";
    }
}