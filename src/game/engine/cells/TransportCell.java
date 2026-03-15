package game.engine.cells;

/**
 * Abstract class for cells that move a monster forward or backward.
 * Works like snakes and ladders - the destination cell doesn't trigger its own effect.
 * Cannot be instantiated directly.
 */
public abstract class TransportCell extends Cell {

    private int effect; // READ ONLY

    /**
     * Creates a TransportCell.
     *
     * @param name   cell name
     * @param effect cells to move (positive = forward, negative = backward)
     */
    public TransportCell(String name, int effect) {
        super(name);
        this.effect = effect;
    }

    public int getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return "TransportCell[" + getName() + ", effect=" + effect + "]";
    }
}