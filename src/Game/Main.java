package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private Music music;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("Tetris.fxml"));
        Game gameController = loader.getController();
        Scene scene = new Scene(root);
        gameController.setMain(this);
        scene.setOnKeyPressed(gameController::parseInput);
        scene.setOnKeyReleased(gameController::stopMovement);
        stage.setOnShown(e -> gameController.run());
        stage.setOnCloseRequest(e -> music.stop());
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
    }

    public void setBGAudioPlayer(Music music) {
        this.music = music;
    }
}
