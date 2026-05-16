package game.gui.controllers;

import game.engine.Board;
import game.engine.Game;
import game.engine.Role;
import game.engine.cards.Card;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.Monster;
import game.gui.Main;
import game.gui.views.EndScreen;
import game.gui.views.GameScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GameController {

    private final Game game;
    private final GameScreen gameScreen;

    private int previousPlayerEnergy;
    private int previousOpponentEnergy;
    private int previousPlayerPosition;
    private int previousOpponentPosition;
    private boolean previousPlayerShield;
    private boolean previousOpponentShield;
    private boolean previousPlayerFrozen;
    private boolean previousOpponentFrozen;
    private int previousPlayerConfusion;
    private int previousOpponentConfusion;
    private Role previousPlayerRole;
    private Role previousOpponentRole;

    public GameController(Role playerRole) throws IOException {
        this.game = new Game(playerRole);
        this.gameScreen = new GameScreen(this);
        captureState();
        gameScreen.addInfoLog("Game started. Selected side: " + playerRole + ".");
        gameScreen.addInfoLog("Deck loaded and shuffled: " + Board.getCards().size() + " cards available.");
        refresh();
    }

    public BorderPane getView() {
        return gameScreen.getView();
    }

    public Game getGame() {
        return game;
    }

    public void rollDice() {
        Monster active = game.getCurrent();
        boolean wasFrozen = active.isFrozen();
        int oldPosition = active.getPosition();

        try {
            if (wasFrozen) {
                gameScreen.addWarningLog(active.getName() + " is frozen. The turn is skipped and freeze is removed.");
            }

            game.playTurn();

            if (wasFrozen) {
                gameScreen.setDiceText("Skipped");
            } else {
                gameScreen.setDiceText(String.valueOf(game.getLastRoll()));
                gameScreen.addInfoLog(active.getName() + " rolled " + game.getLastRoll() + ".");
            }

            if (active.getPosition() != oldPosition) {
                gameScreen.addInfoLog(active.getName() + " moved from cell " + oldPosition + " to cell " + active.getPosition() + ".");
            }

            Card drawnCard = Board.getLastCardDrawn();
            if (drawnCard != null) {
                gameScreen.setLastCard(drawnCard.getName(), drawnCard.getDescription());
                gameScreen.addInfoLog("Card drawn: " + drawnCard.getName() + " — " + drawnCard.getDescription());
                Board.clearLastCardDrawn();
            }

            logStateChanges();
            refresh();
            checkWinner();

        } catch (InvalidMoveException ex) {
            ex.printStackTrace();
            showWarning("Invalid Move", "The move is prohibited because the target cell is occupied or invalid. Choose another valid action.");
            gameScreen.addWarningLog("Invalid move blocked: cannot land on opponent's cell.");
            refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning("Action Error", safeMessage(ex));
            gameScreen.addWarningLog("Action blocked: " + ex.getClass().getSimpleName() + ". The game did not stop.");
            refresh();
        }
    }

    public void usePowerup() {
        Monster active = game.getCurrent();

        try {
            game.usePowerup();
            gameScreen.addSuccessLog(active.getName() + " activated a powerup before rolling.");
            logStateChanges();
            refresh();
        } catch (OutOfEnergyException ex) {
            ex.printStackTrace();
            showWarning("Out of Energy", "Not enough energy to activate the powerup (requires 500). Choose another action.");
            gameScreen.addWarningLog("Powerup blocked: insufficient energy (need 500).");
            refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning("Powerup Error", safeMessage(ex));
            gameScreen.addWarningLog("Powerup blocked: " + ex.getClass().getSimpleName() + ".");
            refresh();
        }
    }

    public void refresh() {
        gameScreen.updateStats(game.getPlayer(), game.getOpponent(), game.getCurrent());
        gameScreen.updateBoard(game.getBoard(), game.getPlayer(), game.getOpponent());
        gameScreen.updateDeckCount(Board.getCards().size());
    }

    private void checkWinner() {
        Monster winner = game.getWinner();
        if (winner != null) {
            EndScreen endScreen = new EndScreen(winner, game.getPlayer(), game.getOpponent());
            Scene scene = new Scene(endScreen.getView(), 1240, 820);
            Main.setScene(scene);
        }
    }

    private void captureState() {
        Monster player = game.getPlayer();
        Monster opponent = game.getOpponent();

        previousPlayerEnergy = player.getEnergy();
        previousOpponentEnergy = opponent.getEnergy();
        previousPlayerPosition = player.getPosition();
        previousOpponentPosition = opponent.getPosition();
        previousPlayerShield = player.isShielded();
        previousOpponentShield = opponent.isShielded();
        previousPlayerFrozen = player.isFrozen();
        previousOpponentFrozen = opponent.isFrozen();
        previousPlayerConfusion = player.getConfusionTurns();
        previousOpponentConfusion = opponent.getConfusionTurns();
        previousPlayerRole = player.getRole();
        previousOpponentRole = opponent.getRole();
    }

    private void logStateChanges() {
        logMonsterChanges(game.getPlayer(), previousPlayerEnergy, previousPlayerPosition, previousPlayerShield,
                previousPlayerFrozen, previousPlayerConfusion, previousPlayerRole);
        logMonsterChanges(game.getOpponent(), previousOpponentEnergy, previousOpponentPosition, previousOpponentShield,
                previousOpponentFrozen, previousOpponentConfusion, previousOpponentRole);
        captureState();
    }

    private void logMonsterChanges(Monster monster, int oldEnergy, int oldPosition, boolean oldShield,
                                   boolean oldFrozen, int oldConfusion, Role oldRole) {
        if (monster.getPosition() != oldPosition) {
            gameScreen.addInfoLog(monster.getName() + " position changed: " + oldPosition + " → " + monster.getPosition() + ".");
        }

        if (monster.getEnergy() != oldEnergy) {
            int diff = monster.getEnergy() - oldEnergy;
            if (diff > 0) {
                gameScreen.addSuccessLog(monster.getName() + " gained " + diff + " energy.");
            } else {
                gameScreen.addWarningLog(monster.getName() + " lost " + Math.abs(diff) + " energy.");
            }
        }

        if (oldShield && !monster.isShielded() && monster.getEnergy() == oldEnergy) {
            gameScreen.addSuccessLog(monster.getName() + " shield blocked energy loss.");
        }

        if (!oldShield && monster.isShielded()) {
            gameScreen.addSuccessLog(monster.getName() + " gained shield.");
        }

        if (!oldFrozen && monster.isFrozen()) {
            gameScreen.addWarningLog(monster.getName() + " is now frozen.");
        }

        if (oldFrozen && !monster.isFrozen()) {
            gameScreen.addInfoLog(monster.getName() + " is no longer frozen.");
        }

        if (monster.getConfusionTurns() != oldConfusion) {
            if (monster.getConfusionTurns() > 0) {
                gameScreen.addWarningLog(monster.getName() + " is confused for " + monster.getConfusionTurns() + " turn(s).");
            } else {
                gameScreen.addInfoLog(monster.getName() + " confusion ended.");
            }
        }

        if (monster.getRole() != oldRole) {
            gameScreen.addWarningLog(monster.getName() + " role changed: " + oldRole + " → " + monster.getRole() + ".");
        }
    }

    private void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content == null ? "Invalid action." : content);
        alert.showAndWait();
    }

    private String safeMessage(Exception ex) {
        return ex.getMessage() == null || ex.getMessage().isBlank() ? ex.getClass().getSimpleName() : ex.getMessage();
    }
}
