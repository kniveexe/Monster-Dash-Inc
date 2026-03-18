package game.engine.monsters;

import game.engine.Role;

public abstract class Monster{
	private String name;
	private String description;
	private Role role;
	private Role originalrole;
	private int energy;
	private int position;
	private boolean frozen;
	private boolean shielded;
	private int confusionTurns;
	
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
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Role getRole() {
		return this.role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getOriginalRole() {
		return this.originalrole;
	}
	public int getEnergy() {
		return this.energy;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean getFrozen() {
		return this.frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	public boolean getShielded() {
		return this.shielded;
	}
	public void setShielded(boolean shielded) {
		this.shielded = shielded;
	}
	public int getConfusionTurns() {
		return this.confusionTurns;
	}
	public void setConfusionTurns(int confusionTurns) {
		this.confusionTurns = confusionTurns;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
