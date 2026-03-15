package game.engine.cards;

/**
 * Represents swapper cards that allow position swapping.
 * Swaps the player's position with the opponent if the player is behind.
 * If the player is already ahead or tied, no effect.
 * Always a lucky card.
 */
public class SwapperCard extends Card {

    /**
     * Creates a SwapperCard. Lucky is always true.
     *
     * @param name        card name
     * @param description what it does
     * @param rarity      how many copies exist in the pile
     */
    public SwapperCard(String name, String description, int rarity) {
        super(name, description, rarity, true);
    }
}