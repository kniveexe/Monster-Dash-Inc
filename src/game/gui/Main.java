package game.gui;

import game.gui.views.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static final String CSS_PATH = "/game/gui/styles/monster.css";

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("DoorDasH: Scare vs Laugh Touchdown");

        StartScreen startScreen = new StartScreen();
        Scene scene = new Scene(startScreen.getView(), 1240, 820);
        applyCss(scene);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1180);
        primaryStage.setMinHeight(760);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setScene(Scene scene) {
        applyCss(scene);
        primaryStage.setScene(scene);
    }

    public static void applyCss(Scene scene) {
        try {
            String css = Main.class.getResource(CSS_PATH).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception ignored) {
            // The GUI still works if the CSS file was not copied by the IDE.
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
