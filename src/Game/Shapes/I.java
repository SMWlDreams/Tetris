package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class I implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public I(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
            t.setCoordinates(200 + (20 * i), 200);
            tiles.add(t);
        }
    }

    public void updateImage(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
        }
    }

    @Override
    public void spawn(Pane pane) {
//        for (Tile t : tiles) {
//            pane.getChildren().add(t);
//        }
        pane.getChildren().addAll(tiles);
    }
}
