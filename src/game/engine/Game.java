package game.engine;

import game.engine.monsters.Monster;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.Constants; // Added missing import

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private final Board board; 
    private final ArrayList<Monster> allMonsters; 
    private final Monster player; 
    private final Monster opponent; 
    private Monster current;

    public Game(Role playerRole) throws IOException {
        this.board = new Board(DataLoader.readCards());
        this.allMonsters = DataLoader.readMonsters();

        this.player = selectRandomMonsterByRole(playerRole);
    
        Role opponentRole = (playerRole == Role.SCARER) ? Role.LAUGHER : Role.SCARER;
        this.opponent = selectRandomMonsterByRole(opponentRole);

        this.current = player;

        // 1. Assign stationed monsters (exclude player and opponent)
        ArrayList<Monster> stationed = new ArrayList<>(allMonsters);
        stationed.remove(this.player);
        stationed.remove(this.opponent);
        Board.setStationedMonsters(stationed);

        // 2. Initialize the board cells
        // NOTE: If 'readCells()' still has a red line, open DataLoader.java 
        // to check exactly what the method for loading cells is called (e.g., loadCells)
        this.board.initializeBoard(DataLoader.readCells());
    }

    private Monster selectRandomMonsterByRole(Role role) {
        ArrayList<Monster> matchingMonsters = new ArrayList<>();
        
        for (Monster m : allMonsters) {
            if (m.getRole() == role) {  
                matchingMonsters.add(m);
            }
        }
        
        if (matchingMonsters.isEmpty()) return null;
        
        int randomIndex = (int) (Math.random() * matchingMonsters.size());
        return matchingMonsters.get(randomIndex);
    }

    public Board getBoard() { return board; } 

    public ArrayList<Monster> getAllMonsters() { return allMonsters; }

    public Monster getPlayer() { return player; } 

    public Monster getOpponent() { return opponent; } 

    public Monster getCurrent() { return current; } 

    public void setCurrent(Monster current) { this.current = current; } 
    
    private Monster getCurrentOpponent() { 
        return (current == player) ? opponent : player;
    }
    
    private int rollDice() { 
        return (int) (Math.random() * 6) + 1;
    }

    public void usePowerup() throws OutOfEnergyException {
        if (current.getEnergy() < Constants.POWERUP_COST) {
            throw new OutOfEnergyException("Not enough energy to use powerup.");
        }
        current.setEnergy(current.getEnergy() - Constants.POWERUP_COST);
        current.executePowerupEffect(getCurrentOpponent());
    }

    public void playTurn() throws InvalidMoveException {
        if (current.isFrozen()) {
            current.setFrozen(false); // Skip turn and unfreeze
        } else {
            int roll = rollDice();
            board.moveMonster(current, roll, getCurrentOpponent());
        }
        switchTurn();
    }

    private void switchTurn() {
        this.current = getCurrentOpponent();
    }

    private boolean checkWinCondition(Monster monster) {
        // Checking if the monster has reached the end of the board
        return monster.getPosition() >= Constants.BOARD_SIZE - 1;
    }

    public Monster getWinner() {
        if (checkWinCondition(player)) {
            return player;
        } else if (checkWinCondition(opponent)) {
            return opponent;
        }
        return null;
    }
}