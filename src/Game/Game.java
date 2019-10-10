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
import java.util.NoSuchElementException;
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
    @FXML
    private Text extraStats;
    @FXML
    private Text tetrisT;
    @FXML
    private Text drought;
    @FXML
    private Text tetrisP;
    @FXML
    private Text droughtP;

    private static final String ID = "CORNDILLY";
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
    private List<Node> extraNodes;
    private int musicSelection = 0;
    private int selectedLevel = 10;
    private boolean pause = false;
    private boolean showExtraStats = false;
    private String specialSong = "";

    /**
     * Highlights whatever is under where the user clicks as long as it is over a clickable menu
     * item
     * @param mouseEvent    Event holding information about the mouse position and button click
     */
    public void select(MouseEvent mouseEvent) {
        if (state.equalsIgnoreCase("Menu")) {
            if (mouseEvent.getX() >= 375 && mouseEvent.getX() <= 520) {
                if (mouseEvent.getY() >= 37 && mouseEvent.getY() <= 77) {
                    showExtraStats = !showExtraStats;
                    if (showExtraStats) {
                        extraStats.setFill(Color.RED);
                    } else {
                        extraStats.setFill(Color.WHITE);
                    }
                } else if (mouseEvent.getSceneY() >= 145 && mouseEvent.getSceneY() <= 180 && musicSelection != 0) {
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

    /**
     * Parses user input for writing high score names, moving pieces, advancing the game state
     * and loading the secret song
     * @param keyEvent  Event sent by pressing a button on the keyboard sent by JavaFX
     */
    public void parseInput(KeyEvent keyEvent) {
        switch (state) {
            case "Title":
                board.loadMainMenu();
                break;
            case "Menu":
                if (keyEvent.getText().toUpperCase().charAt(0) == ID.charAt(specialSong.length())) {
                    specialSong += keyEvent.getText();
                    if (specialSong.equalsIgnoreCase(ID)) {
                        specialSong = "";
                        music.stop();
                        board.startFrameDelay();
                        nesMusic.setFill(Color.WHITE);
                        gbaMusic.setFill(Color.WHITE);
                        noMusic.setFill(Color.WHITE);
                        musicSelection = 4;
                        music.selectTrack(musicSelection);
                    }
                } else {
                    specialSong = "";
                }
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
                            if (name1.getText().length() > 0) {
                                names[0] = name1.getText();
                                writeHighScores();
                                setBGImage("Menu");
                            }
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
                            if (name2.getText().length() > 0) {
                                names[1] = name2.getText();
                                writeHighScores();
                                setBGImage("Menu");
                            }
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
                            if (name3.getText().length() > 0) {
                                names[2] = name3.getText();
                                writeHighScores();
                                setBGImage("Menu");
                            }
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
                        if (board.pause(pane)) {
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

    /**
     * Stops any block commands that have been input upon releasing the corresponding key
     * @param keyEvent  Event sent by pressing a button on the keyboard sent by JavaFX
     */
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

    /**
     * Sets a reference to the main class to allow the platform to invoke close commands
     * @param main  Reference to Main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Initializes the game and sets up the frame counter, should be called immediately upon
     * loading the window
     */
    public void run() {
        loadHighScores();
        Node[] node = {score, lines, level, l, s, t, j, z, o, i, linesT, best, scoreT, next, levelT, stats, top};
        gameNodes = new ArrayList<>();
        gameNodes.addAll(Arrays.asList(node));
        Node[] nodes = {menu, musicText, nesMusic, gbaMusic, noMusic, highScores, grid, scoreGrid, extraStats};
        menuNodes = new ArrayList<>();
        menuNodes.addAll(Arrays.asList(nodes));
        Node[] nodess = {tetrisP, tetrisT, drought, droughtP};
        extraNodes = new ArrayList<>();
        extraNodes.addAll(Arrays.asList(nodess));
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

    /**
     * Stops the application frame timeline until a user input at the end of a game
     */
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

    /**
     * Updates the on screen stats from the new info given from the Board class
     * @param lines The new number of line clears
     * @param score The new user score
     * @param level The level of the current game
     */
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

    /**
     * Updates the block counters on the left side of the screen upon a block being loaded
     * @param identifier    The identifier code for the specific block in alphabetical order, 0
     *                      for i and 6 for z
     */
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

    /**
     * Changes the background image and UI elements when loading a new scene during game play
     * @param element   The code to determine which screen to load
     */
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
                for (Node n : extraNodes) {
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
                for (Node n : extraNodes) {
                    n.setVisible(false);
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
                if (!showExtraStats) {
                    view.setImage(new Image("background.png"));
                    for (Node n : extraNodes) {
                        n.setVisible(false);
                    }
                } else {
                    view.setImage(new Image("stats_background.png"));
                    for (Node n : extraNodes) {
                        n.setVisible(true);
                    }
                }
                break;
            case "Pause":
                for (Node n : gameNodes) {
                    n.setVisible(false);
                }
                for (Node n : menuNodes) {
                    n.setVisible(false);
                }
                for (Node n : extraNodes) {
                    n.setVisible(false);
                }
                view.setImage(new Image("pause.png"));
                break;
        }
    }

    /**
     * Flashes a new background image for one frame when the user gets a tetris
     */
    public void tetris() {
        if (!showExtraStats) {
            view.setImage(new Image("tetris_flash.png"));
        } else {
            view.setImage(new Image("stats_tetris_flash.png"));
        }
    }

    /**
     * Reverts the background image the frame after a flash
     */
    public void resetBG() {
        if (!showExtraStats) {
            view.setImage(new Image("background.png"));
        } else {
            view.setImage(new Image("stats_background.png"));
        }
    }

    /**
     * Updates the drought counter after a piece spawns that is not an I piece, changing color to
     * red after 13 spawns which is the start of a drought
     * @param droughtCounter    The amount of consecutive non-I pieces spawned
     */
    public void updateDroughtCounter(int droughtCounter) {
        droughtP.setText(droughtCounter + "");
        if (droughtCounter >= 13) {
            droughtP.setFill(Color.RED);
        } else {
            droughtP.setFill(Color.WHITE);
        }
    }

    /**
     * Updates the tetris percentage when the player clears a line
     * @param tetrisPercentage  The new percentage as an int, intended to be truncated rather
     *                          than rounded
     */
    public void updateTetrisPercentage(int tetrisPercentage) {
        tetrisP.setText(tetrisPercentage + "");
    }

    /**
     * Loads the high score file, either one saved on the computer or the one in the JAR
     */
    private void loadHighScores() {
        try (Scanner in = new Scanner(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Tetris\\High Scores.txt"))) {
            readFile(in);
        } catch (IOException | NoSuchElementException e) {
            Scanner in = new Scanner(getClass().getResourceAsStream("High Scores.txt"));
            readFile(in);
            in.close();
        }
    }

    /**
     * Reads the desired high score file
     * @param in    Scanner holding the data for the file
     */
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

    /**
     * Writes the new high score with user entered name to a file located on the computer
     */
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

    /**
     * Starts a game by reinitializing the board and playing the desired background track
     */
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

    /**
     * Loads the desired menu option based on if the user gets a new high score or not
     */
    private void restart() {
        pane.getChildren().clear();
        pane.getChildren().add(view);
        pane.getChildren().addAll(new ArrayList<>(gameNodes));
        pane.getChildren().addAll(new ArrayList<>(menuNodes));
        pane.getChildren().addAll(new ArrayList<>(extraNodes));
        for (int i = 0; i < totalUses.length; i++) {
            totalUses[i] = -1;
            updateStats(i);
        }
        tetrisP.setText("100");
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

    /**
     * Clears the selected level option when the user clicks on a different level option
     */
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
}