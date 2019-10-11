package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds information for the "I" shape
 */
public class I implements Shape {
    private static final int[] STAT_X_COORDINATES = {90, 105, 120, 135};
    private static final int[] STAT_Y_COORDINATES = {360};
    private List<Tile> tiles = new ArrayList<>();
    private int lastRotation = 0;
    private static final int COLUMN_START = 3;
    private static final int ROW_START = 0;
    private int rotation = 0;
    private int row;
    private int column;

    /**
     * Default shape constructor to prevent a crash when initializing the initial tileset
     */
    public I() {
        //DOES ABSOLUTELY NOTHING
    }

    /**
     * Creates a new I piece in the "STATISTICS" box
     * @param level The current level
     */
    public I(int level) {
        level %= LEVEL_IMAGE_LOOP;
//        while (level >= LEVEL_IMAGE_LOOP) {
//            level -= LEVEL_IMAGE_LOOP;
//        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile(false);
            t.setImage(new Image("Bar_Box_" + level + ".png"));
            t.setCoordinates(STAT_X_COORDINATES[i], STAT_Y_COORDINATES[0]);
            tiles.add(t);
        }
    }

    /**
     * Creates a new I piece
     * @param level The current level
     * @param next  Whether or not the piece is to be loaded in the "NEXT" box
     */
    public I(int level, boolean next) {
        level %= LEVEL_IMAGE_LOOP;
//        while (level >= LEVEL_IMAGE_LOOP) {
//            level -= LEVEL_IMAGE_LOOP;
//        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile(false);
                t.setImage(new Image("Bar_Box_" + level + ".png"));
                t.setCoordinates(VALID_NEXT_X_COORDINATES[i], VALID_NEXT_Y_COORDINATES[1]);
                tiles.add(t);
            }
        } else {
            Tile t;
            for (int i = 0; i < 4; i++) {
                if (i != 2) {
                    t = new Tile(false, this);
                } else {
                    t = new Tile(true, this);
                }
                t.setImage(new Image("Bar_Box_" + level + ".png"));
                t.setXCoordinate(i + COLUMN_START);
                t.setYCoordinate(ROW_START);
                t.setCoordinates(VALID_X_COORDINATES[i], VALID_Y_COORDINATES[0]);
                tiles.add(t);
            }
        }
        row = 0;
        column = 4;
    }

    /**
     * Updates the image for this shape and by nature all of its tiles
     * @param level The new level to load the image
     */
    public void updateImage(int level) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
        }
        for (Tile t : tiles) {
//            t.setImage(new Image("Assets\\Bar_Box_" + level + ".png"));
            t.setImage(new Image("Bar_Box_" + level + ".png"));
        }
    }

//    @Override
//    public void updateCoordinates(int col, int row) {
//        if (row > this.row) {
//            this.row++;
//            for (Tile t : tiles) {
//                t.setCoordinates(t.getX(), t.getY() + VALID_COORDINATE_MODIFIERS[0]);
//            }
//        }
//        if (col < column) {
//            column--;
//            for (Tile t : tiles) {
//                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY());
//            }
//        } else if (col > column) {
//            column++;
//            for (Tile t : tiles) {
//                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY());
//            }
//        }
//    }

    /**
     * Rotates this shape to the left
     */
    @Override
    public void leftRotate() {
        rightRotate();
    }

    /**
     * Rotates this shape to the right
     */
    @Override
    public void rightRotate() {
        lastRotation = rotation;
        Tile t;
        switch (rotation) {
            case 0:
                rotation++;
                t = tiles.get(0);
                t.setXCoordinate(t.getColumn() + 2);
                t.setYCoordinate(t.getRow() - 2);
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[1], t.getY() - VALID_COORDINATE_MODIFIERS[1]);
                t = tiles.get(1);
                t.setXCoordinate(t.getColumn() + 1);
                t.setYCoordinate(t.getRow() - 1);
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY() - VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setXCoordinate(t.getColumn() - 1);
                t.setYCoordinate(t.getRow() + 1);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY() + VALID_COORDINATE_MODIFIERS[0]);
                break;
            case 1:
                rotation--;
                t = tiles.get(0);
                t.setXCoordinate(t.getColumn() - 2);
                t.setYCoordinate(t.getRow() + 2);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[1], t.getY() + VALID_COORDINATE_MODIFIERS[1]);
                t = tiles.get(1);
                t.setXCoordinate(t.getColumn() - 1);
                t.setYCoordinate(t.getRow() + 1);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY() + VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setXCoordinate(t.getColumn() + 1);
                t.setYCoordinate(t.getRow() - 1);
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY() - VALID_COORDINATE_MODIFIERS[0]);
                break;
        }
    }

    /**
     * Unloads this shape and all its sub-tiles
     * @param pane  The pane to unload the sub-tiles from
     */
    @Override
    public void unload(Pane pane) {
        pane.getChildren().removeAll(tiles);
    }

    /**
     * Reverses the rotation if the rotation is determined to be invalid
     */
    @Override
    public void undoRotation() {
        rotation = lastRotation;
    }

    /**
     * Gets the list of tiles within this shape
     * @return  The tiles within this shape as a list
     */
    @Override
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Gets the current column index of this shape
     * @return  Column index as an int
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Gets the current row index of this shape
     * @return  Row index as an int
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Spawns this shape and loads its tiles
     * @param pane  Pane to load the tiles to
     */
    @Override
    public void spawn(Pane pane) {
        pane.getChildren().addAll(tiles);
    }
}