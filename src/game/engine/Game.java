package game.engine;
import game.engine.monsters.Monster;
import java.io.IOException;
import java.util.ArrayList;
import game.engine.dataloader.DataLoader;
public class Game {
	private final Board board; // The game board [cite: 420]
    private final ArrayList<Monster> allMonsters; // All monsters from CSV [cite: 421]
    private final Monster player; // The human player's monster [cite: 422]
    private final Monster opponent; // The CPU's monster [cite: 423]
    private Monster current; // The monster whose turn it is (Read/Write)

}
