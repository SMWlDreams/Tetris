package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
    @FXML
    private Text zero;
    @FXML
    private Text one;
    @FXML
    private Text two;
    @FXML
    private Text three;
    @FXML
    private Text four;
    @FXML
    private Text five;
    @FXML
    private Text six;
    @FXML
    private Text seven;
    @FXML
    private Text eight;
    @FXML
    private Text nine;

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
    private int selectedLevel = 10;
    private boolean pause = false;

    public void setMusicSelection(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getX());
        System.out.println(mouseEvent.getY());
        if (state.equalsIgnoreCase("Menu")) {
            if (mouseEvent.getX() >= 375 && mouseEvent.getX() <= 520) {
                if (mouseEvent.getSceneY() >= 145 && mouseEvent.getSceneY() <= 180 && musicSelection != 0) {
                    music.stop();
                    board.startFrameDelay();
                    nesMusic.setFill(Color.RED);
                    gbaMusic.setFill(Color.WHITE);
                    noMusic.setFill(Color.WHITE);
                    musicSelection = 0;
                    music.selectTrack(musicSelection);
                } else if (mouseEvent.getSceneY() >= 195 && mouseEvent.getSceneY() <= 230 && musicSelection != 1) {
                    music.stop();
                    board.startFrameDelay();
                    nesMusic.setFill(Color.WHITE);
                    gbaMusic.setFill(Color.RED);
                    noMusic.setFill(Color.WHITE);
                    musicSelection = 1;
                    music.selectTrack(musicSelection);
                } else if (mouseEvent.getSceneY() >= 245 && mouseEvent.getSceneY() <= 280) {
                    nesMusic.setFill(Color.WHITE);
                    gbaMusic.setFill(Color.WHITE);
                    noMusic.setFill(Color.RED);
                    musicSelection = 3;
                    music.stop();
                }
            } else {
                if (mouseEvent.getY() >= 95 && mouseEvent.getY() <= 129) {
                    if (mouseEvent.getX() < 78) {
                        //Do nothing
                    } else if (mouseEvent.getX() <= 116) {
                        if (selectedLevel == 0) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 0;
                            zero.setFill(Color.RED);
                        }
                    } else if (mouseEvent.getX() <= 154) {
                        if (selectedLevel == 1) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 1;
                            one.setFill(Color.RED);
                        }
                    } else if (mouseEvent.getX() <= 193) {
                        if (selectedLevel == 2) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 2;
                            two.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 231) {
                        if (selectedLevel == 3) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 3;
                            three.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 270) {
                        if (selectedLevel == 4) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 4;
                            four.setFill(Color.RED);
                        }

                    }
                } else if (mouseEvent.getY() >= 130 && mouseEvent.getY() < 175) {
                    if (mouseEvent.getX() < 78) {
                        //Do nothing
                    } else if (mouseEvent.getX() <= 116) {
                        if (selectedLevel == 5) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 5;
                            five.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 154) {
                        if (selectedLevel == 6) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 6;
                            six.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 193) {
                        if (selectedLevel == 7) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 7;
                            seven.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 231) {
                        if (selectedLevel == 8) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 8;
                            eight.setFill(Color.RED);
                        }

                    } else if (mouseEvent.getX() <= 270) {
                        if (selectedLevel == 9) {
                            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                selectedLevel += 10;
                            }
                            start();
                        } else {
                            clearSelectedLevel();
                            selectedLevel = 9;
                            nine.setFill(Color.RED);
                        }

                    }
                }
            }
        }
    }

    private void clearSelectedLevel() {
        switch (selectedLevel) {
            case 0:
                zero.setFill(Color.WHITE);
                break;
            case 1:
                one.setFill(Color.WHITE);
                break;
            case 2:
                two.setFill(Color.WHITE);
                break;
            case 3:
                three.setFill(Color.WHITE);
                break;
            case 4:
                four.setFill(Color.WHITE);
                break;
            case 5:
                five.setFill(Color.WHITE);
                break;
            case 6:
                six.setFill(Color.WHITE);
                break;
            case 7:
                seven.setFill(Color.WHITE);
                break;
            case 8:
                eight.setFill(Color.WHITE);
                break;
            case 9:
                nine.setFill(Color.WHITE);
                break;
        }
    }

    public void parseInput(KeyEvent keyEvent) {
        switch (state) {
            case "Title":
                board.loadMainMenu();
                break;
            case "Menu":
                break;
            case "Register":
                switch (restartProperty) {
                    case "Register 1":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name1.getText().length() > 0) {
                                if (name1.getText().length() == 1) {
                                    name1.setText("");
                                } else {
                                    name1.setText(name1.getText().substring(0, name1.getText().length() - 1));
                                }
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[0] = name1.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else if (!(keyEvent.getCode().equals(KeyCode.SPACE))) {
                            if (name1.getText().length() < 7) {
                                if (keyEvent.isShiftDown()) {
                                    name1.setText(name1.getText() + keyEvent.getText().toUpperCase());
                                } else {
                                    name1.setText(name1.getText() + keyEvent.getText());
                                }
                            }
                        }
                        break;
                    case "Register 2":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name2.getText().length() > 0) {
                                if (name2.getText().length() == 1) {
                                    name2.setText("");
                                } else {
                                    name2.setText(name2.getText().substring(0, name2.getText().length() - 1));
                                }
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[1] = name2.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else if (!(keyEvent.getCode().equals(KeyCode.SPACE))) {
                            if (name2.getText().length() < 7) {
                                if (keyEvent.isShiftDown()) {
                                    name2.setText(name2.getText() + keyEvent.getText().toUpperCase());
                                } else {
                                    name2.setText(name2.getText() + keyEvent.getText());
                                }
                            }
                        }
                        break;
                    case "Register 3":
                        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                            if (name3.getText().length() > 0) {
                                if (name3.getText().length() == 1) {
                                    name3.setText("");
                                } else {
                                    name3.setText(name3.getText().substring(0, name3.getText().length() - 1));
                                }
                            }
                        } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            names[2] = name3.getText();
                            writeHighScores();
                            setBGImage("Menu");
                        } else if (!(keyEvent.getCode().equals(KeyCode.SPACE))) {
                            if (name3.getText().length() < 7) {
                                if (keyEvent.isShiftDown()) {
                                    name3.setText(name3.getText() + keyEvent.getText().toUpperCase());
                                } else {
                                    name3.setText(name3.getText() + keyEvent.getText());
                                }
                            }
                        }
                        break;
                }
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
                    } else if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                        board.pause(pane);
                        pause = !pause;
                        if (!pause) {
                            setBGImage("Game");
                            if (musicSelection != 3) {
                                music.selectTrack(musicSelection);
                                board.startFrameDelay();
                            }
                        } else {
                            setBGImage("Pause");
                            if (musicSelection != 3) {
                                music.stop();
                            }
                        }
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
        Node[] nodes = {menu, musicText, nesMusic, gbaMusic, noMusic, highScores, grid, scoreGrid};
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
                music.stop();
                if (musicSelection != 3) {
                    music.selectTrack(musicSelection);
                    board.startFrameDelay();
                }
                loadHighScores();
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
            case "Pause":
                for (Node n : gameNodes) {
                    n.setVisible(false);
                }
                for (Node n : menuNodes) {
                    n.setVisible(false);
                }
                view.setImage(new Image("pause.png"));
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
        try (Scanner in = new Scanner(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Tetris\\High Scores.txt"))) {
            readFile(in);
        } catch (IOException e) {
            Scanner in = new Scanner(getClass().getResourceAsStream("High Scores.txt"));
            readFile(in);
            in.close();
        }
    }

    private void readFile(Scanner in) {
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
        try (PrintWriter writer = new PrintWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Tetris\\High Scores.txt"))) {
            writer.write(names[0] + "\r\n");
            writer.write(highScore[0] + "\r\n");
            writer.write(names[1] + "\r\n");
            writer.write(highScore[1] + "\r\n");
            writer.write(names[2] + "\r\n");
            writer.write(highScore[2] + "\r\n");
        } catch (IOException e) {
            File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Tetris");
            if (f.mkdirs()) {
                writeHighScores();
            }
        }
    }

    private void start() {
        if (board.getDelay() == 0) {
            if (highScore[0] < 100000) {
                best.setText("0" + highScore[0]);
            } else {
                best.setText("" + highScore[0]);
            }
            board = new Board();
            board.setController(this);
            board.init(pane, selectedLevel);
            board.setBGAudioPlayer(music);
            setBGImage("Game");
            updateInfo(0, 0, selectedLevel);
            if (selectedLevel >= 10) {
                selectedLevel -= 10;
            }
            clearSelectedLevel();
            selectedLevel = 99;
        }
    }

    private void restart() {
        pane.getChildren().clear();
        pane.getChildren().add(view);
        pane.getChildren().addAll(new ArrayList<>(gameNodes));
        pane.getChildren().addAll(new ArrayList<>(menuNodes));
        for (int i = 0; i < totalUses.length; i++) {
            totalUses[i] = -1;
            updateStats(i);
        }
        setBGImage("Menu");
        String[] newNames = new String[3];
        int[] newScores = new int[3];
        switch (restartProperty) {
            case "Register 1":
                music.stop();
                music.selectTrack(2);
                board.startFrameDelay();
                System.arraycopy(names, 0, newNames, 1, 2);
                names = newNames;
                System.arraycopy(highScore, 0, newScores, 1, 2);
                newScores[0] = Integer.parseInt(score.getText());
                highScore = newScores;
                name1.setText("");
                name2.setText(names[1]);
                name3.setText(names[2]);
                score1.setText(highScore[0] + "");
                score2.setText(highScore[1] + "");
                score3.setText(highScore[2] + "");
                state = "Register";
                break;
            case "Register 2":
                music.stop();
                music.selectTrack(2);
                board.startFrameDelay();
                newNames[0] = names[0];
                newNames[2] = names[1];
                newScores[0] = highScore[0];
                newScores[1] = Integer.parseInt(score.getText());
                newScores[2] = highScore[1];
                highScore = newScores;
                names = newNames;
                name2.setText("");
                score2.setText(highScore[1] + "");
                name3.setText(names[2]);
                score3.setText(highScore[2] + "");
                state = "Register";
                break;
            case "Register 3":
                music.stop();
                music.selectTrack(2);
                board.startFrameDelay();
                System.arraycopy(names, 0, newNames, 0, 2);
                names = newNames;
                System.arraycopy(highScore, 0, newScores, 0, 2);
                newScores[2] = Integer.parseInt(score.getText());
                highScore = newScores;
                name3.setText("");
                score3.setText(highScore[2] + "");
                state = "Register";
                break;
            default:
                break;
        }
        timeline.play();
        stopped = false;
    }
}