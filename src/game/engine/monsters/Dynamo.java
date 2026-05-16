package game.engine.monsters;

import game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	
	@Override
	public void setEnergy(int newEnergy) {
		int change = newEnergy - getEnergy();
		// Doubles all incoming energy changes, positive or negative
		super.setEnergy(getEnergy() + (change * 2));
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		// Screech Freeze: Freezes the opponent for one turn
		opponentMonster.setFrozen(true);
	}
}