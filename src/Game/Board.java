package Game;

import Game.Shapes.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dasCounter = 16;
    private int frameCount = 0;
    private List<Shape> shapes = new ArrayList<>();
    private int level = 0;
    private Shape activeShape;
    private static final int[] FRAMES_PER_GRIDCELL = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5,
            5, 4, 4, 4, 3, 3, 3, 2};
    private static final int FINAL_FAME_PER_GRIDCELL = 1;
    private int framesSinceLastMove;
    private int framesPerGridcell;
    private int xAdjustment = 0;
    private int dasAcceleration;

    public void nextFrame(Pane pane) {
        dasCounter++;
        frameCount++;
        if (frameCount == Integer.MAX_VALUE) {
            frameCount = 0;
        }
        framesSinceLastMove--;
        if (dasCounter == 16) {
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

    public void drawAllShapes(Pane pane) {
        framesSinceLastMove = FRAMES_PER_GRIDCELL[0];
        framesPerGridcell = framesSinceLastMove;
        Shape shape = new I(0, false);
        shape.spawn(pane);
        activeShape = shape;
        shapes.add(activeShape);
        shape = new I(0, true);
        shape.spawn(pane);
//        shape = new J(0);
//        shape.spawn(pane);
//        shapes.add(shape);
//        shape = new L(0);
//        shape.spawn(pane);
//        shapes.add(shape);
//        shape = new Z(0);
//        shape.spawn(pane);
//        shapes.add(shape);
//        shape = new S(0);
//        shape.spawn(pane);
//        shapes.add(shape);
//        shape = new Cube(0);
//        shape.spawn(pane);
//        shapes.add(shape);
//        shape = new T(0);
//        shape.spawn(pane);
//        shapes.add(shape);
    }

    public void moveLeft() {
        dasCounter = 15;
        xAdjustment = -15;
    }

    public void moveRight() {
        dasCounter = 15;
        xAdjustment = 15;
    }

    private void levelUp() {
        level++;
        if (level >= 19 && level < 29) {
            framesSinceLastMove = FRAMES_PER_GRIDCELL[FRAMES_PER_GRIDCELL.length - 1];
            framesPerGridcell = framesSinceLastMove;
        } else if (level >= 29) {
            framesSinceLastMove = FINAL_FAME_PER_GRIDCELL;
            framesPerGridcell = framesSinceLastMove;
        } else {
            framesSinceLastMove = FRAMES_PER_GRIDCELL[level];
            framesPerGridcell = framesSinceLastMove;
        }
    }

    public void updateDasAcceleration() {
    }
}
