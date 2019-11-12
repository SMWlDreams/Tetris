package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds information for the "O" shape
 */
public class O implements Shape {
    private static final int[] STAT_X_COORDINATES = {90, 105};
    private static final int[] STAT_Y_COORDINATES = {315, 330};
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;

    /**
     * Creates a new O piece in the "STATISTICS" box
     * @param level Current level
     */
    public O(int level) {
        level %= LEVEL_IMAGE_LOOP;
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile(false);
            t.setImage(new Image("Bar_Box_" + level + ".png"));
            if (i % 2 == 0) {
                t.setCoordinates(STAT_X_COORDINATES[0], STAT_Y_COORDINATES[i/2]);
            } else {
                t.setCoordinates(STAT_X_COORDINATES[1], STAT_Y_COORDINATES[i/2]);
            }
            tiles.add(t);
        }
    }

    /**
     * Creates a new O piece
     * @param level The current level
     * @param next  Whether or not the piece is to be loaded in the "NEXT" box
     */
    public O(int level, boolean next) {
        level %= LEVEL_IMAGE_LOOP;
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile(false);
                t.setImage(new Image("Bar_Box_" + level + ".png"));
                if (i % 2 == 0) {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[1], VALID_NEXT_Y_COORDINATES[i/2]);
                } else {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[2], VALID_NEXT_Y_COORDINATES[i/2]);
                }
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile(false, this);
                t.setImage(new Image("Bar_Box_" + level + ".png"));
                if (i % 2 == 0) {
                    t.setXCoordinate(4);
                    t.setYCoordinate(i/2);
                    t.setCoordinates(VALID_X_COORDINATES[1], VALID_Y_COORDINATES[i/2]);
                } else {
                    t.setXCoordinate(5);
                    t.setYCoordinate(i/2);
                    t.setCoordinates(VALID_X_COORDINATES[2], VALID_Y_COORDINATES[i/2]);
                }
                tiles.add(t);
            }
            column = 4;
            row = 0;
        }
    }

    /**
     * Updates the image for this shape and by nature all of its tiles
     * @param level The new level to load the image
     */
    @Override
    public void updateImage(int level) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("Bar_Box_" + level + ".png"));
        }
    }

    /**
     * You can't rotate this block you dummy
     */
    @Override
    public void leftRotate() {
        rightRotate();
    }

    /**
     * You can't rotate this block you dummy
     */
    @Override
    public void undoRotation() {
    }

    /**
     * You can't rotate this block you dummy
     */
    @Override
    public void rightRotate() {
        for (Tile t : tiles) {
            t.setCoordinates(t.getX(), t.getY());
            t.setXCoordinate(t.getColumn());
            t.setYCoordinate(t.getRow());
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

    /**
     * Gets the list of tiles within this shape
     * @return  The tiles within this shape as a list
     */
    @Override
    public List<Tile> getTiles() {
        return tiles;
    }
}
