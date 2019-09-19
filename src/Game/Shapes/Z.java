package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Z implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public Z(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
            if (i == 0) {
                t.setCoordinates(360, 80);
            } else if (i == 1) {
                t.setCoordinates(380, 80);
            } else if (i == 2) {
                t.setCoordinates(380, 100);
            } else {
                t.setCoordinates(400, 100);
            }
            tiles.add(t);
        }
    }

    public void updateImage(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
        }
    }

    @Override
    public void updateCoordinates(int col, int row) {

    }

    //    @Override
    public void spawn(StackPane pane) {

    }

    @Override
    public int getColumn() {
        return 0;
    }

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public void spawn(Pane pane) {
        pane.getChildren().addAll(tiles);
    }
}
