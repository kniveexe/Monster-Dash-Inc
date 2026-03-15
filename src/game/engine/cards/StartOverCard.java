package game.engine.cards;

/**
 * Represents start over cards that reset a monster's position to cell 0.
 * Can be lucky or unlucky depending on who gets sent back:
 *   lucky = true  → opponent gets sent back (good for the player)
 *   lucky = false → player gets sent back   (bad for the player)
 */
public class StartOverCard extends Card {

    /**
     * Creates a StartOverCard.
     *
     * @param name        card name
     * @param description what it does
     * @param rarity      how many copies exist in the pile
     * @param lucky       true if opponent is sent back, false if player is sent back
     */
    public StartOverCard(String name, String description, int rarity, boolean lucky) {
        super(name, description, rarity, lucky);
    }
}