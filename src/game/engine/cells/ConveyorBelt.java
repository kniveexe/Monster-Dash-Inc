package game.engine.cells;

import game.engine.monsters.Monster;

public class ConveyorBelt extends TransportCell {
	
	public ConveyorBelt(String name, int effect) {
		super(name, Math.abs(effect));
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		transport(landingMonster);
	}
}