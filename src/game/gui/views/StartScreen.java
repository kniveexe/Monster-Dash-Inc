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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;

public class StartScreen {
    private VBox view;
    private ToggleGroup roleGroup;

    public StartScreen() {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(50));
        view.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("DoorDasH:\nScare vs Laugh Touchdown");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setTextFill(javafx.scene.paint.Color.WHITE);
        title.setStyle("-fx-text-alignment: center;");

        Label prompt = new Label("Choose Your Side:");
        prompt.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        prompt.setTextFill(javafx.scene.paint.Color.LIGHTGRAY);

        roleGroup = new ToggleGroup();
        RadioButton scarerBtn = new RadioButton("SCARER");
        scarerBtn.setToggleGroup(roleGroup);
        scarerBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        scarerBtn.setFont(Font.font(18));
        scarerBtn.setUserData(Role.SCARER);

        RadioButton laugherBtn = new RadioButton("LAUGHER");
        laugherBtn.setToggleGroup(roleGroup);
        laugherBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        laugherBtn.setFont(Font.font(18));
        laugherBtn.setUserData(Role.LAUGHER);

        Button startBtn = new Button("Start Game");
        startBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10 30;");
        startBtn.setOnMouseEntered(e -> startBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10 30;"));
        startBtn.setOnMouseExited(e -> startBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10 30;"));

        startBtn.setOnAction(e -> {
            if (roleGroup.getSelectedToggle() != null) {
                Role selectedRole = (Role) roleGroup.getSelectedToggle().getUserData();
                startGame(selectedRole);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Role Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select either SCARER or LAUGHER before starting the game.");
                alert.showAndWait();
            }
        });

        Button rulesBtn = new Button("Game Instructions");
        rulesBtn.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        rulesBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-background-radius: 5px;");
        rulesBtn.setOnAction(e -> showInstructions());

        view.getChildren().addAll(title, prompt, scarerBtn, laugherBtn, startBtn, rulesBtn);
    }

    public VBox getView() {
        return view;
    }

    private void startGame(Role role) {
        try {
            GameController controller = new GameController(role);
            Scene gameScene = new Scene(controller.getView(), 1200, 800);
            Main.getPrimaryStage().setScene(gameScene);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Game");
            alert.setContentText("Failed to load game data files: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Instructions");
        alert.setHeaderText("How to Play DoorDasH");
        alert.setContentText("1. Choose to be a SCARER or LAUGHER.\n"
                + "2. Roll the dice to move your monster on the 100-cell board.\n"
                + "3. Land on cells to trigger their effects.\n"
                + "4. Use powerups by spending energy before rolling.\n"
                + "5. Reach cell 99 to win!");
        alert.showAndWait();
    }
}
