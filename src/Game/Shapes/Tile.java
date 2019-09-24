package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public static final int SQUARE_DIMENSIONS = 15;
    private int row;
    private int column;

    public Tile() {
        this.setHeight(SQUARE_DIMENSIONS);
        this.setWidth(SQUARE_DIMENSIONS);
    }

    public void setCoordinates(double x, double y) {
        if (y < 75) {
            this.setVisible(false);
            this.setX(x);
            this.setY(y);
        } else {
            this.setVisible(true);
            this.setX(x);
            this.setY(y);
        }
    }

    public void setImage(Image image) {
        this.setFill(new ImagePattern(image, 0, 0, SQUARE_DIMENSIONS, SQUARE_DIMENSIONS, false));
    }

    public void setCoordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void unload() {

    }
}
