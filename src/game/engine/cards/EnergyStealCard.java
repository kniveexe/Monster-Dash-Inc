package game.engine.cards;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

/**
 * Represents energy steal cards.
 * Steals a fixed amount of energy from the opponent and gives it to the player.
 * The opponent's shield can block the steal entirely.
 * Always a lucky card.
 */
public class EnergyStealCard extends Card implements CanisterModifier {

    private int energy;   // READ ONLY - amount to steal

    /**
     * Creates an EnergyStealCard. Lucky is always true.
     *
     * @param name        card name
     * @param description what it does
     * @param rarity      how many copies exist in the pile
     * @param energy      how much energy to steal from the opponent
     */
    public EnergyStealCard(String name, String description, int rarity, int energy) {
        super(name, description, rarity, true);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    /**
     * Takes energy away from the given monster.
     * Implements CanisterModifier so the game engine can apply
     * just the energy loss side when needed.
     *
     * @param monster the monster losing energy
     */
    @Override
    public void modifyEnergy(Monster monster) {
        monster.setEnergy(monster.getEnergy() - this.energy);
    }
}