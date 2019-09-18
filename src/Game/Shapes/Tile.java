package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    public void setImage(Image image) {
        this.setFill(new ImagePattern(image));
    }
}
