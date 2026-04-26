package game.engine.cards;

import game.engine.Role;
import game.engine.monsters.Monster;

public class ConfusionCard extends Card{
	private final int duration;
	
	public ConfusionCard(String name, String description, int rarity, int duration) {
		super(name,description,rarity,false);
		this.duration = duration;
		
		
	}

	public int getDuration() {
		return duration;
	}
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		Role playerRole = player.getRole();
		Role opponentRole = opponent.getRole();
		player.setConfusionTurns(getDuration());
		opponent.setConfusionTurns(getDuration());
		
		opponent.setRole(playerRole);
		player.setRole(opponentRole);
		
		
		
		
		
	}
	
	
	
	
	
	
}