package game.engine.monsters;

import game.engine.Role;


public class MultiTasker extends Monster{
	private int normalSpeedTurns;
	public MultiTasker(String name, String description, Role originalRole, int energy) {
	
		super(name,description,originalRole,energy);
		this.normalSpeedTurns = 0;
	}
	public int getNormalSpeedTurns() {
		return this.normalSpeedTurns;
	}
	
	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}
	
}