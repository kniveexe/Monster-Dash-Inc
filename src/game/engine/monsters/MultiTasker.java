package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role originalRole, int energy) {
		super(name, description, originalRole, energy);
		this.normalSpeedTurns = 0;
	}
	
	public int getNormalSpeedTurns() {
		return this.normalSpeedTurns;
	}
	
	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}

	@Override
	public void setEnergy(int newEnergy) {
		int change = newEnergy - getEnergy();
		if (change != 0) {
			// Gains +200 energy on all incoming energy changes
			super.setEnergy(newEnergy + 200);
		} else {
			super.setEnergy(newEnergy);
		}
	}

	@Override
	public void move(int distance) {
		super.move(distance); // Passive: Moves at the normal dice roll
		if (normalSpeedTurns > 0) {
			normalSpeedTurns--;
		}
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		// Focus Mode: moves at normal speed for 2 turns
		setNormalSpeedTurns(2);
	}
}