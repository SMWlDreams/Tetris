package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    @FXML
    private Text menu;
    @FXML
    private Text musicText;
    @FXML
    private Text nesMusic;
    @FXML
    private Text gbaMusic;
    @FXML
    private Text noMusic;
    @FXML
    private Text highScores;
    @FXML
    private GridPane grid;
    @FXML
    private GridPane scoreGrid;
    @FXML
    private Text name1;
    @FXML
    private Text name2;
    @FXML
    private Text name3;
    @FXML
    private Text score1;
    @FXML
    private Text score2;
    @FXML
    private Text score3;

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
    private int musicSelection = 0;

    public void setMusicSelection(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getX());
        System.out.println(mouseEvent.getY());
        if (state.equalsIgnoreCase("Menu")) {
            if (mouseEvent.getX() >= 375 && mouseEvent.getX() <= 520) {
                if (mouseEvent.getSceneY() >= 145 && mouseEvent.getSceneY() <= 180) {
//                    nesMusic.setStyle("-fx-text-fill: red");
//                    gbaMusic.setStyle("-fx-text-fill: white");
//                    noMusic.setStyle("-fx-text-fill: white");
                    nesMusic.setFill(Color.RED);
                    gbaMusic.setFill(Color.WHITE);
                    noMusic.setFill(Color.WHITE);
                    musicSelection = 0;
                    music.selectTrack(musicSelection);
                    music.play();
                } else if (mouseEvent.getSceneY() >= 195 && mouseEvent.getSceneY() <= 230) {
//                    nesMusic.setStyle("-fx-text-fill: white");
//                    gbaMusic.setStyle("-fx-text-fill: red");
//                    noMusic.setStyle("-fx-text-fill: white");
                    nesMusic.setFill(Color.WHITE);
                    gbaMusic.setFill(Color.RED);
                    noMusic.setFill(Color.WHITE);
                    musicSelection = 1;
                    music.selectTrack(musicSelection);
                    music.play();
                } else if (mouseEvent.getSceneY() >= 245 && mouseEvent.getSceneY() <= 280) {
//                    nesMusic.setStyle("-fx-text-fill: white");
//                    gbaMusic.setStyle("-fx-text-fill: white");
//                    noMusic.setStyle("-fx-text-fill: red");
                    nesMusic.setFill(Color.WHITE);
                    gbaMusic.setFill(Color.WHITE);
                    noMusic.setFill(Color.RED);
                    musicSelection = 3;
                    music.stop();
                }
            }
        }
    }

    public void parseInput(KeyEvent keyEvent) {
        switch (state) {
            case "Title":
                board.loadMainMenu();
                break;
            case "Menu":
                start();
                break;
            case "Register":
                switch (restartProperty) {
                    case "Register 1":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name1.getText().length() > 0) {
                                name1.setText(name1.getText().substring(0, name1.getText().length() - 2));
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[0] = name1.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else {
                            if (name1.getText().length() < 8) {
                                name1.setText(name1.getText() + keyEvent.getCode().getChar());
                            }
                        }
                        break;
                    case "Register 2":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name2.getText().length() > 0) {
                                name2.setText(name2.getText().substring(0, name2.getText().length() - 2));
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[1] = name2.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else {
                            if (name2.getText().length() < 8) {
                                name2.setText(name2.getText() + keyEvent.getCode().getChar());
                            }
                        }
                        break;
                    case "Register 3":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name3.getText().length() > 0) {
                                name3.setText(name3.getText().substring(0, name3.getText().length() - 2));
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[2] = name3.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else {
                            if (name3.getText().length() < 8) {
                                name3.setText(name3.getText() + keyEvent.getCode().getChar());
                            }
                        }
                        break;
                }
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
                } else {
                    restart();
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
//        Node[] nodes = {menu, musicText, nesMusic, gbaMusic, noMusic, highScores, grid,
//                scoreGrid, name1, name2, name3, score1, score2, score3};
        Node[] nodes = {menu, musicText, nesMusic, gbaMusic, noMusic, highScores, grid,
                scoreGrid};
        menuNodes = new ArrayList<>();
        menuNodes.addAll(Arrays.asList(nodes));
        timeline = new Timeline();
        board.setController(this);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/60.0), e -> board.nextFrame(pane));
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setBGImage("Title");
        music = new Music();
        main.setBGAudioPlayer(music);
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
        if (score > highScore[0]) {
            if (score < 100000) {
                best.setText("0" + score);
            } else {
                best.setText(score + "");
            }
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
                    n.setVisible(false);
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
        name1.setText(names[0]);
        name2.setText(names[1]);
        name3.setText(names[2]);
        score1.setText(highScore[0] + "");
        score2.setText(highScore[1] + "");
        score3.setText(highScore[2] + "");
    }

    private void writeHighScores() {

    }

    private void start() {
        music.stop();
        if (highScore[0] < 100000) {
            best.setText("0" + highScore[0]);
        } else {
            best.setText("" + highScore[0]);
        }
        board = new Board();
        board.setController(this);
        board.init(pane, 0);
        board.setBGAudioPlayer(music);
        setBGImage("Game");
        if (musicSelection != 3) {
            music.selectTrack(musicSelection);
            music.play();
        }
        updateInfo(0, 0, 0);
    }

    private void restart() {
        pane.getChildren().clear();
        pane.getChildren().add(view);
        pane.getChildren().addAll(new ArrayList<>(gameNodes));
        pane.getChildren().addAll(new ArrayList<>(menuNodes));
        for (int i : totalUses) {
            i = 0;
        }
        setBGImage("Menu");
        String[] newNames = new String[3];
        switch (restartProperty) {
            case "Register 1":
                System.arraycopy(names, 0, newNames, 1, 2);
                names = newNames;
                state = "Register";
                break;
            case "Register 2":
                newNames[0] = names[0];
                newNames[2] = names[1];
                names = newNames;
                state = "Register";
                break;
            case "Register 3":
                System.arraycopy(names, 0, newNames, 0, 2);
                names = newNames;
                state = "Register";
                break;
            default:
                break;
        }
        timeline.play();
        stopped = false;
//        board = new Board();
//        pane.getChildren().add(view);
//        view.setImage(new Image("backeground.png"));
//        pane.getChildren().addAll(new ArrayList<>(gameNodes));
//        board.init(pane, 0);
//        board.setController(this);
//        for (int i = 0; i < 7; i++) {
//            totalUses[i] = -1;
//            updateStats(i);
//        }
//        updateInfo(0, 0, 0);
//        stopped = false;
//        timeline.play();
    }
}