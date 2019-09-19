package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class S implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public S(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("\\Assets\\S_J_" + level + ".png"));
            if (i == 0) {
                t.setCoordinates(100, 100);
            } else if (i == 1) {
                t.setCoordinates(120, 100);
            } else if (i == 2) {
                t.setCoordinates(120, 80);
            } else {
                t.setCoordinates(140, 80);
            }
            tiles.add(t);
        }
    }

    public void updateImage(int level) {
        while (level >= 10) {
            level -= 10;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\S_J_" + level + ".png"));
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
