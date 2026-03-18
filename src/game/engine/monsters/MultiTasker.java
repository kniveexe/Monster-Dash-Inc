package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;

public class MultiTasker extends Monster{
	int normalSpeedTurns;
	public MultiTasker(String name, String description, Role originalRole, int energy) {
	
		super(name,description,originalRole,energy);
		this.normalSpeedTurns = 0;
	}
	
	
}