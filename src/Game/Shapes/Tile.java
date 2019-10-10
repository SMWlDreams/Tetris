package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Holds information for each individual tile
 */
public class Tile extends Rectangle {
    /**
     * The dimensions (length and width) of each individual tile
     */
    public static final int SQUARE_DIMENSIONS = 15;

    private boolean anchor;
    private int row = 0;
    private int column = 0;
    private int previousRow;
    private int previousColumn;
    private double previousX = 0;
    private double previousY = 0;
    private Shape shape;

    /**
     * Creates a new tile using the dummy shape
     * @param anchor    Always FALSE for this method
     */
    public Tile(boolean anchor) {
        this(anchor, new Dummy());
    }

    /**
     * Creates a new tile in the specified shape
     * @param anchor    True if it is the anchor (non-moving) shape, false if otherwise
     * @param shape     The shape this tile is a part of
     */
    public Tile(boolean anchor, Shape shape) {
        this.shape = shape;
        this.anchor = anchor;
        this.setHeight(SQUARE_DIMENSIONS);
        this.setWidth(SQUARE_DIMENSIONS);
    }

    /**
     * Sets the coordinates of this tile and saves the previous coordinates in the event they
     * need to be restored. Will make the tiles invisible if they are off the top of the screen
     * @param x The new X coordinate for the tile
     * @param y The new Y coordinate for the tile
     */
    public void setCoordinates(double x, double y) {
        if (y < 75) {
            this.setVisible(false);
            previousX = this.getX();
            previousY = this.getY();
            this.setX(x);
            this.setY(y);
        } else {
            this.setVisible(true);
            previousX = this.getX();
            previousY = this.getY();
            this.setX(x);
            this.setY(y);
        }
    }

    /**
     * Removes this tile from the specified pane
     * @param pane  The pane holding a reference to this tile
     */
    public void unload(Pane pane) {
        pane.getChildren().remove(this);
    }

    /**
     * Gets the shape this tile is a part of
     * @return  The shape this tile is in
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Changes the image for this tile to the one provided
     * @param image The new image to draw on to this tile
     */
    public void setImage(Image image) {
        this.setFill(new ImagePattern(image, 0, 0, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, false));
    }

    /**
     * Sets the column index for this tile and backups the old one if it needs to be reverted
     * @param column    The new column this tile is located in
     */
    public void setXCoordinate(int column) {
        previousColumn = this.column;
        this.column = column;
    }

    /**
     * Sets the row index for this tile and backups the old one if it needs to be reverted
     * @param row   The new row this tile is located in
     */
    public void setYCoordinate(int row) {
        previousRow = this.row;
        this.row = row;
    }

    /**
     * Reverts the (x,y) coordinate pair, the column index and the row index in the event a
     * transformation (rotation or block movement) is determined to be invalid after
     */
    public void revert() {
        if (!anchor) {
            row = previousRow;
            column = previousColumn;
            this.setX(previousX);
            this.setY(previousY);
            if (previousY < 75) {
                this.setVisible(false);
            } else {
                this.setVisible(true);
            }
        }
    }

    /**
     * Gets the current column index for this specific tile
     * @return  The column index as an int
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the current row index for this specific tile
     * @return  The row index as an int
     */
    public int getRow() {
        return row;
    }

    /**
     * Changes if this tile is an anchor for the shape or not
     * @param bool  True if it is now an anchor, false if it is not
     */
    public void setAnchor(boolean bool) {
        anchor = bool;
    }
}
