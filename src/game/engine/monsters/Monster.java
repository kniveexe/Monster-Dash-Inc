package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public abstract class Monster implements Comparable<Monster> {
	final private String name;
	final private String description;
	private Role role;
	final private Role originalRole;
	private int energy;
	private int position;
	private boolean frozen;
	private boolean shielded;
	private int confusionTurns;
	
	public Monster(String name, String description, Role originalRole, int energy) {
		this.name = name;
		this.description = description;
		this.originalRole = originalRole;
		this.role = originalRole;
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
		return this.originalRole;
	}
	public int getEnergy() {
		return this.energy;
	}
	
	public void setEnergy(int energy) {
	    if(energy < Constants.MIN_ENERGY) {
	        this.energy = Constants.MIN_ENERGY; // = 0
	    } else {
	        this.energy = energy;
	    }
	}
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
	    if (position < Constants.STARTING_POSITION) {
	        this.position = Constants.STARTING_POSITION; 
	    } else {
	        this.position = position % Constants.BOARD_SIZE; 
	    }
	}
	public boolean isFrozen() {
		return this.frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	public boolean isShielded() {
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
	
	public void move(int distance) {
	    setPosition(this.position + distance);
	}

	public void decrementConfusion() {
	    if (confusionTurns > 0) {
	        confusionTurns--;
	        if (confusionTurns == 0) {
	            this.role = this.originalRole;
	        }
	    }
	}
	public abstract void executePowerupEffect(Monster opponentMonster);
	
	
}
