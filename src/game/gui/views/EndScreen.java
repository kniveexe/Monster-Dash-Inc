package game.gui.views;

import game.engine.monsters.Monster;
import game.gui.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EndScreen {

    private final VBox view;

    public EndScreen(Monster winner, Monster player, Monster opponent) {
        view = new VBox(24);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(48));
        view.getStyleClass().add("end-root");

        // ── GAME WON headline ──
        Label headline = new Label("GAME WON!");
        headline.getStyleClass().add("hero-title");

        // ── Winner banner ──
        VBox winnerBanner = new VBox(6);
        winnerBanner.setAlignment(Pos.CENTER);
        winnerBanner.getStyleClass().add("winner-banner");

        Label winnerHeader = new Label("WINNER");
        winnerHeader.getStyleClass().add("winner-announce");

        Label winnerName = new Label(winner.getName());
        winnerName.getStyleClass().add("winner-name");

        String roleText = winner.getRole().toString();
        if (winner.isConfused()) {
            roleText += " (Confused — originally " + winner.getOriginalRole() + ")";
        }
        Label winnerRole = new Label(roleText);
        winnerRole.getStyleClass().add("winner-role");

        Label winnerType = new Label(winner.getClass().getSimpleName()
                + " · Original Role: " + winner.getOriginalRole());
        winnerType.getStyleClass().add("winner-type");

        winnerBanner.getChildren().addAll(winnerHeader, winnerName, winnerRole, winnerType);

        // ── Final energy comparison ──
        HBox statsRow = new HBox(20);
        statsRow.setAlignment(Pos.CENTER);

        boolean playerWon = (winner == player);
        statsRow.getChildren().addAll(
                buildStatBox(player,   "PLAYER",   playerWon),
                buildStatBox(opponent, "OPPONENT", !playerWon)
        );

        // ── Return button ──
        Button returnBtn = new Button("Return to Start");
        returnBtn.getStyleClass().add("primary-button");
        returnBtn.setOnAction(e -> {
            StartScreen startScreen = new StartScreen();
            Scene scene = new Scene(startScreen.getView(), 1240, 820);
            Main.setScene(scene);
        });

        view.getChildren().addAll(headline, winnerBanner, statsRow, returnBtn);
    }

    private VBox buildStatBox(Monster monster, String label, boolean isWinner) {
        VBox box = new VBox(6);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("final-stats-box");
        box.setMinWidth(240);

        Label titleLabel = new Label(label);
        titleLabel.getStyleClass().add("final-stats-title");
        titleLabel.setStyle(isWinner ? "-fx-text-fill: #86efac;" : "-fx-text-fill: #64748b;");

        Label nameLabel = new Label(monster.getName());
        nameLabel.getStyleClass().add("final-stats-item");

        Label energyLabel = new Label(monster.getEnergy() + " energy");
        energyLabel.getStyleClass().add("final-stats-item");
        if (isWinner) energyLabel.getStyleClass().add("final-stats-winner-energy");

        Label roleLabel = new Label(monster.getRole().toString()
                + " · " + monster.getClass().getSimpleName());
        roleLabel.getStyleClass().add("final-stats-sub");

        Label posLabel = new Label("Reached cell " + monster.getPosition());
        posLabel.getStyleClass().add("final-stats-sub");

        box.getChildren().addAll(titleLabel, nameLabel, energyLabel, roleLabel, posLabel);
        return box;
    }

    public VBox getView() {
        return view;
    }
}
