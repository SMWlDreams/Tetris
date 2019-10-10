package Game.Shapes;

import javafx.scene.layout.Pane;

import java.util.List;

/**
 * General holder for the different shapes
 */
public interface Shape {
    /**
     * Valid X Coordinates for the block to be in
     */
    double[] X_COORDS = {240, 255, 270, 285, 300, 315, 330, 345, 360, 375};
    /**
     * Valid X Coordinates for the block to spawned in
     */
    double[] VALID_X_COORDINATES = {285, 300, 315, 330};
    /**
     * Valid Y Coordinates for the block to spawned in
     */
    double[] VALID_Y_COORDINATES = {75, 90};
    /**
     * Valid X Coordinates for the block to spawned in for the next box
     */
    double[] VALID_NEXT_X_COORDINATES = {420, 435, 450, 465};
    /**
     * Valid Y Coordinates for the block to spawned in for the next box
     */
    double[] VALID_NEXT_Y_COORDINATES = {240, 255};
    /**
     * Coordinate modifiers for block rotation
     */
    double[] VALID_COORDINATE_MODIFIERS = {15, 30};
    /**
     * The number to subtract from level values greater than or equal to 10
     */
    int LEVEL_IMAGE_LOOP = 10;

    /**
     * Gets the current column index of this shape
     * @return  Column index as an int
     */
    int getColumn();

    /**
     * Gets the current row index of this shape
     * @return  Row index as an int
     */
    int getRow();

    /**
     * Spawns this shape and loads its tiles
     * @param pane  Pane to load the tiles to
     */
    void spawn(Pane pane);

    /**
     * Updates the image for this shape and by nature all of its tiles
     * @param level The new level to load the image
     */
    void updateImage(int level);

//    void updateCoordinates(int col, int row);

    /**
     * Rotates this shape to the left
     */
    void leftRotate();

    /**
     * Rotates this shape to the right
     */
    void rightRotate();

    /**
     * Unloads this shape and all its sub-tiles
     * @param pane  The pane to unload the sub-tiles from
     */
    void unload(Pane pane);

    /**
     * Reverses the rotation if the rotation is determined to be invalid
     */
    void undoRotation();

    /**
     * Gets the list of tiles within this shape
     * @return  The tiles within this shape as a list
     */
    List<Tile> getTiles();
}
