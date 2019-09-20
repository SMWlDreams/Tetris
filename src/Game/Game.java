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
    private KeyCode lastPressedKey;
    private boolean rotate = false;
    private KeyCode lastRotationKey;

    public void parseInput(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.SLASH) && !rotate) {
            lastRotationKey = KeyCode.SLASH;
            board.rotate(true);
            rotate = true;
        } else if (keyEvent.getCode().equals(KeyCode.PERIOD) && !rotate) {
            lastRotationKey = KeyCode.PERIOD;
            board.rotate(false);
            rotate = true;
        } else if (!moving) {
            lastPressedKey = keyEvent.getCode();
            if (lastPressedKey.equals(KeyCode.A)) {
                board.moveLeft();
                moving = true;
            } else if (lastPressedKey.equals(KeyCode.D)) {
                board.moveRight();
                moving = true;
            }
        }
    }

    public void stopMovement(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(lastRotationKey)) {
            rotate = false;
        } if (keyEvent.getCode().equals(lastPressedKey)) {
            moving = false;
            board.stopMovement();
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
