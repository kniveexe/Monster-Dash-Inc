package game.engine.monsters;

import game.engine.Role;

public class Dasher extends Monster {
	
	private int momentumTurns;
	
	public Dasher(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.momentumTurns = 0;
	}
	
	public int getMomentumTurns() {
		return this.momentumTurns;
	}
	
	public void setMomentumTurns(int momentumTurns) {
		this.momentumTurns = momentumTurns;
	}

	@Override
	public void move(int distance) {
		if (momentumTurns > 0) {
			super.move(distance * 3); // Powerup: 3x speed
			momentumTurns--;
		} else {
			super.move(distance * 2); // Passive: 2x speed
		}
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		// Momentum Rush: moves at 3x speed for 3 turns
		setMomentumTurns(3);
	}
}