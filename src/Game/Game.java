package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Game {
    @FXML
    Pane pane;

    private Board board = new Board();

    public void parseInput(KeyEvent keyEvent) {

    }

    public void run() {
        board.drawAllShapes(pane);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/60.0), e -> board.nextFrame(pane));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
