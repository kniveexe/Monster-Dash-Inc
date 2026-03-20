package game.engine.cells;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;


public class DoorCell extends Cell implements CanisterModifier{
	private final Role role;
	private final int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);	
		this.role = role;
		this.energy = energy;
		this.activated = false;
		
	}

	
	public void modifyEnergy(Monster monster) {
		if(activated) return;
		if(monster.getRole() == role) {
			monster.setEnergy(monster.getEnergy()+energy);
		}else {
			monster.setEnergy(monster.getEnergy()-energy);
		}
		activated = true;
	}
	
	public Role getRole() { return role; }
    public int getEnergy() { return energy; }
    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

	

   
    
    
    
    
    
    
    
    
    
}