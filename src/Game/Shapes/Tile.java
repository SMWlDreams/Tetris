package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public Tile() {
        this.setHeight(15);
        this.setWidth(15);
    }

    public void setCoordinates(int x, int y) {
        if (y < 75) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
            this.setX(x);
            this.setY(y);
        }
    }

    public void setImage(Image image) {
        this.setFill(new ImagePattern(image, 0, 0, 15, 15, false));
    }
}
