package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public Tile() {
        this.setHeight(20);
        this.setWidth(20);
    }

    public void setCoordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void setImage(Image image) {
        this.setFill(new ImagePattern(image, 0, 0, 20, 20, false));
    }
}
