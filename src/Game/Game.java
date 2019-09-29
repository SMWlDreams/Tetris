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
    @FXML
    private Text l;
    @FXML
    private Text s;
    @FXML
    private Text t;
    @FXML
    private Text j;
    @FXML
    private Text z;
    @FXML
    private Text o;
    @FXML
    private Text i;

    private boolean moving = false;
    private Timeline timeline;
    private Board board = new Board();
    private KeyCode lastPressedKey;
    private boolean rotate = false;
    private KeyCode lastRotationKey;
    private boolean down = false;
    private int[] totalUses = new int[7];
    private boolean stopped = false;

    public void parseInput(KeyEvent keyEvent) {
        if (!stopped) {
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

    public void stop() {
        timeline.stop();
        stopped = true;
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

    public void updateStats(int identifier) {
        int a;
        switch (identifier) {
            case 0:
                a = ++totalUses[identifier];
                if (a < 10) {
                    i.setText("00" + a);
                } else if (a < 100) {
                    i.setText("0" + a);
                } else {
                    i.setText("" + a);
                }
                break;
            case 1:
                a = ++totalUses[identifier];
                if (a < 10) {
                    j.setText("00" + a);
                } else if (a < 100) {
                    j.setText("0" + a);
                } else {
                    j.setText("" + a);
                }
                break;
            case 2:
                a = ++totalUses[identifier];
                if (a < 10) {
                    l.setText("00" + a);
                } else if (a < 100) {
                    l.setText("0" + a);
                } else {
                    l.setText("" + a);
                }
                break;
            case 3:
                a = ++totalUses[identifier];
                if (a < 10) {
                    o.setText("00" + a);
                } else if (a < 100) {
                    o.setText("0" + a);
                } else {
                    o.setText("" + a);
                }
                break;
            case 4:
                a = ++totalUses[identifier];
                if (a < 10) {
                    s.setText("00" + a);
                } else if (a < 100) {
                    s.setText("0" + a);
                } else {
                    s.setText("" + a);
                }
                break;
            case 5:
                a = ++totalUses[identifier];
                if (a < 10) {
                    t.setText("00" + a);
                } else if (a < 100) {
                    t.setText("0" + a);
                } else {
                    t.setText("" + a);
                }
                break;
            case 6:
                a = ++totalUses[identifier];
                if (a < 10) {
                    z.setText("00" + a);
                } else if (a < 100) {
                    z.setText("0" + a);
                } else {
                    z.setText("" + a);
                }
                break;
        }
    }
}
