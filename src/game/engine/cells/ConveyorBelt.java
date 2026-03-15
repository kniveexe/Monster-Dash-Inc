package game.engine.cells;

/**
 * Moves the monster forward. Effect is always positive.
 */
public class ConveyorBelt extends TransportCell {

    /**
     * Creates a ConveyorBelt. Effect is forced to be positive.
     *
     * @param name   cell name
     * @param effect steps to move forward (will be made positive if not already)
     */
    public ConveyorBelt(String name, int effect) {
        super(name, Math.abs(effect));
    }

    @Override
    public String toString() {
        return "ConveyorBelt[" + getName() + ", effect=" + getEffect() + "]";
    }
}