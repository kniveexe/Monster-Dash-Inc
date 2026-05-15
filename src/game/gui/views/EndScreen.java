package game.gui.views;

import game.engine.monsters.Monster;
import game.gui.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class EndScreen {
    private VBox view;

    public EndScreen(Monster winner) {
        view = new VBox(30);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(50));
        view.setStyle("-fx-background-color: #2c3e50;");

        Label gameOverLabel = new Label("GAME OVER!");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameOverLabel.setTextFill(Color.GOLD);

        String winnerText = "Winner: " + winner.getName() + "\nRole: " + winner.getOriginalRole();
        Label winnerLabel = new Label(winnerText);
        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        winnerLabel.setTextFill(Color.WHITE);
        winnerLabel.setStyle("-fx-text-alignment: center;");

        Label energyLabel = new Label("Final Energy: " + winner.getEnergy());
        energyLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        energyLabel.setTextFill(Color.LIGHTGREEN);

        Button returnBtn = new Button("Return to Start");
        returnBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        returnBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10 30;");
        returnBtn.setOnAction(e -> {
            StartScreen startScreen = new StartScreen();
            Scene scene = new Scene(startScreen.getView(), 1000, 800);
            Main.getPrimaryStage().setScene(scene);
        });

        view.getChildren().addAll(gameOverLabel, winnerLabel, energyLabel, returnBtn);
    }

    public VBox getView() {
        return view;
    }
}
