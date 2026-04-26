package game.engine.cards;

import game.engine.monsters.Monster;

public class StartOverCard extends Card{
	
	public StartOverCard(String name, String description, int rarity, boolean lucky) {
		super(name,description,rarity,lucky);
		
		
	}
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		if(isLucky()) {
		    opponent.setPosition(0);
		} else {
		    player.setPosition(0);
		}
		
		
		
	}
	
	
	
}
