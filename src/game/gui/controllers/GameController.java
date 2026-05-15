package game.gui.controllers;

import game.engine.Game;
import game.engine.Role;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.Monster;
import game.gui.views.EndScreen;
import game.gui.views.GameScreen;
import game.gui.Main;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GameController {

    private Game game;
    private GameScreen gameScreen;
    
    private int p1PrevEnergy, p2PrevEnergy;
    private boolean p1PrevShield, p2PrevShield;

    public GameController(Role playerRole) throws IOException {
        game = new Game(playerRole);
        gameScreen = new GameScreen(this);
        captureStats();
        updateView();
    }

    private void captureStats() {
        p1PrevEnergy = game.getPlayer().getEnergy();
        p2PrevEnergy = game.getOpponent().getEnergy();
        p1PrevShield = game.getPlayer().isShielded();
        p2PrevShield = game.getOpponent().isShielded();
    }

    private void logDiffs() {
        checkMonsterDiff(game.getPlayer(), p1PrevEnergy, p1PrevShield);
        checkMonsterDiff(game.getOpponent(), p2PrevEnergy, p2PrevShield);
        captureStats();
    }

    private void checkMonsterDiff(Monster m, int prevEnergy, boolean prevShield) {
        if (prevShield && !m.isShielded() && m.getEnergy() == prevEnergy) {
            gameScreen.addLogMessage(m.getName() + "'s SHIELD blocked an energy loss!");
        } else if (m.getEnergy() != prevEnergy) {
            int diff = m.getEnergy() - prevEnergy;
            gameScreen.addLogMessage(m.getName() + (diff > 0 ? " GAINED " : " LOST ") + Math.abs(diff) + " energy.");
        }
    }

    public BorderPane getView() {
        return gameScreen.getView();
    }

    public Game getGame() {
        return game;
    }

    public void rollDice() {
        try {
            Monster activeMonster = game.getCurrent();
            int oldPos = activeMonster.getPosition();
            game.playTurn();
            
            String log = activeMonster.getName() + " rolled a " + game.getLastRoll() + " and moved to " + activeMonster.getPosition() + ".";
            gameScreen.addLogMessage(log);

            if (game.getBoard().getLastCardDrawn() != null) {
                gameScreen.addLogMessage("Card Drawn: " + game.getBoard().getLastCardDrawn().getName() + " - " + game.getBoard().getLastCardDrawn().getDescription());
                game.getBoard().clearLastCardDrawn();
            }
            
            logDiffs();
            updateView();
            checkWin();
        } catch (InvalidMoveException e) {
            showAlert("Invalid Move", "You cannot move there. The cell is occupied by the opponent.");
        }
    }

    public void usePowerup() {
        try {
            game.usePowerup();
            gameScreen.addLogMessage(game.getCurrent().getName() + " used their powerup!");
            logDiffs();
            updateView();
        } catch (OutOfEnergyException e) {
            showAlert("Out of Energy", "Not enough energy to use this powerup.");
        }
    }

    public void updateView() {
        gameScreen.updateBoard(game.getBoard(), game.getPlayer(), game.getOpponent());
        gameScreen.updateStats(game.getCurrent(), game.getCurrent() == game.getPlayer() ? game.getOpponent() : game.getPlayer());
    }

    private void checkWin() {
        Monster winner = game.getWinner();
        if (winner != null) {
            EndScreen endScreen = new EndScreen(winner);
            Scene scene = new Scene(endScreen.getView(), 1000, 800);
            Main.getPrimaryStage().setScene(scene);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
