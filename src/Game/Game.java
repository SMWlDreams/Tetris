package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Game {
    @FXML
    private Pane pane;
    @FXML
    private Text score;
    @FXML
    private Text lines;
    @FXML
    private Text level;

    private boolean moving = false;
    private Timeline timeline;
    private Board board = new Board();
    private KeyCode lastPressedKey;
    private boolean rotate = false;
    private KeyCode lastRotationKey;
    private boolean down = false;

    public void parseInput(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.SLASH) && !rotate) {
            lastRotationKey = KeyCode.SLASH;
            board.rotate(true);
            rotate = true;
        } else if (keyEvent.getCode().equals(KeyCode.PERIOD) && !rotate) {
            lastRotationKey = KeyCode.PERIOD;
            board.rotate(false);
            rotate = true;
        } else if (!down && keyEvent.getCode().equals(KeyCode.S)) {
            down = true;
            board.setDown(true);
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
        }
        if (keyEvent.getCode().equals(lastPressedKey)) {
            moving = false;
            board.stopMovement();
        }
        if (down && keyEvent.getCode().equals(KeyCode.S)) {
            down = false;
            board.setDown(false);
        }
    }

    public void run() {
        board.init(pane, 0);
        board.setController(this);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/60.0), e -> board.nextFrame(pane));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void updateInfo(int lines, int score, int level) {
        if (lines < 10) {
            this.lines.setText("0" + lines);
        } else {
            this.lines.setText("" + lines);
        }
        if (score < 100) {
            this.score.setText("0000" + score);
        } else if (score < 1000) {
            this.score.setText("000" + score);
        } else if (score < 10000) {
            this.score.setText("00" + score);
        } else if (score < 100000) {
            this.score.setText("0" + score);
        } else {
            this.score.setText(score + "");
        }
        this.level.setText("" + level);
    }
}
