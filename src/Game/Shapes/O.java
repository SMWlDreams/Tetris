package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class O implements Shape {
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;

    public O(int level, boolean next) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                if (i % 2 == 0) {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[1], VALID_NEXT_Y_COORDINATES[i/2]);
                } else {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[2], VALID_NEXT_Y_COORDINATES[i/2]);
                }
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Bar_Box_" + level + ".png"));
                if (i % 2 == 0) {
                    t.setCoordinates(VALID_X_COORDINATES[1], VALID_Y_COORDINATES[i/2]);
                    t.setCoordinates(5, i/2);
                } else {
                    t.setCoordinates(VALID_X_COORDINATES[2], VALID_Y_COORDINATES[i/2]);
                    t.setCoordinates(6, i/2);
                }
                tiles.add(t);
            }
            column = 4;
            row = 0;
        }
    }

    public void updateImage(int level) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
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
                t.setCoordinates(t.getX(), t.getY() + VALID_COORDINATE_MODIFIERS[0]);
            }
        }
        if (col < column) {
            column--;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY());
            }
        } else if (col > column) {
            column++;
            for (Tile t : tiles) {
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY());
            }
        }
    }

    /**
     * You cant rotate this block you dummy
     */
    @Override
    public void leftRotate() {
    }

    /**
     * You cant rotate this block you dummy
     */
    @Override
    public void rightRotate() {
    }

    @Override
    public void unload(Pane pane) {
        pane.getChildren().removeAll(tiles);
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

    @Override
    public List<Tile> getTiles() {
        return tiles;
    }
}
