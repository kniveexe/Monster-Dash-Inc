package game.engine.cells;

/**
 * Landing here lets the monster draw a random card from the pile.
 */
public class CardCell extends Cell {

    /**
     * Creates a CardCell.
     *
     * @param name cell name
     */
    public CardCell(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "CardCell[" + getName() + "]";
    }
}