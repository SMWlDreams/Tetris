package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    @FXML
    private ImageView view;
    @FXML
    private Text linesT;
    @FXML
    private Text top;
    @FXML
    private Text best;
    @FXML
    private Text scoreT;
    @FXML
    private Text next;
    @FXML
    private Text levelT;
    @FXML
    private Text stats;

    private String state;
    private Music music;
    private List<Node> gameNodes;
    private boolean moving = false;
    private Timeline timeline;
    private Board board = new Board();
    private KeyCode lastPressedKey;
    private boolean rotate = false;
    private KeyCode lastRotationKey;
    private boolean down = false;
    private int[] totalUses = new int[7];
    private boolean stopped = false;
    private Main main;
    private String[] names = new String[3];
    private int[] highScore = new int[3];
    private String restartProperty;
    private List<Node> menuNodes;

    public void parseInput(KeyEvent keyEvent) {
        switch (state) {
            case "Title":
                board.loadMainMenu();
                break;
            case "Menu":
                start();
                break;
            case "Game":
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
                break;
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

    public void setMain(Main main) {
        this.main = main;
    }

    public void run() {
        loadHighScores();
        Node[] node = {score, lines, level, l, s, t, j, z, o, i, linesT, best, scoreT, next, levelT, stats, top};
        gameNodes = new ArrayList<>();
        gameNodes.addAll(Arrays.asList(node));
        Node[] nodes = {};
        menuNodes = new ArrayList<>();
        menuNodes.addAll(Arrays.asList(nodes));
        board.setController(this);
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/60.0), e -> board.nextFrame(pane));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setBGImage("Title");
        music = new Music();
        main.setBGAudioPlayer(music);
        board.setBGAudioPlayer(music);
    }

    public void stop() {
        timeline.stop();
        stopped = true;
        int i = Integer.parseInt(score.getText());
        if (i >= highScore[0]) {
            restartProperty = "Register 1";
        } else if (i >= highScore[1]) {
            restartProperty = "Register 2";
        } else if (i >= highScore[2]) {
            restartProperty = "Register 3";
        } else {
            restartProperty = "Menu";
        }
    }

    public void updateInfo(int lines, int score, int level) {
        if (lines < 10) {
            this.lines.setText("0" + lines);
        } else {
            this.lines.setText("" + lines);
        }
        if (score < 10) {
            this.score.setText("00000" + score);
        } else if (score < 100) {
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

    public void setBGImage(String element) {
        switch (element) {
            case "Title":
                state = "Title";
                for (Node n : gameNodes) {
                    n.setVisible(false);
                }
                for (Node n : menuNodes) {
                    n.setVisible(false);
                }
                view.setImage(new Image("title_screen_temp.png"));
                break;
            case "Menu":
                state = "Menu";
                for (Node n : gameNodes) {
                    n.setVisible(false);
                }
                for (Node n : menuNodes) {
                    n.setVisible(true);
                }
                view.setImage(new Image("main_menu.png"));
                break;
            case "Game":
                state = "Game";
                for (Node n : gameNodes) {
                    n.setVisible(true);
                }
                for (Node n : menuNodes) {
                    n.setVisible(true);
                }
                view.setImage(new Image("background.png"));
                break;
        }
    }

    public void tetris() {
        view.setImage(new Image("tetris_flash.png"));
    }

    public void resetBG() {
        view.setImage(new Image("background.png"));
    }

    private void loadHighScores() {
        Scanner in = new Scanner(getClass().getResourceAsStream("High Scores.txt"));
        names[0] = in.next();
        in.nextLine();
        highScore[0] = in.nextInt();
        in.nextLine();
        names[1] = in.next();
        in.nextLine();
        highScore[1] = in.nextInt();
        in.nextLine();
        names[2] = in.next();
        in.nextLine();
        highScore[2] = in.nextInt();
        System.out.println("debug");
    }

    private void start() {
        board.init(pane, 0);
        setBGImage("Game");
        music.selectTrack(2);
        music.play();
    }

    private void restart() {
        board = new Board();
        pane.getChildren().clear();
        pane.getChildren().add(view);
        view.setImage(new Image("background.png"));
        pane.getChildren().addAll(new ArrayList<>(gameNodes));
        board.init(pane, 0);
        board.setController(this);
        for (int i = 0; i < 7; i++) {
            totalUses[i] = -1;
            updateStats(i);
        }
        updateInfo(0, 0, 0);
        stopped = false;
        timeline.play();
    }
}
