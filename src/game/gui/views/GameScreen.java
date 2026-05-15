package game.gui.views;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;
import game.engine.cells.CardCell;
import game.engine.cells.Cell;
import game.engine.cells.ContaminationSock;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.DoorCell;
import game.engine.cells.MonsterCell;
import game.engine.cells.TransportCell;
import game.engine.monsters.Monster;
import game.gui.controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class GameScreen {

    private final BorderPane view;
    private final GridPane boardGrid;
    private final VBox logBox;
    private final ScrollPane logScroll;

    private final Label turnLabel;
    private final Label currentRoleLabel;
    private final Label diceLabel;
    private final Label lastCardNameLabel;
    private final Label lastCardEffectLabel;
    private final Label deckCountLabel;
    private final Label playerStatsLabel;
    private final Label opponentStatsLabel;

    private final GameController controller;

    public GameScreen(GameController controller) {
        this.controller = controller;

        view = new BorderPane();
        view.getStyleClass().add("game-root");

        VBox mainArea = new VBox(12);
        mainArea.setPadding(new Insets(14));
        mainArea.getStyleClass().add("main-area");

        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        VBox titleBox = new VBox(2);
        Label title = new Label("DoorDasH Board");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("All 100 cells, cell types, doors, cards, effects and monsters are visible.");
        subtitle.getStyleClass().add("muted-text");
        titleBox.getChildren().addAll(title, subtitle);

        turnLabel = new Label("Current Turn: -");
        turnLabel.getStyleClass().add("status-pill");

        currentRoleLabel = new Label("Role: -");
        currentRoleLabel.getStyleClass().add("status-pill");

        topBar.getChildren().addAll(titleBox, spacer(), turnLabel, currentRoleLabel);

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.getStyleClass().add("board-grid");

        mainArea.getChildren().addAll(topBar, boardGrid);
        VBox.setVgrow(boardGrid, Priority.ALWAYS);

        VBox sidePanel = new VBox(12);
        sidePanel.setPadding(new Insets(14));
        sidePanel.setPrefWidth(380);
        sidePanel.getStyleClass().add("side-panel");

        Label panelTitle = new Label("Control Center");
        panelTitle.getStyleClass().add("panel-title");

        diceLabel = cardValue("Last Dice Roll", "-");
        lastCardNameLabel = cardValue("Last Card", "None");
        lastCardEffectLabel = new Label("No card drawn yet.");
        lastCardEffectLabel.setWrapText(true);
        lastCardEffectLabel.getStyleClass().add("card-effect-text");

        deckCountLabel = new Label("Deck: - cards");
        deckCountLabel.getStyleClass().add("deck-label");

        playerStatsLabel = new Label();
        playerStatsLabel.setWrapText(true);
        playerStatsLabel.getStyleClass().add("monster-card-player");

        opponentStatsLabel = new Label();
        opponentStatsLabel.setWrapText(true);
        opponentStatsLabel.getStyleClass().add("monster-card-opponent");

        Button powerupButton = new Button("Use Powerup Before Rolling");
        powerupButton.getStyleClass().add("secondary-button");
        powerupButton.setMaxWidth(Double.MAX_VALUE);
        powerupButton.setOnAction(event -> controller.usePowerup());

        Button rollButton = new Button("Roll Dice");
        rollButton.getStyleClass().add("primary-button");
        rollButton.setMaxWidth(Double.MAX_VALUE);
        rollButton.setOnAction(event -> controller.rollDice());

        Label legendTitle = new Label("Legend");
        legendTitle.getStyleClass().add("small-title");
        Label legend = new Label("Purple: Scarer Door | Yellow: Laugher Door | Blue: Card | Red: Monster | Gray: Conveyor | Green: Sock | White: Normal | Dimmed Door: Activated/Exhausted");
        legend.setWrapText(true);
        legend.getStyleClass().add("legend-text");

        Label logTitle = new Label("Action Log");
        logTitle.getStyleClass().add("small-title");

        logBox = new VBox(7);
        logBox.setPadding(new Insets(8));
        logScroll = new ScrollPane(logBox);
        logScroll.setFitToWidth(true);
        logScroll.setPrefHeight(175);
        logScroll.getStyleClass().add("log-scroll");

        sidePanel.getChildren().addAll(
                panelTitle,
                diceLabel,
                lastCardNameLabel,
                lastCardEffectLabel,
                deckCountLabel,
                playerStatsLabel,
                opponentStatsLabel,
                powerupButton,
                rollButton,
                legendTitle,
                legend,
                logTitle,
                logScroll
        );

        view.setCenter(mainArea);
        view.setRight(sidePanel);
    }

    private Label cardValue(String caption, String value) {
        Label label = new Label(caption + "\n" + value);
        label.getStyleClass().add("metric-card");
        label.setWrapText(true);
        return label;
    }

    private javafx.scene.layout.Region spacer() {
        javafx.scene.layout.Region region = new javafx.scene.layout.Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    public BorderPane getView() {
        return view;
    }

    public void setDiceText(String text) {
        diceLabel.setText("Last Dice Roll\n" + text);
    }

    public void setLastCard(String name, String effect) {
        lastCardNameLabel.setText("Last Card\n" + name);
        lastCardEffectLabel.setText(effect);
    }

    public void updateDeckCount(int count) {
        deckCountLabel.setText("Deck: " + count + " shuffled card(s) remaining");
    }

    public void updateStats(Monster player, Monster opponent, Monster current) {
        turnLabel.setText("Current Turn: " + current.getName());
        currentRoleLabel.setText("Current Role: " + current.getRole());

        playerStatsLabel.setText("PLAYER\n" + formatMonster(player));
        opponentStatsLabel.setText("OPPONENT\n" + formatMonster(opponent));
    }

    private String formatMonster(Monster monster) {
        return "Name: " + monster.getName()
                + "\nType: " + monster.getClass().getSimpleName()
                + "\nOriginal Role: " + monster.getOriginalRole()
                + "\nCurrent Role: " + monster.getRole() + (monster.isConfused() ? "  [CONFUSED]" : "")
                + "\nEnergy: " + monster.getEnergy()
                + "\nPosition: " + monster.getPosition()
                + "\nEffects: " + effectsText(monster);
    }

    private String effectsText(Monster monster) {
        StringBuilder builder = new StringBuilder();
        if (monster.isShielded()) builder.append("Shield ");
        if (monster.isFrozen()) builder.append("Freeze ");
        if (monster.isConfused()) builder.append("Confusion(").append(monster.getConfusionTurns()).append(") ");
        if (builder.length() == 0) return "None";
        return builder.toString().trim();
    }

    public void updateBoard(Board board, Monster player, Monster opponent) {
        boardGrid.getChildren().clear();
        Cell[][] cells = board.getBoardCells();

        for (int index = 0; index < Constants.BOARD_SIZE; index++) {
            int[] rowCol = indexToRowCol(index);
            int row = rowCol[0];
            int col = rowCol[1];
            int visualRow = (Constants.BOARD_ROWS - 1) - row;

            StackPane cellPane = createCellPane(cells[row][col], index, player, opponent);
            GridPane.setHgrow(cellPane, Priority.ALWAYS);
            GridPane.setVgrow(cellPane, Priority.ALWAYS);
            boardGrid.add(cellPane, col, visualRow);
        }
    }

    private StackPane createCellPane(Cell cell, int index, Monster player, Monster opponent) {
        StackPane pane = new StackPane();
        pane.setMinSize(64, 54);
        pane.setPrefSize(72, 58);
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.getStyleClass().add("board-cell");

        Label cellText = new Label();
        cellText.setTextAlignment(TextAlignment.CENTER);
        cellText.setAlignment(Pos.CENTER);
        cellText.setWrapText(true);
        cellText.getStyleClass().add("cell-text");

        String text = index + "\nNORMAL";

        if (cell instanceof DoorCell) {
            DoorCell door = (DoorCell) cell;
            if (door.getRole() == Role.SCARER) {
                pane.getStyleClass().add("scarer-door-cell");
            } else {
                pane.getStyleClass().add("laugher-door-cell");
            }
            text = index + "\n" + (door.getRole() == Role.SCARER ? "S-DOOR" : "L-DOOR") + "\nE:" + door.getEnergy();
            if (door.isActivated()) {
                pane.getStyleClass().add("activated-cell");
                text += "\nUSED";
            }
        } else if (cell instanceof CardCell) {
            pane.getStyleClass().add("card-cell");
            text = index + "\nCARD";
        } else if (cell instanceof ContaminationSock) {
            pane.getStyleClass().add("sock-cell");
            text = index + "\nSOCK\n" + ((TransportCell) cell).getEffect();
        } else if (cell instanceof ConveyorBelt) {
            pane.getStyleClass().add("conveyor-cell");
            text = index + "\nBELT\n+" + ((TransportCell) cell).getEffect();
        } else if (cell instanceof MonsterCell) {
            pane.getStyleClass().add("monster-cell");
            Monster stationed = ((MonsterCell) cell).getCellMonster();
            text = index + "\nMONSTER\n" + shortName(stationed == null ? "?" : stationed.getName());
        } else {
            pane.getStyleClass().add("normal-cell");
        }

        cellText.setText(text);
        pane.getChildren().add(cellText);

        HBox tokens = new HBox(4);
        tokens.setAlignment(Pos.BOTTOM_CENTER);
        tokens.setPadding(new Insets(0, 0, 3, 0));

        if (player.getPosition() == index) {
            tokens.getChildren().add(createMonsterToken("P", player, true));
        }

        if (opponent.getPosition() == index) {
            tokens.getChildren().add(createMonsterToken("O", opponent, false));
        }

        pane.getChildren().add(tokens);
        return pane;
    }

    private Label createMonsterToken(String label, Monster monster, boolean player) {
        Label token = new Label(label + ":" + monster.getClass().getSimpleName().charAt(0));
        token.setAlignment(Pos.CENTER);
        token.getStyleClass().add(player ? "player-token" : "opponent-token");
        if (monster.isConfused()) {
            token.getStyleClass().add("confused-token");
        }
        return token;
    }

    private int[] indexToRowCol(int index) {
        int row = index / Constants.BOARD_COLS;
        int rawCol = index % Constants.BOARD_COLS;
        int col = (row % 2 == 0) ? rawCol : (Constants.BOARD_COLS - 1) - rawCol;
        return new int[]{row, col};
    }

    private String shortName(String name) {
        if (name == null || name.isBlank()) return "?";
        return name.length() <= 6 ? name : name.substring(0, 6);
    }

    public void addInfoLog(String message) {
        addLogMessage(message, "log-info");
    }

    public void addSuccessLog(String message) {
        addLogMessage(message, "log-success");
    }

    public void addWarningLog(String message) {
        addLogMessage(message, "log-warning");
    }

    private void addLogMessage(String message, String styleClass) {
        Label label = new Label("• " + message);
        label.setWrapText(true);
        label.getStyleClass().addAll("log-message", styleClass);
        logBox.getChildren().add(label);
        logScroll.setVvalue(1.0);
    }
}
