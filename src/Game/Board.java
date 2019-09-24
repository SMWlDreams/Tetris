package Game;

import Game.Shapes.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private int dasCounter = 15;
    private int frameCount = 0;
    private List<Shape> shapes = new ArrayList<>();
    private int level = 7;
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
    private List<Integer> memory = new ArrayList<>();
    private List<List<Boolean>> usedSpaces = new ArrayList<>();
    private Random r = new Random();

    public void nextFrame(Pane pane) {
        if (moving) {
            dasCounter++;
        }
        frameCount++;
        if (frameCount % 130 == 0) {
            nextShape.unload(pane);
            loadActiveShape(pane);
            chooseNextShape(pane);
        }
        if (frameCount == Integer.MAX_VALUE) {
            frameCount = 0;
        }
        framesSinceLastMove--;
        if (dasCounter > INITIAL_DAS_DELAY) {
            dasCounter = 0;
            if (framesSinceLastMove == 0) {
                framesSinceLastMove = framesPerGridcell;
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow() + 1);
            } else {
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow());
            }
        } else if (dasCounter == INITIAL_DAS_DELAY) {
            firstDasDelay = true;
            dasCounter = 0;
            if (framesSinceLastMove == 0) {
                framesSinceLastMove = framesPerGridcell;
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow() + 1);
            } else {
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow());
            }
        } else if (dasCounter == DAS_ACCELERATION && firstDasDelay) {
            dasCounter = 0;
            if (framesSinceLastMove == 0) {
                framesSinceLastMove = framesPerGridcell;
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow() + 1);
            } else {
                activeShape.updateCoordinates(activeShape.getColumn() + xAdjustment, activeShape.getRow());
            }
        } else {
            if (framesSinceLastMove == 0) {
                framesSinceLastMove = framesPerGridcell;
                activeShape.updateCoordinates(activeShape.getColumn(), activeShape.getRow() + 1);
            }
        }
    }

    public void init(Pane pane) {
        framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
        framesPerGridcell = framesSinceLastMove;
        drawFirstShape(pane);
        chooseNextShape(pane);
        int i = 0;
        while (i < 10) {
            usedSpaces.add(i++, new ArrayList<>(20));
        }
    }

    public void moveLeft() {
        dasCounter = INITIAL_DAS_DELAY;
        moving = true;
        xAdjustment = -1;
    }

    public void moveRight() {
        dasCounter = INITIAL_DAS_DELAY;
        moving = true;
        xAdjustment = 1;
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
        if (dir) {
            activeShape.rightRotate();
        } else {
            activeShape.leftRotate();
        }
    }

    private int selectRandom() {
        int get = r.nextInt(7);
        boolean verify = false;
        List<Integer> recall;
        while (!verify) {
            get = r.nextInt(7);
            if (memory.size() > 1) {
                recall = memory.subList(0, Math.min(memory.size(),6));
                if (Collections.frequency(recall, get) < 2) {
                    addToMemory(get);
                    verify = true;
                }
            } else {
                addToMemory(get);
                verify = true;
            }
        }
        return get;
    }

    private void addToMemory(int value) {
        memory.add(0, value);
    }

    private void loadActiveShape(Pane pane) {
        String name = nextShape.getClass().getName();
        switch (name) {
            case "Game.Shapes.I":
                activeShape = new I(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.J":
                activeShape = new J(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.L":
                activeShape = new L(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.O":
                activeShape = new O(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.S":
                activeShape = new S(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.T":
                activeShape = new T(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case "Game.Shapes.Z":
                activeShape = new Z(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
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
                shapes.add(activeShape);
                break;
            case 1:
                activeShape = new J(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case 2:
                activeShape = new L(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case 3:
                activeShape = new O(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case 4:
                activeShape = new S(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case 5:
                activeShape = new T(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
            case 6:
                activeShape = new Z(level, false);
                activeShape.spawn(pane);
                shapes.add(activeShape);
                break;
        }
    }


}
