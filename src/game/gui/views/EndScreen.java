package game.gui.views;

import game.engine.monsters.Monster;
import game.gui.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndScreen {

    private final VBox view;

    public EndScreen(Monster winner, Monster player, Monster opponent) {
        view = new VBox(22);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(40));
        view.getStyleClass().add("end-root");

        Label title = new Label("GAME WON");
        title.getStyleClass().add("hero-title");

        Label winnerLabel = new Label(
                "Winning Monster: " + winner.getName()
                        + "\nOriginal Role: " + winner.getOriginalRole()
                        + "\nCurrent Role: " + winner.getRole()
        );
        winnerLabel.getStyleClass().add("winner-text");

        Label finalStats = new Label(
                "Final Energy\n"
                        + player.getName() + ": " + player.getEnergy()
                        + "\n"
                        + opponent.getName() + ": " + opponent.getEnergy()
        );
        finalStats.getStyleClass().add("final-stats");

        Button returnButton = new Button("Return to Start Window");
        returnButton.getStyleClass().add("primary-button");
        returnButton.setOnAction(event -> {
            StartScreen startScreen = new StartScreen();
            Scene scene = new Scene(startScreen.getView(), 1240, 820);
            Main.setScene(scene);
        });

        view.getChildren().addAll(title, winnerLabel, finalStats, returnButton);
    }

    public VBox getView() {
        return view;
    }
}
