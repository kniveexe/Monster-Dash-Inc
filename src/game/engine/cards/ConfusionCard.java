package game.engine.cards;

/**
 * Represents confusion cards that confuse monsters about their role.
 * Swaps the roles of both player and opponent for a set number of turns.
 * During confusion, door interactions use the swapped role.
 * Always an unlucky card.
 */
public class ConfusionCard extends Card {

    private int duration;   // READ ONLY - how many turns the role swap lasts

    /**
     * Creates a ConfusionCard. Lucky is always false.
     *
     * @param name        card name
     * @param description what it does
     * @param rarity      how many copies exist in the pile
     * @param duration    how many turns both monsters stay confused
     */
    public ConfusionCard(String name, String description, int rarity, int duration) {
        super(name, description, rarity, false);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}