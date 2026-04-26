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
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		int oppEnergy = opponent.getEnergy();
		int cardEnergy = getEnergy();
		int playerEnergy = player.getEnergy();
		
		if(!opponent.isShielded()) {
			if(oppEnergy>=cardEnergy) {
				player.setEnergy(playerEnergy + cardEnergy);
				opponent.setEnergy(oppEnergy - cardEnergy);
				
			}else {
				player.setEnergy(playerEnergy+oppEnergy);
				opponent.setEnergy(0);
			
			
		}
		
		
	}
	 
	 
	 
	}
	
	
}	
