package game.engine.monsters;

import game.engine.Role;

public abstract class Monster{
	String name;
	String description;
	Role role;
	Role originalrole;
	int energy;
	int position;
	boolean frozen;
	boolean shielded;
	int confusionTurns;
	
	public Monster(String name, String description, Role originalRole, int energy) {
		this.name = name;
		this.description = description;
		this.originalrole = originalRole;
		this.energy = energy;
		this.position = 0;
		this.frozen = false;
		this.shielded = false;
		this.confusionTurns = 0;
	}
	
	public int compareTo(Monster o) {
	    return this.position - o.position;
	}
	
	
	
	
	
	
	
	
	
	
	
}
