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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Three-column game view:
 *   LEFT  – turn info, dice, deck count, action buttons, legend
 *   CENTER – top bar + 10x10 board
 *   RIGHT  – player/opponent stat cards, last card, scrollable action log
 */
public class GameScreen {

    private final BorderPane view;
    private final GridPane boardGrid;
    private final VBox logBox;
    private final ScrollPane logScroll;

    // Left-sidebar labels
    private final Label turnNameLabel;
    private final Label turnRoleLabel;
    private final Label diceNumberLabel;
    private final Label deckCountLabel;

    // Top-bar labels
    private final Label topTurnLabel;
    private final Label topRoleLabel;

    // Right-sidebar stat boxes (rebuilt on each refresh)
    private final VBox playerStatBox;
    private final VBox opponentStatBox;

    // Right-sidebar card display
    private final Label cardNameLabel;
    private final Label cardEffectLabel;

    private final GameController controller;

    // ═══════════════════════════════════════════════════════════
    // Constructor
    // ═══════════════════════════════════════════════════════════

    public GameScreen(GameController controller) {
        this.controller = controller;

        // ── Initialise display components ──

        topTurnLabel = new Label("Turn: —");
        topTurnLabel.getStyleClass().add("status-pill");

        topRoleLabel = new Label("Role: —");
        topRoleLabel.getStyleClass().add("status-pill");

        turnNameLabel = new Label("—");
        turnNameLabel.getStyleClass().add("turn-value-text");
        turnNameLabel.setWrapText(true);

        turnRoleLabel = new Label("—");
        turnRoleLabel.getStyleClass().add("turn-value-text");
        turnRoleLabel.setStyle("-fx-font-size: 11px;");

        diceNumberLabel = new Label("—");
        diceNumberLabel.getStyleClass().add("dice-number");
        diceNumberLabel.setMaxWidth(Double.MAX_VALUE);
        diceNumberLabel.setAlignment(Pos.CENTER);

        deckCountLabel = new Label("Deck: — cards");
        deckCountLabel.getStyleClass().add("deck-count-label");
        deckCountLabel.setMaxWidth(Double.MAX_VALUE);

        playerStatBox = new VBox(4);
        playerStatBox.getStyleClass().addAll("monster-stat-card", "player-stat-card");

        opponentStatBox = new VBox(4);
        opponentStatBox.getStyleClass().addAll("monster-stat-card", "opponent-stat-card");

        cardNameLabel = new Label("None drawn yet");
        cardNameLabel.getStyleClass().add("card-name-label");
        cardNameLabel.setWrapText(true);

        cardEffectLabel = new Label("No card effect.");
        cardEffectLabel.getStyleClass().add("card-effect-label");
        cardEffectLabel.setWrapText(true);

        logBox = new VBox(5);
        logBox.getStyleClass().add("log-inner");
        logBox.setPadding(new Insets(6));
        logBox.setFillWidth(true);

        logScroll = new ScrollPane(logBox);
        logScroll.setFitToWidth(true);
        logScroll.setPrefHeight(200);
        logScroll.getStyleClass().add("log-scroll");
        logScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(logScroll, Priority.ALWAYS);

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(4);
        boardGrid.setVgap(4);
        boardGrid.getStyleClass().add("board-grid");
        VBox.setVgrow(boardGrid, Priority.ALWAYS);

        // Equal-width columns and equal-height rows so the board fills available space
        for (int i = 0; i < Constants.BOARD_COLS; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            boardGrid.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < Constants.BOARD_ROWS; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            boardGrid.getRowConstraints().add(rc);
        }

        // ── Assemble layout ──
        view = new BorderPane();
        view.getStyleClass().add("game-root");
        view.setLeft(buildLeftSidebar());
        view.setCenter(buildCenter());
        view.setRight(buildRightSidebar());
    }

    // ═══════════════════════════════════════════════════════════
    // Layout builders
    // ═══════════════════════════════════════════════════════════

    private VBox buildLeftSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("left-sidebar");
        sidebar.setPrefWidth(205);
        sidebar.setMinWidth(190);
        sidebar.setFillWidth(true);

        // Mini title
        Label miniTitle = new Label("DoorDasH");
        miniTitle.getStyleClass().add("game-mini-title");

        sidebar.getChildren().add(miniTitle);

        // ── Current turn ──
        addSectionLabel(sidebar, "CURRENT TURN");
        VBox turnBox = new VBox(3);
        turnBox.getStyleClass().add("turn-display");
        Label tnCaption = new Label("Monster");
        tnCaption.getStyleClass().add("turn-label-text");
        turnBox.getChildren().addAll(tnCaption, turnNameLabel, turnRoleLabel);
        sidebar.getChildren().add(turnBox);

        // ── Dice ──
        addSectionLabel(sidebar, "DICE ROLL");
        VBox diceBox = new VBox(4);
        diceBox.getStyleClass().add("dice-box");
        diceBox.setAlignment(Pos.CENTER);
        diceBox.setFillWidth(true);
        Label diceCaption = new Label("Last Roll");
        diceCaption.getStyleClass().add("dice-label-text");
        diceCaption.setAlignment(Pos.CENTER);
        diceCaption.setMaxWidth(Double.MAX_VALUE);
        diceBox.getChildren().addAll(diceCaption, diceNumberLabel);
        sidebar.getChildren().add(diceBox);

        // ── Deck ──
        addSectionLabel(sidebar, "CARD DECK");
        sidebar.getChildren().add(deckCountLabel);

        // ── Actions ──
        addSectionLabel(sidebar, "ACTIONS");
        Button powerupBtn = new Button("Use Powerup");
        powerupBtn.getStyleClass().add("secondary-button");
        powerupBtn.setMaxWidth(Double.MAX_VALUE);
        powerupBtn.setOnAction(e -> controller.usePowerup());

        Button rollBtn = new Button("Roll Dice");
        rollBtn.getStyleClass().add("primary-button");
        rollBtn.setMaxWidth(Double.MAX_VALUE);
        rollBtn.setOnAction(e -> controller.rollDice());

        sidebar.getChildren().addAll(powerupBtn, rollBtn);

        // ── Spacer pushes legend to bottom ──
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().add(spacer);

        // ── Legend ──
        addSectionLabel(sidebar, "CELL LEGEND");
        sidebar.getChildren().add(buildLegend());

        return sidebar;
    }

    private VBox buildCenter() {
        // Top bar
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");
        topBar.setFillHeight(true);

        VBox titleGroup = new VBox(2);
        Label boardTitle = new Label("Game Board");
        boardTitle.getStyleClass().add("page-title");
        Label boardSub = new Label("All 100 cells, types and states visible");
        boardSub.getStyleClass().add("muted-text");
        titleGroup.getChildren().addAll(boardTitle, boardSub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(titleGroup, spacer, topTurnLabel, topRoleLabel);

        VBox center = new VBox(8, topBar, boardGrid);
        center.setPadding(new Insets(10));
        center.getStyleClass().add("center-area");
        center.setFillWidth(true);
        return center;
    }

    private VBox buildRightSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("right-sidebar");
        sidebar.setPrefWidth(355);
        sidebar.setMinWidth(330);
        sidebar.setFillWidth(true);

        // ── Player stat card ──
        addSectionLabel(sidebar, "PLAYER");
        sidebar.getChildren().add(playerStatBox);

        // ── Opponent stat card ──
        addSectionLabel(sidebar, "OPPONENT");
        sidebar.getChildren().add(opponentStatBox);

        // ── Last card drawn ──
        addSectionLabel(sidebar, "LAST CARD DRAWN");
        VBox cardPanel = new VBox(5, cardNameLabel, cardEffectLabel);
        cardPanel.getStyleClass().add("card-panel");
        sidebar.getChildren().add(cardPanel);

        // ── Action log ──
        addSectionLabel(sidebar, "ACTION LOG");
        sidebar.getChildren().add(logScroll);

        return sidebar;
    }

    private VBox buildLegend() {
        VBox box = new VBox(3);
        box.getStyleClass().add("legend-box");
        box.getChildren().addAll(
                legendRow("S-DOOR", "#c4b5fd", " Scarer Door"),
                legendRow("L-DOOR", "#fde68a", " Laugher Door"),
                legendRow("CARD  ", "#93c5fd", " Card Cell"),
                legendRow("MONST ", "#fca5a5", " Monster Cell"),
                legendRow("BELT  ", "#94a3b8", " Conveyor Belt"),
                legendRow("SOCK  ", "#86efac", " Contam. Sock"),
                legendRow("NORM  ", "#dde1e7", " Normal Cell"),
                legendRow("USED  ", "#ef4444", " Activated Door")
        );
        return box;
    }

    private Label legendRow(String tag, String color, String label) {
        Label l = new Label("[" + tag + "]" + label);
        l.getStyleClass().add("legend-item");
        l.setStyle("-fx-text-fill: " + color + ";");
        return l;
    }

    private void addSectionLabel(VBox parent, String text) {
        Label label = new Label(text);
        label.getStyleClass().add("sidebar-section-title");
        parent.getChildren().add(label);
    }

    // ═══════════════════════════════════════════════════════════
    // Public update API (called by GameController)
    // ═══════════════════════════════════════════════════════════

    public BorderPane getView() {
        return view;
    }

    public void setDiceText(String text) {
        diceNumberLabel.setText(text);
    }

    public void setLastCard(String name, String effect) {
        cardNameLabel.setText(name);
        cardEffectLabel.setText(effect);
    }

    public void updateDeckCount(int count) {
        deckCountLabel.setText("Deck: " + count + " card(s) left");
    }

    public void updateStats(Monster player, Monster opponent, Monster current) {
        topTurnLabel.setText("Turn: " + current.getName());
        topRoleLabel.setText("Role: " + current.getRole() + (current.isConfused() ? " [CONFUSED]" : ""));
        turnNameLabel.setText(current.getName());
        turnRoleLabel.setText(current.getRole().toString()
                + (current.isConfused() ? " — CONFUSED" : ""));

        fillStatCard(playerStatBox,   player,   true);
        fillStatCard(opponentStatBox, opponent, false);
    }

    private void fillStatCard(VBox box, Monster monster, boolean isPlayer) {
        box.getChildren().clear();

        // Header
        Label header = new Label(isPlayer ? "PLAYER" : "OPPONENT");
        header.getStyleClass().add("stat-card-header");
        header.setStyle(isPlayer ? "-fx-text-fill: #60a5fa;" : "-fx-text-fill: #f87171;");

        // Name
        Label name = new Label(monster.getName());
        name.getStyleClass().add("stat-card-name");
        name.setWrapText(true);

        // Stat rows
        Label type     = statRow("Type",      monster.getClass().getSimpleName());
        Label origRole = statRow("Orig. Role", monster.getOriginalRole().toString());
        Label curRole  = statRow("Curr. Role", monster.getRole().toString()
                + (monster.isConfused() ? " [!]" : ""));
        Label energy   = statRow("Energy",    String.valueOf(monster.getEnergy()));
        Label position = statRow("Position",  "Cell " + monster.getPosition());

        // Effect pills
        HBox effects = new HBox(5);
        effects.setAlignment(Pos.CENTER_LEFT);
        if (monster.isShielded()) {
            effects.getChildren().add(effectPill("SHIELD", "effect-shield"));
        }
        if (monster.isFrozen()) {
            effects.getChildren().add(effectPill("FROZEN", "effect-frozen"));
        }
        if (monster.isConfused()) {
            effects.getChildren().add(effectPill("CONFUSED " + monster.getConfusionTurns(), "effect-confused"));
        }

        box.getChildren().addAll(header, name, type, origRole, curRole, energy, position);
        if (!effects.getChildren().isEmpty()) {
            box.getChildren().add(effects);
        }
    }

    private Label statRow(String key, String value) {
        // Pad key to 11 chars for monospace alignment
        StringBuilder sb = new StringBuilder(key);
        while (sb.length() < 11) sb.append(' ');
        Label label = new Label(sb + ": " + value);
        label.getStyleClass().add("stat-card-row");
        label.setWrapText(true);
        return label;
    }

    private Label effectPill(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().addAll("effect-pill", styleClass);
        return label;
    }

    // ═══════════════════════════════════════════════════════════
    // Board rendering
    // ═══════════════════════════════════════════════════════════

    public void updateBoard(Board board, Monster player, Monster opponent) {
        boardGrid.getChildren().clear();
        Cell[][] cells = board.getBoardCells();

        for (int index = 0; index < Constants.BOARD_SIZE; index++) {
            int[] rc        = indexToRowCol(index);
            int   row       = rc[0];
            int   col       = rc[1];
            int   visualRow = (Constants.BOARD_ROWS - 1) - row;   // flip: row 0 at bottom

            StackPane cell = createCellPane(cells[row][col], index, player, opponent);
            GridPane.setHgrow(cell, Priority.ALWAYS);
            GridPane.setVgrow(cell, Priority.ALWAYS);
            boardGrid.add(cell, col, visualRow);
        }
    }

    private StackPane createCellPane(Cell cell, int index, Monster player, Monster opponent) {
        StackPane pane = new StackPane();
        pane.setMinSize(56, 50);
        pane.setPrefSize(65, 57);
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.getStyleClass().add("board-cell");

        // ── Determine cell type and build label text ──
        Label cellText = new Label();
        cellText.setTextAlignment(TextAlignment.CENTER);
        cellText.setAlignment(Pos.CENTER);
        cellText.setWrapText(true);
        cellText.getStyleClass().add("cell-text");

        String text;

        if (cell instanceof DoorCell) {
            DoorCell door   = (DoorCell) cell;
            boolean scarer  = door.getRole() == Role.SCARER;
            pane.getStyleClass().add(scarer ? "scarer-door-cell" : "laugher-door-cell");
            text = index + "\n" + (scarer ? "S-DOOR" : "L-DOOR") + "\nE:" + door.getEnergy();
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
            text = index + "\nNORMAL";
        }

        cellText.setText(text);
        pane.getChildren().add(cellText);

        // ── Tokens ──
        boolean playerHere   = player.getPosition()   == index;
        boolean opponentHere = opponent.getPosition() == index;

        if (playerHere || opponentHere) {
            HBox tokens = new HBox(3);
            tokens.setAlignment(Pos.BOTTOM_CENTER);
            tokens.setPadding(new Insets(0, 0, 2, 0));
            if (playerHere)   tokens.getChildren().add(buildToken("P", player,   true));
            if (opponentHere) tokens.getChildren().add(buildToken("O", opponent, false));
            pane.getChildren().add(tokens);
        }

        return pane;
    }

    private Label buildToken(String prefix, Monster monster, boolean isPlayer) {
        char typeLetter = monster.getClass().getSimpleName().charAt(0);
        Label token = new Label(prefix + ":" + typeLetter);
        token.setAlignment(Pos.CENTER);
        token.getStyleClass().add(isPlayer ? "player-token" : "opponent-token");
        if (monster.isConfused()) {
            token.getStyleClass().add("confused-token");
        }
        return token;
    }

    /** Converts a linear board index [0..99] to the serpentine (boustrophedon) row/col. */
    private int[] indexToRowCol(int index) {
        int row    = index / Constants.BOARD_COLS;
        int rawCol = index % Constants.BOARD_COLS;
        int col    = (row % 2 == 0) ? rawCol : (Constants.BOARD_COLS - 1) - rawCol;
        return new int[]{row, col};
    }

    private String shortName(String name) {
        if (name == null || name.isBlank()) return "?";
        return name.length() <= 8 ? name : name.substring(0, 8);
    }

    // ═══════════════════════════════════════════════════════════
    // Action log
    // ═══════════════════════════════════════════════════════════

    public void addInfoLog(String message) {
        addLog(message, "log-info");
    }

    public void addSuccessLog(String message) {
        addLog(message, "log-success");
    }

    public void addWarningLog(String message) {
        addLog(message, "log-warning");
    }

    private void addLog(String message, String styleClass) {
        Label label = new Label("• " + message);
        label.setWrapText(true);
        label.getStyleClass().addAll("log-message", styleClass);
        logBox.getChildren().add(label);
        logScroll.setVvalue(1.0);
    }
}
