package game.engine.cells;

import game.engine.monsters.Monster;
public class Cell{
	private final String name; private Monster monster;  // READ AND WRITE aka setters we getters

    public Cell(String name) {
        this.name    = name;
        this.monster = null;
    }

    public String getName() { //de be teget el name
        return name;
    }

    public Monster getMonster() { // de le monster 3shn tegb el  monster wa2f fen (cell)
        return monster;
    }

    public void setMonster(Monster monster) { // de hat8yar mkan monster
        this.monster = monster;
    }

    
    public String toString() {
        return "Cell position for (" + name + ")";
    }
}// mkan el monster
	
