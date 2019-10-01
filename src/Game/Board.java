package Game;

import Game.Shapes.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
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

    public void nextFrame(Pane pane) {
        frameCount++;
        if (play) {
            if (!gameOver) {
                if (lock) {
                    if (clearLine) {
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
                    if (flash) {
                        controller.resetBG();
                        flash = false;
                    }
                    if (clear) {
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
                            int tempLevel = level;
                            while (tempLevel >= 10) {
                                tempLevel -= 10;
                            }
                            for (List<Tile> tiles : savedTiles) {
                                for (Tile t : tiles) {
                                    switch (t.getShape().getClass().getName()) {
                                        case "Game.Shapes.I":
//                                    t.setImage(new Image("Assets\\Bar_Box_" + tempLevel + ".png"));
                                            t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.J":
//                                    t.setImage(new Image("Assets\\S_J_" + tempLevel + ".png"));
                                            t.setImage(new Image("S_J_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.L":
//                                    t.setImage(new Image("Assets\\Z_L_" + tempLevel + ".png"));
                                            t.setImage(new Image("Z_L_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.O":
//                                    t.setImage(new Image("Assets\\Bar_Box_" + tempLevel + ".png"));
                                            t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.S":
//                                    t.setImage(new Image("Assets\\S_J_" + tempLevel + ".png"));
                                            t.setImage(new Image("S_J_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.T":
//                                    t.setImage(new Image("Assets\\Bar_Box_" + tempLevel + ".png"));
                                            t.setImage(new Image("Bar_Box_" + tempLevel + ".png"));
                                            break;
                                        case "Game.Shapes.Z":
//                                    t.setImage(new Image("Assets\\Z_L_" + tempLevel + ".png"));
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
                        nextShape.unload(pane);
                        loadActiveShape(pane);
                        chooseNextShape(pane);
                    }
                } else {
                    List<Tile> tiles = activeShape.getTiles();
                    if (moving) {
                        dasCounter++;
                    }
                    if (frameCount == Integer.MAX_VALUE) {
                        frameCount = 0;
                    }
                    if (!down) {
                        framesSinceLastMove--;
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
                        if (framesSinceLastMove == 0) {
                            framesSinceLastMove = framesPerGridcell;
                            if (verifyVerticalMovement(tiles)) {
                                adjustVertical(tiles);
                            } else {
                                writeNewLines(tiles);
                                if (!gameOver) {
                                    if (checkFullLine()) {
                                        clearLine(pane);
                                    }
                                }
                            }
                        }
                    } else {
                        framesSinceLastMove = framesPerGridcell;
                        if (verifyVerticalMovement(tiles)) {
                            adjustVertical(tiles);
                        } else {
                            writeNewLines(tiles);
                            if (checkFullLine()) {
                                clearLine(pane);
                            }
                        }
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

    public void setBGAudioPlayer(Music music) {
        this.music = music;
    }

    public void endGame() {
        gameOver = true;
        startEndSequenceFrame = frameCount;
        index = 0;
        music.stop();
        sfx.playClip(3);
//        controller.stop();
    }

    public void init(Pane pane, int level) {
        score = 0;
        framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
        this.level = level;
        startLevel = level;
        finishedFirstLevel = false;
        framesPerGridcell = framesSinceLastMove;
        drawFirstShape(pane);
        controller.updateStats(lastShapeIndex);
        chooseNextShape(pane);
        defaultList = new ArrayList<>();
        cmpList = new ArrayList<>();
        defaultTiles = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            defaultList.add(false);
            cmpList.add(true);
            defaultTiles.add(new Tile(false));
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

    public void setDown(boolean bool) {
        down = bool;
    }

    public void moveLeft() {
        if (!lock) {
            dasCounter = INITIAL_DAS_DELAY;
            moving = true;
            xAdjustment = -1;
        }
    }

    public void moveRight() {
        if (!lock) {
            dasCounter = INITIAL_DAS_DELAY;
            moving = true;
            xAdjustment = 1;
        }
    }

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

    public void stopMovement() {
        xAdjustment = 0;
        moving = false;
        firstDasDelay = false;
    }

    public void rotate(boolean dir) {
        if (!lock) {
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

    private void adjustVertical(List<Tile> tiles) {
        for (Tile t : tiles) {
            t.setYCoordinate(t.getRow() + 1);
            t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS);
        }
    }

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
                }}
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
    }

    private void clearLine(Pane pane) {
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
//                pane.getChildren().removeAll(savedTiles.get(i));
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
                break;
        }
        linesCleared += clears;
        while (tempSpaces.size() < usedSpaces.size()) {
            tempSpaces.add(0, new ArrayList<>(defaultList));
        }
        usedSpaces = tempSpaces;
        clearLine = true;
    }

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

    public void setController(Game game) {
        controller = game;
    }

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

    public void loadMainMenu() {
        controller.setBGImage("Menu");
    }
}