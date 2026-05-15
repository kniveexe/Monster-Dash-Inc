package game.gui.views;

import game.engine.Board;
import game.engine.Role;
import game.engine.cells.*;
import game.engine.monsters.Monster;
import game.gui.controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class GameScreen {
    private BorderPane view;
    private GridPane boardGrid;
    private VBox sidePanel;
    private VBox logBox;
    private ScrollPane logScroll;

    private Label currentPlayerLabel;
    private Label p1Stats;
    private Label p2Stats;

    private GameController controller;
    private List<String> actionLog = new ArrayList<>();

    public GameScreen(GameController controller) {
        this.controller = controller;
        view = new BorderPane();
        view.setStyle("-fx-background-color: #ecf0f1;");

        // Board Area
        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setPadding(new Insets(20));

        // Side Panel Area
        sidePanel = new VBox(20);
        sidePanel.setPadding(new Insets(20));
        sidePanel.setPrefWidth(350);
        sidePanel.setStyle("-fx-background-color: #34495e;");

        Label title = new Label("Game Stats");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        currentPlayerLabel = new Label("Current Turn: ");
        currentPlayerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        currentPlayerLabel.setTextFill(Color.GOLD);

        p1Stats = new Label();
        p1Stats.setFont(Font.font("Arial", 14));
        p1Stats.setTextFill(Color.WHITE);

        p2Stats = new Label();
        p2Stats.setFont(Font.font("Arial", 14));
        p2Stats.setTextFill(Color.WHITE);

        Button rollBtn = new Button("Roll Dice");
        rollBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        rollBtn.setStyle("-fx-background-color: linear-gradient(#3498db, #2980b9); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);");
        rollBtn.setOnMouseEntered(e -> rollBtn.setStyle("-fx-background-color: linear-gradient(#5dade2, #3498db); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 8, 0, 0, 8);"));
        rollBtn.setOnMouseExited(e -> rollBtn.setStyle("-fx-background-color: linear-gradient(#3498db, #2980b9); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"));
        rollBtn.setOnAction(e -> controller.rollDice());

        Button powerupBtn = new Button("Use Powerup");
        powerupBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        powerupBtn.setStyle("-fx-background-color: linear-gradient(#e67e22, #d35400); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);");
        powerupBtn.setOnMouseEntered(e -> powerupBtn.setStyle("-fx-background-color: linear-gradient(#eb984e, #e67e22); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 8, 0, 0, 8);"));
        powerupBtn.setOnMouseExited(e -> powerupBtn.setStyle("-fx-background-color: linear-gradient(#e67e22, #d35400); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 25; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"));
        powerupBtn.setOnAction(e -> controller.usePowerup());

        logBox = new VBox(5);
        logScroll = new ScrollPane(logBox);
        logScroll.setPrefHeight(200);
        logScroll.setStyle("-fx-background: #2c3e50; -fx-background-color: #2c3e50;");
        logScroll.setFitToWidth(true);

        sidePanel.getChildren().addAll(title, currentPlayerLabel, p1Stats, p2Stats, rollBtn, powerupBtn, new Label("Action Log:") {{ setTextFill(Color.WHITE); }}, logScroll);

        view.setCenter(boardGrid);
        view.setRight(sidePanel);
    }

    public BorderPane getView() {
        return view;
    }

    public void addLogMessage(String message) {
        Label msgLbl = new Label("- " + message);
        msgLbl.setTextFill(Color.LIGHTGRAY);
        msgLbl.setWrapText(true);
        logBox.getChildren().add(msgLbl);
        logScroll.setVvalue(1.0);
    }

    public void updateStats(Monster current, Monster opponent) {
        currentPlayerLabel.setText("Current Turn:\n" + current.getName() + " (" + current.getRole() + ")");
        
        p1Stats.setText(formatMonsterStats(current) + (current.isFrozen() ? "\n[FROZEN - Turn will be skipped]" : ""));
        p2Stats.setText(formatMonsterStats(opponent));
    }

    private String formatMonsterStats(Monster m) {
        return String.format("Name: %s\nType: %s\nRole: %s (Original: %s)\nEnergy: %d\nPosition: %d\nShielded: %b\nConfused: %d turns left",
                m.getName(), m.getClass().getSimpleName(), m.getRole(), m.getOriginalRole(), m.getEnergy(), m.getPosition(), m.isShielded(), m.getConfusionTurns());
    }

    public void updateBoard(Board board, Monster player1, Monster player2) {
        boardGrid.getChildren().clear();
        Cell[][] cells = board.getBoardCells();

        // Constants for a 10x10 board
        int rows = 10;
        int cols = 10;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if (cell == null) continue; // In case of uninitialized cells

                int index = getIndexFromRowCol(r, c, cols);

                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(75, 75);

                String bgColor = "#ffffff";
                String textInfo = index + "\n";

                if (cell instanceof DoorCell) {
                    DoorCell dc = (DoorCell) cell;
                    bgColor = dc.getRole() == Role.SCARER ? "#9b59b6" : "#f1c40f"; // Purple or Yellow
                    textInfo += "DOOR\n" + dc.getEnergy();
                    if (dc.isActivated()) {
                        bgColor = dc.getRole() == Role.SCARER ? "#d2b4de" : "#f9e79f"; // Lighter colors when exhausted
                        textInfo += "\n(Used)";
                    }
                } else if (cell instanceof MonsterCell) {
                    bgColor = "#e74c3c"; // Red
                    Monster stationed = ((MonsterCell) cell).getMonster();
                    if (stationed != null) {
                        textInfo += stationed.getName().substring(0, Math.min(5, stationed.getName().length()));
                    } else {
                        textInfo += "MONSTER";
                    }
                } else if (cell instanceof CardCell) {
                    bgColor = "#3498db"; // Blue
                    textInfo += "CARD";
                } else if (cell instanceof ConveyorBelt) {
                    bgColor = "#95a5a6"; // Gray
                    textInfo += "BELT";
                } else if (cell instanceof ContaminationSock) {
                    bgColor = "#2ecc71"; // Green
                    textInfo += "SOCK";
                } else {
                    bgColor = "#ecf0f1"; // Normal Cell - light gray
                }

                cellPane.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: #7f8c8d; -fx-border-width: 1;");

                Label infoLbl = new Label(textInfo);
                infoLbl.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                infoLbl.setTextAlignment(TextAlignment.CENTER);
                
                // Set text color for better contrast
                if (bgColor.equals("#9b59b6") || bgColor.equals("#e74c3c") || bgColor.equals("#3498db")) {
                    infoLbl.setTextFill(Color.WHITE);
                } else {
                    infoLbl.setTextFill(Color.BLACK);
                }

                cellPane.getChildren().add(infoLbl);

                // Add player markers if they are on this cell
                HBox playersBox = new HBox(5);
                playersBox.setAlignment(Pos.BOTTOM_CENTER);
                playersBox.setPadding(new Insets(2));
                if (player1.getPosition() == index) {
                    Label p1Token = createPlayerToken(player1, Color.BLUE);
                    playersBox.getChildren().add(p1Token);
                }
                if (player2.getPosition() == index) {
                    Label p2Token = createPlayerToken(player2, Color.RED);
                    playersBox.getChildren().add(p2Token);
                }
                
                cellPane.getChildren().add(playersBox);

                // JavaFX GridPane adds (col, row). Note we render row 9 at visual top (row 0), and row 0 at bottom (row 9).
                boardGrid.add(cellPane, c, (rows - 1) - r);
            }
        }
    }

    private Label createPlayerToken(Monster m, Color color) {
        Label token = new Label(m.getName().substring(0, 1));
        token.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        token.setTextFill(Color.WHITE);
        String style = "-fx-background-color: " + toHexString(color) + "; -fx-background-radius: 15; -fx-min-width: 25; -fx-min-height: 25; -fx-alignment: center; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 2, 0, 0, 1);";
        if (m.isConfused()) {
            style += "-fx-border-color: #f1c40f; -fx-border-width: 2; -fx-border-radius: 15;";
        }
        token.setStyle(style);
        return token;
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private int getIndexFromRowCol(int row, int col, int maxCols) {
        if (row % 2 == 0) {
            return row * maxCols + col;
        } else {
            return row * maxCols + ((maxCols - 1) - col);
        }
    }
}
