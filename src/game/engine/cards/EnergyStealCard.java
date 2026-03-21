package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier{
	private final int energy;
	
	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name,description,rarity,true);
		this.energy = energy;
		
		
	}
	 public void modifyEnergy(Monster monster) {
	        
	        monster.setEnergy(monster.getEnergy() - energy);
	    }
	
	 public int getEnergy() {
		 return energy;
	 }
	
	
}
