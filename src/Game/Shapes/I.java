package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class I implements Shape {
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;
    private int rotation = 0;

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
                t.setCoordinates(285 + (15 * i), 75);
                tiles.add(t);
                column = 4;
                row = 0;
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
    public void updateCoordinates(int col, int row) {
        if (row > this.row){
            this.row++;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX(), t.getY() + 15);
            }
        }
        if (col < column) {
            column--;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() - 15, t.getY());
            }
        } else if (col > column) {
            column++;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() + 15, t.getY());
            }
        }
    }

    @Override
    public void leftRotate() {
    }

    @Override
    public void rightRotate() {
        if (rotation == 0) {
            rotation++;
            Tile t = tiles.get(0);
            t.setCoordinates(t.getX() + 30, t.getY() - 30);
            t = tiles.get(1);
            t.setCoordinates(t.getX() + 15, t.getY() - 15);
            t = tiles.get(3);
            t.setCoordinates(t.getX() - 15, t.getY() + 15);
        } else if (rotation == 1) {
            rotation++;
            Tile t = tiles.get(0);
            t.setCoordinates(t.getX() + 30, t.getY() + 30);
            t = tiles.get(1);
            t.setCoordinates(t.getX() + 15, t.getY() + 15);
            t = tiles.get(3);
            t.setCoordinates(t.getX() - 15, t.getY() - 15);
        } else if (rotation == 2) {
            rotation++;
            Tile t = tiles.get(0);
            t.setCoordinates(t.getX() - 30, t.getY() + 30);
            t = tiles.get(1);
            t.setCoordinates(t.getX() - 15, t.getY() + 15);
            t = tiles.get(3);
            t.setCoordinates(t.getX() + 15, t.getY() - 15);
        } else if (rotation == 3) {
            rotation = 0;
            Tile t = tiles.get(0);
            t.setCoordinates(t.getX() - 30, t.getY() - 30);
            t = tiles.get(1);
            t.setCoordinates(t.getX() - 15, t.getY() - 15);
            t = tiles.get(3);
            t.setCoordinates(t.getX() + 15, t.getY() + 15);
        }
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void spawn(Pane pane) {
//        for (Tile t : tiles) {
//            pane.getChildren().add(t);
//        }
        pane.getChildren().addAll(tiles);
    }
}
