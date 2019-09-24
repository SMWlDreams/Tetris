package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class S implements Shape {
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;
    private int rotation = 0;

    public S(int level, boolean next) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\S_J_" + level + ".png"));
                if (i == 0) {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[1], VALID_NEXT_Y_COORDINATES[1]);
                } else if (i == 1) {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[2], VALID_NEXT_Y_COORDINATES[1]);
                } else if (i == 2) {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[2], VALID_NEXT_Y_COORDINATES[0]);
                } else {
                    t.setCoordinates(VALID_NEXT_X_COORDINATES[3], VALID_NEXT_Y_COORDINATES[0]);
                }
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\S_J_" + level + ".png"));
                if (i == 0) {
                    t.setCoordinates(VALID_X_COORDINATES[1], VALID_Y_COORDINATES[1]);
                    t.setCoordinates(5, 1);
                } else if (i == 1) {
                    t.setCoordinates(VALID_X_COORDINATES[2], VALID_Y_COORDINATES[1]);
                    t.setCoordinates(6, 1);
                } else if (i == 2) {
                    t.setCoordinates(VALID_X_COORDINATES[2], VALID_Y_COORDINATES[0]);
                    t.setCoordinates(6, 0);
                } else {
                    t.setCoordinates(VALID_X_COORDINATES[3], VALID_Y_COORDINATES[0]);
                    t.setCoordinates(7, 0);
                }
                tiles.add(t);
            }
            row = 0;
            column = 4;
        }
    }

    public void updateImage(int level) {
        while (level >= LEVEL_IMAGE_LOOP) {
            level -= LEVEL_IMAGE_LOOP;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\S_J_" + level + ".png"));
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
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[1], t.getY());
                t = tiles.get(1);
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY() - VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY() - VALID_COORDINATE_MODIFIERS[0]);
                break;
            case 1:
                rotation--;
                t = tiles.get(0);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[1], t.getY());
                t = tiles.get(1);
                t.setCoordinates(t.getX() - VALID_COORDINATE_MODIFIERS[0], t.getY() + VALID_COORDINATE_MODIFIERS[0]);
                t = tiles.get(3);
                t.setCoordinates(t.getX() + VALID_COORDINATE_MODIFIERS[0], t.getY() + VALID_COORDINATE_MODIFIERS[0]);
                break;
        }
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
