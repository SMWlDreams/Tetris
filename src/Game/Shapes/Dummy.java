package Game.Shapes;

import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Dummy shape for tiles that were not part of a shape
 */
public class Dummy implements Shape {

    /**
     * Gets the current column index of this shape
     * @return  Column index as an int
     */
    @Override
    public int getColumn() {
        return 0;
    }

    /**
     * Gets the current row index of this shape
     * @return  Row index as an int
     */
    @Override
    public int getRow() {
        return 0;
    }

    /**
     * Spawns this shape and loads its tiles
     * @param pane  Pane to load the tiles to
     */
    @Override
    public void spawn(Pane pane) {

    }

    /**
     * Updates the image for this shape and by nature all of its tiles
     * @param level The new level to load the image
     */
    @Override
    public void updateImage(int level) {

    }

    /**
     * Rotates this shape to the left
     */
    @Override
    public void leftRotate() {

    }

    /**
     * Rotates this shape to the right
     */
    @Override
    public void rightRotate() {

    }

    /**
     * Unloads this shape and all its sub-tiles
     * @param pane  The pane to unload the sub-tiles from
     */
    @Override
    public void unload(Pane pane) {

    }

    /**
     * Reverses the rotation if the rotation is determined to be invalid
     */
    @Override
    public void undoRotation() {

    }

    /**
     * Gets the list of tiles within this shape
     * @return  The tiles within this shape as a list
     */
    @Override
    public List<Tile> getTiles() {
        return null;
    }
}
