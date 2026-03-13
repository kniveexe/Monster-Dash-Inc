package game.engine.monsters;
import game.engine.Role;
public abstract class Monsters implements Comparable<Monsters> {

}
public abstract class Monsters implements Comparable<Monsters> {private final String name; // The monster's name (READ ONLY)[cite: 125].
private final String description; // Power description (READ ONLY)
private Role role; // Current game role (SCARER or LAUGHER)
private final Role originalRole; // Permanent starting role (READ ONLY)
private int energy; // Amount of energy collected (must be >= 0)
private int position; // Current square on the board (0-99)
private boolean frozen; // If true, the monster skips a turn
private boolean shielded; // If true, protected from energy loss
private int confusionTurns;
public Monsters(String name, String description, Role originalRole, int energy) {
    this.name = name; // Assigns the name from the input parameter[cite: 135].
    this.description = description; // Assigns the description[cite: 135].
    this.originalRole = originalRole; // Records the starting role[cite: 135].
    this.role = originalRole; // Sets current role to the original role initially[cite: 136].
    this.energy = energy; // Initializes starting energy[cite: 135].
    this.position = 0; // Starts at the first index (position 0)[cite: 136].
    this.confusionTurns = 0; // Starts with no confusion[cite: 136].
    this.frozen = false; // Initially not frozen[cite: 137].
    this.shielded = false; }}// Initially not shielded[cite: 137].


