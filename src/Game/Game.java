package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Game {
    @FXML
    private Pane pane;

    private boolean moving = false;
    private Timeline timeline;
    private Board board = new Board();

    public void parseInput(KeyEvent keyEvent) {
        if (!moving) {
            if (keyEvent.getCode().equals(KeyCode.A)) {
                board.moveLeft();
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0/60.0), e -> board.updateDasAcceleration()));
            } else if (keyEvent.getCode().equals(KeyCode.D)) {
                board.moveRight();
            }
            moving = true;
        }
    }

    public void run() {
        board.drawAllShapes(pane);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/60.0), e -> board.nextFrame(pane));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
