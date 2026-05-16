package game.engine.cards;

import game.engine.monsters.Monster;

public class SwapperCard extends Card{
	
	public SwapperCard(String name, String description, int rarity) {
		super(name, description, rarity,true);
		
	}
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		int playerPos = player.getPosition();
		int oppPos = opponent.getPosition();
		
		if(playerPos<oppPos) {
			
			player.setPosition(oppPos);
			opponent.setPosition(playerPos);
		}
		
		
		
		
		
		
		
	}
	
	
	
	
}