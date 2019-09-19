package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class L implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public L(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
            if (i < 3) {
                t.setCoordinates(400 + (i * 20), 300);
            } else {
                t.setCoordinates(400, 320);
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

    @Override
    public void leftRotate() {

    }

    @Override
    public void rightRotate() {

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
