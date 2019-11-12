package Game;

import Game.Shapes.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int droughtCounter;
    private Tile defaultTile = new Tile(false);
    private int delay = 0;
    private SFX sfx = new SFX();
    private int inputDelay = 0;
    private int startEndSequenceFrame = 0;
    private boolean gameOver = false;
    private boolean tetris = false;
    private boolean flash = false;
    private boolean clear = false;
    private int clears;
    private List<List<Tile>> tempTile;
    private int score;
    private Game controller;
    private int previousLevelUpLine = 0;
    private boolean levelUp = false;
    private boolean lock = false;
    private boolean clearLine = false;
    private boolean down = false;
    private int lockFrameCounter;
    private int dasCounter = 15;
    private int frameCount = 0;
    private int level;
    private int startLevel;
    private boolean finishedFirstLevel;
    private Shape activeShape;
    private Shape nextShape;
    private boolean firstPiece = true;
    private int firstPieceFrameDropDelay = 0;
    private static final int[] FRAMES_PER_GRIDCELL = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5,
            5, 4, 4, 4, 3, 3, 3, 2};
    private static final int FINAL_FRAME_PER_GRIDCELL = 1;
    private int framesSinceLastMove;
    private int framesPerGridcell;
    private int xAdjustment = 0;
    private static final int INITIAL_DAS_DELAY = 16;
    private static final int DAS_ACCELERATION = 6;
    private boolean firstDasDelay = false;
    private boolean moving = false;
    private List<List<Tile>> savedTiles = new ArrayList<>();
    private List<List<Boolean>> usedSpaces = new ArrayList<>();
    private Random r = new Random();
    private List<Boolean> defaultList;
    private List<Boolean> cmpList;
    private List<Tile> defaultTiles;
    private int lastShapeIndex = 7;
    private int linesCleared = 0;
    private int[] indicies = new int[4];
    private List<Shape> statShapes;
    private int index;
    private boolean play = false;
    private Music music;
    private int scoreIncrement = 0;
    private boolean pause = false;
    private int tetrisLineClears;
    private String lastDir = "None";

    /**
     * Plays the next frame of the game and applies any translations based on user input and
     * frame counters
     * @param pane  The main pane of the game to draw or move shapes on
     */
    public void nextFrame(Pane pane) {
        frameCount++;
        if (frameCount == Integer.MAX_VALUE) {
            frameCount = 0;
        }
        if (delay != 0) {
            if(--delay == 0) {
                music.play();
            }
        }
        if (play) {
            if (!pause) {
                if (!gameOver) {
                    if (lock) {
                        if (clearLine) {
                            clearLines();
                        }
                        if (flash) {
                            controller.resetBG();
                            flash = false;
                        }
                        if (clear) {
                            tileUnload(pane);
                        }
                        if (--lockFrameCounter == 0) {
                            if (clear) {
                                dropTiles();
                                clear = false;
                                tetris = false;
                            }
                            controller.updateInfo(linesCleared, score, level);
                            lock = false;
                            if (levelUp) {
                                sfx.playClip(6);
                                int tempLevel = level % Shape.LEVEL_IMAGE_LOOP;
                                updateImages(tempLevel);
                            }
                            nextShape.unload(pane);
                            if (lastShapeIndex != 0) {
                                droughtCounter++;
                            } else {
                                droughtCounter = 0;
                            }
                            controller.updateDroughtCounter(droughtCounter);
                            loadActiveShape(pane);
                            chooseNextShape(pane);
                        }
                    } else {
                        List<Tile> tiles = activeShape.getTiles();
                        if (moving) {
                            dasCounter++;
                        }
                        updateDasMovement(tiles);
                        if (!down) {
                            updateFrameCounterNotDown(tiles);
                        } else {
                            updateFrameCounterDown(tiles);
                        }
                    }
                } else if (inputDelay != 0) {
                    if (--inputDelay == 0) {
                        controller.stop();
                        play = false;
                        gameOver = false;
                    }
                } else {
                    if (index == 20) {
                        inputDelay = 60;
                    }
                    if ((frameCount - startEndSequenceFrame) % 4 == 0) {
                        int tempLevel = level;
                        while (tempLevel >= 10) {
                            tempLevel -= 10;
                        }
                        pane.getChildren().removeAll(savedTiles.get(index));
                        List<Tile> tiles = savedTiles.get(index);
                        Tile t;
                        for (int i = 0; i < 10; i++) {
                            t = new Tile(false, new I());
                            t.setImage(new Image("LEVEL_" + tempLevel + "_BLIND.png"));
                            t.setCoordinates(Shape.X_COORDS[i], Shape.VALID_Y_COORDINATES[0] + (15 * index));
                            tiles.set(i, t);
                        }
                        pane.getChildren().addAll(savedTiles.get(index++));
                    }
                }
            }
        }
    }

    /**
     * Pauses the game when the user presses the space bar
     * @param pane  The main pane to remove the shapes from
     * @return      True if the game was successfully paused or unpaused
     */
    public boolean pause(Pane pane) {
        if (!gameOver && !lock) {
            if (pause) {
                for (List<Tile> tls : savedTiles) {
                    for (Tile t : tls) {
                        if (t != defaultTile) {
                            pane.getChildren().add(t);
                        }
                    }
                }
                if (activeShape != null) {
                    activeShape.spawn(pane);
                }
                nextShape.spawn(pane);
                for (Shape s : statShapes) {
                    s.spawn(pane);
                }
            } else {
                for (List<Tile> t : savedTiles) {
                    pane.getChildren().removeAll(t);
                }
                if (activeShape != null) {
                    activeShape.unload(pane);
                }
                nextShape.unload(pane);
                for (Shape s : statShapes) {
                    s.unload(pane);
                }
            }
            pause = !pause;
            return true;
        }
        return false;
    }

    /**
     * Sets the background audio player
     * @param music The music wrapper of the audio files
     */
    public void setBGAudioPlayer(Music music) {
        this.music = music;
    }

    /**
     * Changes values to end the game, playing sfx and stopping music
     */
    public void endGame() {
        gameOver = true;
        startEndSequenceFrame = frameCount;
        index = 0;
        music.stop();
        sfx.playClip(3);
    }

    /**
     * Initializes the games and the first shapes
     * @param pane  Pane to draw the first shape to
     * @param level The desired starting level
     */
    public void init(Pane pane, int level) {
        score = 0;
        tetrisLineClears = 0;
        framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
        firstPieceFrameDropDelay = FRAMES_PER_GRIDCELL[0];
        firstPiece = true;
        this.level = level;
        startLevel = level;
        finishedFirstLevel = false;
        framesPerGridcell = framesSinceLastMove;
        drawFirstShape(pane);
        controller.updateStats(lastShapeIndex);
        if (lastShapeIndex == 0) {
            droughtCounter = 0;
        } else {
            droughtCounter = 1;
        }
        controller.updateDroughtCounter(droughtCounter);
        chooseNextShape(pane);
        defaultList = new ArrayList<>();
        cmpList = new ArrayList<>();
        defaultTiles = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            defaultList.add(false);
            cmpList.add(true);
            defaultTiles.add(defaultTile);
        }
        int i = 0;
        while (i < 20) {
            usedSpaces.add(i, new ArrayList<>(defaultList));
            savedTiles.add(i++, new ArrayList<>(defaultTiles));
        }
        statShapes = new ArrayList<>();
        Shape s;
        s = new I(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new L(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new J(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new O(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new S(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new T(level);
        s.spawn(pane);
        statShapes.add(s);
        s = new Z(level);
        s.spawn(pane);
        statShapes.add(s);
        play = true;
    }

    /**
     * Speeds up the downward movement of options when the user holds down the S key
     * @param bool  Whether to speed up or stop holding down
     */
    public void setDown(boolean bool) {
        if (!bool) {
            scoreIncrement = 0;
        }
        down = bool;
    }

    /**
     * Sets the block movement to go left and resets the DAS counter
     */
    public void moveLeft() {
        if (!lock && !moving) {
            if (!lastDir.equalsIgnoreCase("Left")) {
                lastDir = "Left";
                dasCounter = INITIAL_DAS_DELAY;
                firstDasDelay = false;
            }
            moving = true;
            xAdjustment = -1;
        }
    }

    /**
     * Sets the block movement to go to the right and resets the sad counter
     */
    public void moveRight() {
        if (!lock && !moving) {
            if (!lastDir.equalsIgnoreCase("Right")) {
                lastDir = "Right";
                dasCounter = INITIAL_DAS_DELAY;
                firstDasDelay = false;
            }
            moving = true;
            xAdjustment = 1;
        }
    }

    /**
     * Loads the main menu after a game
     */
    public void loadMainMenu() {
        controller.setBGImage("Menu");
    }

    /**
     * Basic frame delay for loading new music
     */
    public void startFrameDelay() {
        delay = 12;
    }

    /**
     * Gets the total amount of frames left in the music delay
     * @return  Frames remaining in the delay as an int
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Stops the block movement when the key is released
     */
    public void stopMovement() {
        xAdjustment = 0;
        moving = false;
        dasCounter = 0;
    }

    /**
     * Rotates the active block and plays the audio clip
     * @param dir   True if rotating to the right, false if rotating to the left
     */
    public void rotate(boolean dir) {
        if (!lock && activeShape != null) {
            sfx.playClip(2);
            if (dir) {
                activeShape.rightRotate();
            } else {
                activeShape.leftRotate();
            }
            List<Tile> tiles = activeShape.getTiles();
            try {
                for (Tile t : tiles) {
                    int i = t.getRow();
                    if (!(i < 0)) {
                        try {
                            if (usedSpaces.get(i).get(t.getColumn())) {
                                throw new InvalidNewCoordinateException("Invalid new coordinate for tile rotation");
                            }
                        } catch (IndexOutOfBoundsException e) {
                            throw new InvalidNewCoordinateException("Invalid new coordinate for tile rotation");
                        }
                    }
                }
            } catch (InvalidNewCoordinateException e) {
                for (Tile t : tiles) {
                    t.revert();
                    activeShape.undoRotation();
                }
            }
        }
    }

    /**
     * Sets the main controller
     * @param game  Reference to the controller class
     */
    public void setController(Game game) {
        controller = game;
    }

    /**
     * Selects a random number to load the next shape
     * @return  The number selected after verifying it is not a repeat
     */
    private int selectRandom() {
        int get = r.nextInt(7);
        boolean verify = false;
        if (get != lastShapeIndex) {
            verify = true;
            lastShapeIndex = get;
        }
        while (!verify) {
            get = r.nextInt(7);
            if (get != lastShapeIndex) {
                verify = true;
                lastShapeIndex = get;
            }
        }
        return get;
    }

    /**
     * Loads a new active shape from the current next shape
     * @param pane  The pane to draw the shape to
     */
    private void loadActiveShape(Pane pane) {
        controller.updateStats(lastShapeIndex);
        String name = nextShape.getClass().getName();
        switch (name) {
            case "Game.Shapes.I":
                activeShape = new I(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.J":
                activeShape = new J(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.L":
                activeShape = new L(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.O":
                activeShape = new O(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.S":
                activeShape = new S(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.T":
                activeShape = new T(level, false);
                activeShape.spawn(pane);
                break;
            case "Game.Shapes.Z":
                activeShape = new Z(level, false);
                activeShape.spawn(pane);
                break;
        }
    }

    /**
     * Selects the next shape after calling RNG
     * @param pane  Pane to draw the next shape to
     */
    private void chooseNextShape(Pane pane) {
        switch (selectRandom()) {
            case 0:
                nextShape = new I(level, true);
                nextShape.spawn(pane);
                break;
            case 1:
                nextShape = new J(level, true);
                nextShape.spawn(pane);
                break;
            case 2:
                nextShape = new L(level, true);
                nextShape.spawn(pane);
                break;
            case 3:
                nextShape = new O(level, true);
                nextShape.spawn(pane);
                break;
            case 4:
                nextShape = new S(level, true);
                nextShape.spawn(pane);
                break;
            case 5:
                nextShape = new T(level, true);
                nextShape.spawn(pane);
                break;
            case 6:
                nextShape = new Z(level, true);
                nextShape.spawn(pane);
                break;
        }
    }

    /**
     * Draws the first spawned shape to the board
     * @param pane  The pane to draw the shape on
     */
    private void drawFirstShape(Pane pane) {
        switch (selectRandom()) {
            case 0:
                activeShape = new I(level, false);
                activeShape.spawn(pane);
                break;
            case 1:
                activeShape = new J(level, false);
                activeShape.spawn(pane);
                break;
            case 2:
                activeShape = new L(level, false);
                activeShape.spawn(pane);
                break;
            case 3:
                activeShape = new O(level, false);
                activeShape.spawn(pane);
                break;
            case 4:
                activeShape = new S(level, false);
                activeShape.spawn(pane);
                break;
            case 5:
                activeShape = new T(level, false);
                activeShape.spawn(pane);
                break;
            case 6:
                activeShape = new Z(level, false);
                activeShape.spawn(pane);
                break;
        }
    }

    /**
     * Verifies any sideways movement
     * @param tiles The individual tiles for the active shape
     * @return      True if the movement can go through, false if it cannot
     */
    private boolean verifyHorizMovement(List<Tile> tiles) {
        if (xAdjustment > 0) {
            for (Tile t : tiles) {
                try {
                    if (t.getColumn() + 1 >= 10) {
                        return false;
                    }
                    List<Boolean> b = usedSpaces.get(t.getRow());
                    try {
                        if (usedSpaces.get(t.getRow()).get(t.getColumn() + 1)) {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //Does nothing because you should still be able to move when above the board
                }
            }
        } else {
            for (Tile t : tiles) {
                try {
                    if (t.getColumn() - 1 < 0) {
                        return false;
                    }
                    List<Boolean> b = usedSpaces.get(t.getRow());
                    try {
                        if (usedSpaces.get(t.getRow()).get(t.getColumn() - 1)) {
                            return false;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //Does nothing because you should still be able to move when above the board
                }
            }
        }
        return true;
    }

    /**
     * Verifies if the piece can move downward
     * @param tiles The individual tiles from the active shape
     * @return      True if it can move down, false if it cannot
     */
    private boolean verifyVerticalMovement(List<Tile> tiles) {
        try {
            for (Tile t : tiles) {
                if (!(t.getRow() < 0)) {
                    if (usedSpaces.get(t.getRow() + 1).get(t.getColumn())) {
                        return false;
                    }
                } else {
                    if (usedSpaces.get(0).get(t.getColumn())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Moves the piece to the side if the movement was validated
     * @param tiles The individual tiles for the active shape
     */
    private void adjustHoriz(List<Tile> tiles) {
        sfx.playClip(0);
        if (xAdjustment > 0) {
            for (Tile t : tiles) {
                t.setXCoordinate(t.getColumn() + 1);
                t.setCoordinates(t.getX() + Tile.SQUARE_DIMENSIONS, t.getY());
            }
        } else {
            for (Tile t : tiles) {
                t.setXCoordinate(t.getColumn() - 1);
                t.setCoordinates(t.getX() - Tile.SQUARE_DIMENSIONS, t.getY());
            }
        }
    }

    /**
     * Moves the piece down if the movement was validated
     * @param tiles The individual tiles for the active shape
     */
    private void adjustVertical(List<Tile> tiles) {
        for (Tile t : tiles) {
            t.setYCoordinate(t.getRow() + 1);
            t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS);
        }
    }

    /**
     * Writes the tiles from the active shape to the list of tiles and disables the active shape
     * @param tiles The individual tiles from the active shape
     */
    private void writeNewLines(List<Tile> tiles) {
        lock = true;
        sfx.playClip(1);
        int row = 0;
        for (Tile t : tiles) {
            t.setAnchor(false);
            int r = t.getRow();
            if (r > row) {
                row = r;
            }
            if (!(r < 0)) {
                if (usedSpaces.get(r).get(t.getColumn())) {
                    endGame();
                    break;
                } else {
                    usedSpaces.get(r).set(t.getColumn(), true);
                    savedTiles.get(r).set(t.getColumn(), t);
                }
            }
        }
        if (row < 4) {
            lockFrameCounter = 18;
        } else if (row < 8) {
            lockFrameCounter = 16;
        } else if (row < 12) {
            lockFrameCounter = 14;
        } else if (row < 16) {
            lockFrameCounter = 12;
        } else {
            lockFrameCounter = 10;
        }
        activeShape = null;
    }

    /**
     * Clears any full lines off the board
     */
    private void clearLine() {
        lockFrameCounter = 24;
        List<Boolean> l;
        List<List<Boolean>> tempSpaces = new ArrayList<>(usedSpaces);
        tempTile = new ArrayList<>(savedTiles);
        clears = 0;
        for (int i = 0; i < 20; i++) {
            l = usedSpaces.get(i);
            if (l.equals(cmpList)) {
                indicies[clears++] = i;
                tempSpaces.set(i, null);
                tempTile.set(i, null);
            }
        }
        for (int i = 0; i < tempSpaces.size(); i++) {
            if (tempSpaces.get(i) == null) {
                tempSpaces.remove(i--);
            }
        }
        switch (clears) {
            case 1:
                sfx.playClip(4);
                score += (40 * (level + 1));
                break;
            case 2:
                sfx.playClip(4);
                score += (100 * (level + 1));
                break;
            case 3:
                sfx.playClip(4);
                score += (300 * (level + 1));
                break;
            case 4:
                sfx.playClip(5);
                score += (1200 * (level + 1));
                tetris = true;
                tetrisLineClears += clears;
                break;
        }
        linesCleared += clears;
        while (tempSpaces.size() < usedSpaces.size()) {
            tempSpaces.add(0, new ArrayList<>(defaultList));
        }
        usedSpaces = tempSpaces;
        clearLine = true;
    }

    /**
     * Checks if any of the lines on the board are complete
     * @return  True if there is a complete line, false if there is not
     */
    private boolean checkFullLine() {
        List<Boolean> l;
        for (int i = 0; i < 20; i++) {
            l = usedSpaces.get(i);
            if (l.equals(cmpList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Drops any layers of tiles located above any cleared lines, works with asynchronous line
     * clears
     */
    private void dropTiles() {
        boolean exit = false;
        switch (clears) {
            case 1:
                for (int i = 0; i < 20 && !exit; i++) {
                    List<Tile> tile = tempTile.get(i);
                    if (i < indicies[0]) {
                        for (Tile t : tile) {
                            t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * clears);
                        }
                    } else {
                        exit = true;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 20 && !exit; i++) {
                    List<Tile> tile = tempTile.get(i);
                    if (tile != null) {
                        if (i < indicies[0]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * clears);
                            }
                        } else if (i < indicies[1]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 1));
                            }
                        } else {
                            exit = true;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 20 && !exit; i++) {
                    List<Tile> tile = tempTile.get(i);
                    if (tile != null) {
                        if (i < indicies[0]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * clears);
                            }
                        } else if (i < indicies[1]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 1));
                            }
                        } else if (i < indicies[2]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 2));
                            }
                        } else {
                            exit = true;
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 20 && !exit; i++) {
                    List<Tile> tile = tempTile.get(i);
                    if (tile != null) {
                        if (i < indicies[0]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * clears);
                            }
                        } else if (i < indicies[1]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 1));
                            }
                        } else if (i < indicies[2]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 2));
                            }
                        } else if (i < indicies[3]) {
                            for (Tile t : tile) {
                                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * (clears - 3));
                            }
                        } else {
                            exit = true;
                        }
                    }
                }
                break;
        }
        for (int i = 0; i < tempTile.size(); i++) {
            if (tempTile.get(i) == null) {
                tempTile.remove(i--);
            }
        }
        while (tempTile.size() < savedTiles.size()) {
            tempTile.add(0, new ArrayList<>(defaultTiles));
        }
        savedTiles = new ArrayList<>(tempTile);
    }

    /**
     * Changes the level and sets the frame count for blocks to fall
     */
    private void levelUp() {
        level++;
        if (level >= 19 && level < 29) {
            framesSinceLastMove = FRAMES_PER_GRIDCELL[FRAMES_PER_GRIDCELL.length - 1];
            framesPerGridcell = framesSinceLastMove;
        } else if (level >= 29) {
            framesSinceLastMove = FINAL_FRAME_PER_GRIDCELL;
            framesPerGridcell = framesSinceLastMove;
        } else {
            framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
            framesPerGridcell = framesSinceLastMove;
        }
    }
    /**
     * Moves the tile to the side if the DAS counter calls for it
     * @param tiles Tiles from the active shape
     */
    private void updateDasMovement(List<Tile> tiles) {
        if (dasCounter > INITIAL_DAS_DELAY) {
            dasCounter = 0;
            if (verifyHorizMovement(tiles)) {
                adjustHoriz(tiles);
            }
        } else if (dasCounter == INITIAL_DAS_DELAY) {
            firstDasDelay = true;
            dasCounter = 0;
            if (verifyHorizMovement(tiles)) {
                adjustHoriz(tiles);
            }
        } else if (dasCounter == DAS_ACCELERATION && firstDasDelay) {
            dasCounter = 0;
            if (verifyHorizMovement(tiles)) {
                adjustHoriz(tiles);
            }
        }
    }

    /**
     * Updates the position of the shape if the user is holding down
     * @param tiles List of tiles from the active shape
     */
    private void updateFrameCounterDown(List<Tile> tiles) {
        framesSinceLastMove = framesPerGridcell;
        scoreIncrement++;
        if (verifyVerticalMovement(tiles)) {
            adjustVertical(tiles);
        } else {
            score += scoreIncrement;
            scoreIncrement = 0;
            writeNewLines(tiles);
            if (checkFullLine()) {
                clearLine();
            }
        }
    }

    /**
     * Updates the position of the shape if needed and decrements the frame since last piece
     * vertical movement counter if necessary
     * @param tiles Tiles from the active shape
     */
    private void updateFrameCounterNotDown(List<Tile> tiles) {
        framesSinceLastMove--;
        if (firstPiece) {
            if (--firstPieceFrameDropDelay == 0) {
                framesSinceLastMove = framesPerGridcell;
                if (verifyVerticalMovement(tiles)) {
                    adjustVertical(tiles);
                } else {
                    writeNewLines(tiles);
                    if (!gameOver) {
                        if (checkFullLine()) {
                            clearLine();
                        }
                    }
                }
                firstPiece = false;
            }
        } else if (framesSinceLastMove == 0) {
            framesSinceLastMove = framesPerGridcell;
            if (verifyVerticalMovement(tiles)) {
                adjustVertical(tiles);
            } else {
                writeNewLines(tiles);
                if (!gameOver) {
                    if (checkFullLine()) {
                        clearLine();
                    }
                }
            }
        }
    }

    /**
     * Updates the images for all tiles currently on the board
     * @param tempLevel The level % 10 to load the image with
     */
    private void updateImages(int tempLevel) {
        for (List<Tile> tiles : savedTiles) {
            for (Tile t : tiles) {
                switch (t.getShape().getClass().getName()) {
                    case "Game.Shapes.I":
                        t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.J":
                        t.setImage(new Image("S_J_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.L":
                        t.setImage(new Image("Z_L_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.O":
                        t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.S":
                        t.setImage(new Image("S_J_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.T":
                        t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                        break;
                    case "Game.Shapes.Z":
                        t.setImage(new Image("Z_L_" + tempLevel + ".png"));
                        break;
                }
            }
        }
        for (Shape s : statShapes) {
            s.updateImage(level);
        }
        levelUp = false;
    }

    /**
     * Unloads the tiles if the frame counter is a multiple of 4
     * @param pane  Pane the tiles are on
     */
    private void tileUnload(Pane pane) {
        if (lockFrameCounter % 4 == 0) {
            if (tetris) {
                controller.tetris();
                flash = true;
            }
            if (lockFrameCounter == 24) {
                index = 0;
            }
            if (lockFrameCounter != 4) {
                for (int i = 0; i < clears; i++) {
                    List<Tile> tiles = savedTiles.get(indicies[i]);
                    tiles.get(4 - index).unload(pane);
                    tiles.get(5 + index).unload(pane);
                }
                index++;
            }
        }
    }

    /**
     * Begins the line clear animation if a line clear has been requested
     */
    private void clearLines() {
        controller.updateTetrisPercentage((tetrisLineClears * 100)/linesCleared);
        if (!finishedFirstLevel) {
            if (startLevel < 9) {
                if (linesCleared >= (startLevel * 10 + 10)) {
                    levelUp();
                    levelUp = true;
                    finishedFirstLevel = true;
                }
            } else {
                if (linesCleared >= Math.max(100, startLevel * 10 - 50)) {
                    levelUp();
                    levelUp = true;
                    finishedFirstLevel = true;
                }
            }
            previousLevelUpLine = linesCleared;
            while (previousLevelUpLine % 10 != 0) {
                previousLevelUpLine--;
            }
        } else if (linesCleared >= previousLevelUpLine + 10) {
            levelUp();
            levelUp = true;
            previousLevelUpLine = linesCleared;
            while (previousLevelUpLine % 10 != 0) {
                previousLevelUpLine--;
            }
        }
        clearLine = false;
        clear = true;
    }
}