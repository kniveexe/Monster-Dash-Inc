package game.engine.cards;

/**
 * Abstract class representing the special cards available in the game.
 * No objects of type Card can be instantiated directly.
 */
public abstract class Card {

    private String name;         // READ ONLY
    private String description;  // READ ONLY
    private int rarity;          // READ ONLY
    private boolean lucky;       // READ ONLY

    /**
     * Creates a Card with the given values.
     *
     * @param name        the card's name
     * @param description the card's description
     * @param rarity      the card's rarity level - specifies number of cards in the pile
     * @param lucky       whether this is a lucky card or not according to the player
     */
    public Card(String name, String description, int rarity, boolean lucky) {
        this.name        = name;
        this.description = description;
        this.rarity      = rarity;
        this.lucky       = lucky;
    }

    // ─── Getters (all READ ONLY - no setters) ────────────────────────────────

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRarity() {
        return rarity;
    }

    // boolean getter uses "is" prefix per naming conventions
    public boolean isLucky() {
        return lucky;
    }

    @Override
    public String toString() {
        return name + " (rarity=" + rarity + ", lucky=" + lucky + ")";
    }
}