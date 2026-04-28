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

	    modifyCanisterEnergy(opponent, -amountToSteal);

	    // If shield blocked it, actualStolen = 0. If Dynamo doubled it, still use amountToSteal cap.
	    int actualStolen = Math.min(amountToSteal, oppOldEnergy - opponent.getEnergy());

	    modifyCanisterEnergy(player, actualStolen);
	}}