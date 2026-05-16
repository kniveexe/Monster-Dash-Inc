package game.gui.views;

import game.engine.Role;
import game.gui.Main;
import game.gui.controllers.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class StartScreen {

    private final BorderPane view;
    private Role selectedRole = null;
    private VBox scarerCard;
    private VBox laugherCard;
    private Button startButton;

    public StartScreen() {
        view = new BorderPane();
        view.getStyleClass().add("start-root");

        VBox container = new VBox(22);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(36));
        container.setMaxWidth(940);

        // ── Title ──
        Label title = new Label("DoorDasH");
        title.getStyleClass().add("hero-title");

        Label subtitle = new Label("Scare vs Laugh Touchdown");
        subtitle.getStyleClass().add("hero-subtitle");

        // ── Instructions ──
        VBox instructionsBox = new VBox(6);
        instructionsBox.getStyleClass().add("start-instructions-box");
        instructionsBox.setMaxWidth(820);

        Label instrTitle = new Label("HOW TO PLAY");
        instrTitle.getStyleClass().add("instructions-title");

        Label instructions = new Label(
                "Choose your side — SCARER or LAUGHER — then roll the dice to advance across the 100-cell board. " +
                "Land on Door Cells to gain energy for your role or lose it for the enemy role. " +
                "Card Cells draw powerful cards: shields, energy steal, position swap, confusion, and more. " +
                "Monster Cells trigger powerups or energy swaps with stationed monsters. " +
                "Conveyor Belts leap you forward; Contamination Socks drag you back (shield absorbs). " +
                "Spend 500 energy before rolling to activate your monster's unique Powerup. " +
                "The first to reach or pass cell 99 wins!"
        );
        instructions.setWrapText(true);
        instructions.getStyleClass().add("instructions-text");
        instructionsBox.getChildren().addAll(instrTitle, instructions);

        // ── Role picker label ──
        Label chooseLabel = new Label("CHOOSE YOUR SIDE");
        chooseLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 900; -fx-text-fill: #64748b; -fx-padding: 4 0 0 0;");

        // ── Role cards ──
        scarerCard = buildRoleCard(Role.SCARER);
        laugherCard = buildRoleCard(Role.LAUGHER);

        HBox rolesRow = new HBox(24, scarerCard, laugherCard);
        rolesRow.setAlignment(Pos.CENTER);

        // ── Start button ──
        startButton = new Button("Start Game");
        startButton.getStyleClass().add("primary-button");
        startButton.setDisable(true);
        startButton.setOnAction(e -> startGame());

        // ── Feature checklist ──
        HBox checklist = new HBox(10,
                pill("100 Cells"),
                pill("Role Logic"),
                pill("Cards & Effects"),
                pill("Status Tracking"),
                pill("Action Log"),
                pill("Safe Errors")
        );
        checklist.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                title, subtitle,
                instructionsBox,
                chooseLabel, rolesRow,
                startButton,
                checklist
        );

        view.setCenter(container);
    }

    private VBox buildRoleCard(Role role) {
        boolean isScarer = (role == Role.SCARER);
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(270);
        card.setMinWidth(230);
        card.getStyleClass().addAll("role-card", isScarer ? "role-card-scarer" : "role-card-laugher");

        Label icon = new Label(isScarer ? "👾" : "😂");  // 👾 😂
        icon.getStyleClass().add("role-icon");

        Label name = new Label(isScarer ? "SCARER" : "LAUGHER");
        name.getStyleClass().add("role-name");
        name.setStyle(isScarer ? "-fx-text-fill: #c4b5fd;" : "-fx-text-fill: #fde68a;");

        Label desc = new Label(isScarer
                ? "Master of fear. Gain energy from\nScarer Doors. Purple powerhouse."
                : "King of laughter. Gain energy from\nLaugher Doors. Golden champion."
        );
        desc.setWrapText(true);
        desc.setTextAlignment(TextAlignment.CENTER);
        desc.setAlignment(Pos.CENTER);
        desc.getStyleClass().add("role-desc");

        card.getChildren().addAll(icon, name, desc);
        card.setOnMouseClicked(e -> selectRole(role));
        return card;
    }

    private void selectRole(Role role) {
        selectedRole = role;

        // Remove selection highlight from both
        scarerCard.getStyleClass().removeAll("role-card-scarer-selected", "role-card-laugher-selected");
        laugherCard.getStyleClass().removeAll("role-card-scarer-selected", "role-card-laugher-selected");

        // Add to chosen
        if (role == Role.SCARER) {
            scarerCard.getStyleClass().add("role-card-scarer-selected");
        } else {
            laugherCard.getStyleClass().add("role-card-laugher-selected");
        }

        startButton.setDisable(false);
    }

    private Label pill(String text) {
        Label label = new Label("✓ " + text);
        label.getStyleClass().add("check-pill");
        return label;
    }

    public BorderPane getView() {
        return view;
    }

    private void startGame() {
        if (selectedRole == null) {
            showWarning("Select a Side", "Please select SCARER or LAUGHER before starting the game.");
            return;
        }

        try {
            GameController controller = new GameController(selectedRole);
            Scene scene = new Scene(controller.getView(), 1240, 820);
            Main.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
            showWarning("Loading Error", "Could not load game data files.\n" + safeMessage(ex));
        } catch (Exception ex) {
            ex.printStackTrace();
            showWarning("Unexpected Error", safeMessage(ex));
        }
    }

    private void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String safeMessage(Exception ex) {
        return (ex.getMessage() == null || ex.getMessage().isBlank())
                ? ex.getClass().getSimpleName() : ex.getMessage();
    }
}
