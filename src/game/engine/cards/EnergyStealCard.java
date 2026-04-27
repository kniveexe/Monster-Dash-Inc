package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier {
	private final int energy;
	
	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy = energy;
	}

	public int getEnergy() {
		return energy;
	}
	
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		if (canisterValue < 0) {
			monster.alterEnergy(canisterValue); // alterEnergy respects the shield
		} else {
			monster.setEnergy(monster.getEnergy() + canisterValue);
		}
	}
	
	@Override
	public void performAction(Monster player, Monster opponent) {
		int amountToSteal = Math.min(this.energy, opponent.getEnergy());
		int oppOldEnergy = opponent.getEnergy();
		
		// Attempt to steal from opponent (respects shield via modifyCanisterEnergy)
		modifyCanisterEnergy(opponent, -amountToSteal);
		
		// Calculate how much was actually stolen in case the shield blocked it
		int actualStolen = oppOldEnergy - opponent.getEnergy();
		
		// Player gains the actual stolen amount
		modifyCanisterEnergy(player, actualStolen);
	}
}