package game.engine.cells;

import game.engine.monsters.Monster;

public class MonsterCell extends Cell {
	private final Monster cellMonster;
	
	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}
	
	public Monster getCellMonster() {
		return cellMonster;
	}

	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		
		if (landingMonster.getRole() == cellMonster.getRole()) {
			// Free powerup execution
			landingMonster.executePowerupEffect(opponentMonster);
		} else if (landingMonster.getEnergy() > cellMonster.getEnergy()) {
			// Swap energies
			int landingOldEnergy = landingMonster.getEnergy();
			int cellOldEnergy = cellMonster.getEnergy();
			int penalty = landingOldEnergy - cellOldEnergy;
			
			// Landing monster loses energy down to the cell monster's level (respects shield)
			landingMonster.alterEnergy(-penalty);
			
			// Cell monster gains the energy
			cellMonster.setEnergy(landingOldEnergy);
		}
	}
}