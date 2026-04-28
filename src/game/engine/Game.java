package game.engine;

import game.engine.monsters.Monster;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.Constants;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private final Board board; 
    private final ArrayList<Monster> allMonsters; 
    private final Monster player; 
    private final Monster opponent; 
    private Monster current;

    // CHECK THIS LINE: If your M1 Game constructor didn't take a Role, 
    // change this signature back to exactly what it was in Milestone 1!
    public Game(Role playerRole) throws IOException {
        this.board = new Board(DataLoader.readCards());
        this.allMonsters = DataLoader.readMonsters();

        this.player = selectRandomMonsterByRole(playerRole);
    
        // NOTE: Make sure your Role enum uses SCARER and LAUGHER. If it's SCARE and LAUGH, update these.
        Role opponentRole = (playerRole == Role.SCARER) ? Role.LAUGHER : Role.SCARER;
        this.opponent = selectRandomMonsterByRole(opponentRole);

        this.current = player;

        // 1. Assign stationed monsters (exclude player and opponent)
        allMonsters.remove(this.player);
        allMonsters.remove(this.opponent);
        Board.setStationedMonsters(allMonsters);
        
        // 2. Initialize the board cells
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
            // FIXED: Removed the String inside the exception so it matches M1
            throw new OutOfEnergyException();
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
        // FIXED: Replaced hardcoded numbers with Constants variables
        return monster.getPosition() >= Constants.WINNING_POSITION;
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