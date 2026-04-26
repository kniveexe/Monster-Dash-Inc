package game.engine.cards;

import game.engine.monsters.Monster;

public abstract class Card{
	private final String name;
    private final String description;
    private final int rarity;
    private final boolean lucky;
	
	public Card(String name, String description, int rarity, boolean lucky) {
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.lucky = lucky;
		
		
	}
	
	abstract void performAction(Monster player, Monster opponent);
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getRarity() {
		return rarity;
	}

	public boolean isLucky() {
		return lucky;
	}
    
    
	
	
	
    
	
}
