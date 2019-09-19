package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResourceAsStream("Tetris.fxml"));
        Game gameController = loader.getController();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> gameController.parseInput(e));
        stage.setOnShown(e -> gameController.run());
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
    }
}
