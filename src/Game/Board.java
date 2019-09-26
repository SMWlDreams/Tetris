package Game;

import Game.Shapes.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private boolean test = false;
    private boolean secondAuthentication = false;
    private int blocksPlaced = 0;
    private boolean lock = false;
    private boolean clearLine = false;
    private boolean down = false;
    private int lockFrameCounter;
    private int dasCounter = 15;
    private int frameCount = 0;
    private int level = 7;
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

    public void nextFrame(Pane pane) {
        frameCount++;
        if (lock) {
            if (clearLine) {
            }
            if (--lockFrameCounter == 0) {
                lock = false;
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
                        if (checkFullLine()) {
                            clearLine(pane);
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
    }

    public void init(Pane pane, int level) {
        framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
        this.level = level;
        startLevel = level;
        finishedFirstLevel = false;
        framesPerGridcell = framesSinceLastMove;
        drawFirstShape(pane);
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
            if (dir) {
                activeShape.rightRotate();
            } else {
                activeShape.leftRotate();
            }
            List<Tile> tiles = activeShape.getTiles();
            try {
                for (Tile t : tiles) {
                    try {
                        List<Boolean> bools = usedSpaces.get(t.getRow());
                        try {
                            if (bools.get(t.getColumn())) {
                                throw new InvalidNewCoordinateException("Invalid new coordinate for tile rotation");
                            }
                        } catch (IndexOutOfBoundsException e) {
                            throw new InvalidNewCoordinateException("Invalid new coordinate for tile rotation");
                        }
                    } catch (IndexOutOfBoundsException e) {
//                        System.out.println("oof");
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
//                    System.out.println("F");
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
//                    System.out.println("F");
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
                }
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private void adjustHoriz(List<Tile> tiles) {
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
        int row = 0;
        for (Tile t : tiles) {
            t.setAnchor(false);
            int r = t.getRow();
            if (r > row) {
                row = r;
            }
            if (!(r < 0)) {
                usedSpaces.get(r).set(t.getColumn(), true);
                savedTiles.get(r).set(t.getColumn(), t);
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
        System.out.println(++blocksPlaced);
        if (secondAuthentication) {
            System.out.println("DEBUG");
        }
        if (test) {
            secondAuthentication = true;
        }
//        if (blocksPlaced == 40) {
//            System.out.println("ENTERED DEBUG MODE");
//        }
    }

    private void clearLine(Pane pane) {
        List<Boolean> l;
        List<List<Boolean>> tempSpaces = new ArrayList<>(usedSpaces);
        List<List<Tile>> tempTile = new ArrayList<>(savedTiles);
        int clears = 0;
        for (int i = 0; i < 20; i++) {
            l = usedSpaces.get(i);
            if (l.equals(cmpList)) {
                tempSpaces.set(i, null);
                pane.getChildren().removeAll(savedTiles.get(i));
                tempTile.set(i, null);
                clears++;
            }
        }
        for (List<Tile> tile : tempTile) {
            if (tile == null) {
                break;
            }
            for (Tile t : tile) {
                t.setCoordinates(t.getX(), t.getY() + Tile.SQUARE_DIMENSIONS * clears);
            }
        }
        for (int i = 0; i < tempSpaces.size(); i++) {
            if (tempSpaces.get(i) == null) {
                tempSpaces.remove(i);
                tempTile.remove(i--);
            }
        }
        linesCleared += clears;
        if (blocksPlaced >= 40) {
            test = true;
        }
        while (tempSpaces.size() < usedSpaces.size()) {
            tempSpaces.add(0, defaultList);
            tempTile.add(0, defaultTiles);
        }
        usedSpaces = tempSpaces;
        savedTiles = tempTile;
        lockFrameCounter = 20;
        System.out.println(linesCleared);
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
}
