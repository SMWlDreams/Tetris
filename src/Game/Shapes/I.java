package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class I implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public I(int level, boolean next) {
        while (level >= 10) {
            level -= 10;
        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                t.setCoordinates(420 + (15 * i), 225);
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                t.setCoordinates(270 + (15 * i), 75);
                tiles.add(t);
            }
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
