package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class I implements Shape {
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;
    private int rotation = 0;

    public I(int level, boolean next) {
        while (level >= Shape.LEVEL_IMAGE_LOOP) {
            level -= Shape.LEVEL_IMAGE_LOOP;
        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                t.setCoordinates(VALID_NEXT_X_COORDINATES[i], VALID_NEXT_Y_COORDINATES[0]);
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                t.setCoordinates(VALID_X_COORDINATES[i], VALID_Y_COORDINATES[0]);
                tiles.add(t);
            }
            column = 4;
            row = 0;
        }
    }

    public void updateImage(int level) {
        while (level >= Shape.LEVEL_IMAGE_LOOP) {
            level -= Shape.LEVEL_IMAGE_LOOP;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
        }
    }

    @Override
    public void updateCoordinates(int col, int row) {
        if (row > this.row) {
            this.row++;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX(), t.getY() + Shape.VALID_COORDINATE_MODIFIERS[0]);
            }
        }
        if (col < column) {
            column--;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() - Shape.VALID_COORDINATE_MODIFIERS[0], t.getY());
            }
        } else if (col > column) {
            column++;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() + Shape.VALID_COORDINATE_MODIFIERS[0], t.getY());
            }
        }
    }

    @Override
    public void leftRotate() {
        rightRotate();
    }

    @Override
    public void rightRotate() {
        Tile t;
        switch (rotation) {
            case 0:
                rotation++;
                t = tiles.get(0);
                t.setCoordinates(t.getX() + Shape.VALID_COORDINATE_MODIFIERS[1], t.getY() - Shape.VALID_COORDINATE_MODIFIERS[1]);
                t = tiles.get(1);
                t.setCoordinates(t.getX() + Shape.VALID_COORDINATE_MODIFIERS[0], t.getY() - Shape.VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setCoordinates(t.getX() - Shape.VALID_COORDINATE_MODIFIERS[0], t.getY() + Shape.VALID_COORDINATE_MODIFIERS[0]);
                break;
            case 1:
                rotation--;
                t = tiles.get(0);
                t.setCoordinates(t.getX() - Shape.VALID_COORDINATE_MODIFIERS[1], t.getY() + Shape.VALID_COORDINATE_MODIFIERS[1]);
                t = tiles.get(1);
                t.setCoordinates(t.getX() - Shape.VALID_COORDINATE_MODIFIERS[0], t.getY() + Shape.VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setCoordinates(t.getX() + Shape.VALID_COORDINATE_MODIFIERS[0], t.getY() - Shape.VALID_COORDINATE_MODIFIERS[0]);
                break;
        }
    }

    @Override
    public void unload(Pane pane) {
        pane.getChildren().removeAll(tiles);
    }

    @Override
    public List<Tile> getTiles() {
        return tiles;
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
        pane.getChildren().addAll(tiles);
    }
}
