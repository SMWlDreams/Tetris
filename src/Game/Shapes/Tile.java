package Game.Shapes;

import javafx.animation.StrokeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile extends Rectangle {
    public static final int SQUARE_DIMENSIONS = 15;
    private boolean anchor;
    private int row = 0;
    private int column = 0;
    private int previousRow;
    private int previousColumn;
    private double previousX = 0;
    private double previousY = 0;
    private Shape shape;

    public Tile(boolean anchor) {
        this(anchor, new Dummy());
    }

    public Tile(boolean anchor, Shape shape) {
        this.shape = shape;
        this.anchor = anchor;
        this.setHeight(SQUARE_DIMENSIONS);
        this.setWidth(SQUARE_DIMENSIONS);
    }

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

    public void unload(Pane pane) {
        pane.getChildren().remove(this);
    }

    public Shape getShape() {
        return shape;
    }

    public void setImage(Image image) {
        this.setFill(new ImagePattern(image, 0, 0, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, false));
    }

    public void setXCoordinate(int column) {
        previousColumn = this.column;
        this.column = column;
    }

    public void setYCoordinate(int row) {
        previousRow = this.row;
        this.row = row;
    }

    public void revert() {
        if (!anchor) {
            row = previousRow;
            column = previousColumn;
            this.setX(previousX);
            this.setY(previousY);
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setAnchor(boolean bool) {
        anchor = bool;
    }
}
