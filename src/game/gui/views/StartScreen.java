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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StartScreen {

    private final BorderPane view;
    private final ToggleGroup roleGroup;

    public StartScreen() {
        view = new BorderPane();
        view.getStyleClass().add("start-root");

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(36));
        container.setMaxWidth(920);
        container.getStyleClass().add("glass-card");

        Label title = new Label("DoorDasH");
        title.getStyleClass().add("hero-title");

        Label subtitle = new Label("Scare vs Laugh Touchdown");
        subtitle.getStyleClass().add("hero-subtitle");

        Label instructionsTitle = new Label("Game Instructions");
        instructionsTitle.getStyleClass().add("section-title");

        Label instructions = new Label(
                "Choose SCARER or LAUGHER, then play the game from the selected side. "
                        + "Use a powerup before rolling if you have enough energy. Roll the dice, move across the 100-cell board, "
                        + "trigger doors, monster cells, card cells, conveyor belts and contamination socks. "
                        + "The board, turn, dice result, card effect, energy changes, shield blocks, confusion and freeze effects are shown during play."
        );
        instructions.setWrapText(true);
        instructions.getStyleClass().add("instructions-text");

        roleGroup = new ToggleGroup();
        RadioButton scarerButton = createRoleButton("SCARER", Role.SCARER);
        RadioButton laugherButton = createRoleButton("LAUGHER", Role.LAUGHER);

        HBox roles = new HBox(22, scarerButton, laugherButton);
        roles.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("primary-button");
        startButton.setOnAction(event -> startGame());

        HBox checklist = new HBox(14,
                checklistItem("100 cells"),
                checklistItem("Visible roles"),
                checklistItem("Cards + effects"),
                checklistItem("Status tracking"),
                checklistItem("Safe errors")
        );
        checklist.setAlignment(Pos.CENTER);

        container.getChildren().addAll(title, subtitle, instructionsTitle, instructions, roles, startButton, checklist);

        view.setCenter(container);
    }

    private Label checklistItem(String text) {
        Label label = new Label("✓ " + text);
        label.getStyleClass().add("check-pill");
        return label;
    }

    private RadioButton createRoleButton(String text, Role role) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(roleGroup);
        button.setUserData(role);
        button.getStyleClass().add("role-radio");
        return button;
    }

    public BorderPane getView() {
        return view;
    }

    private void startGame() {
        if (roleGroup.getSelectedToggle() == null) {
            showWarning("Select a Side", "Please select SCARER or LAUGHER before starting the game.");
            return;
        }

        Role selectedRole = (Role) roleGroup.getSelectedToggle().getUserData();

        try {
            GameController controller = new GameController(selectedRole);
            Scene scene = new Scene(controller.getView(), 1240, 820);
            Main.setScene(scene);
        } catch (IOException ex) {
            showWarning("Loading Error", "Could not load game data files.\n" + safeMessage(ex));
        } catch (Exception ex) {
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
        return ex.getMessage() == null || ex.getMessage().isBlank() ? ex.getClass().getSimpleName() : ex.getMessage();
    }
}
