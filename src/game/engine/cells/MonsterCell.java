package game.engine.cells;

import game.engine.monsters.Monster;

/**
 * A cell with a stationed monster on it.
 * Landing here either triggers your powerup for free (same role)
 * or swaps energies if you have more than the cell monster (different role).
 */
public class MonsterCell extends Cell {

    private Monster cellMonster; // READ ONLY

    /**
     * Creates a MonsterCell with a stationed monster.
     *
     * @param name         cell name
     * @param cellMonster  the monster stationed here
     */
    public MonsterCell(String name, Monster cellMonster) {
        super(name);
        this.cellMonster = cellMonster;
    }

    public Monster getCellMonster() {
        return cellMonster;
    }

    @Override
    public String toString() {
        return "MonsterCell[" + getName() + ", stationed=" + cellMonster.getName() + "]";
    }
}