package game.engine.cards;

/**
 * Represents shield cards that protect monsters from losing energy.
 * Gives the current player a shield that blocks the next negative energy effect.
 * If the opponent already has a shield, it is removed first.
 * Always a lucky card.
 */
public class ShieldCard extends Card {

    /**
     * Creates a ShieldCard. Lucky is always true.
     *
     * @param name        card name
     * @param description what it does
     * @param rarity      how many copies exist in the pile
     */
    public ShieldCard(String name, String description, int rarity) {
        super(name, description, rarity, true);
    }
}