package Game.Shapes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Z implements Shape {
    private List<Tile> tiles = new ArrayList<>();
    private int column;
    private int row;
    private int rotation = 0;

    public Z(int level, boolean next) {
        while (level >= Shape.LEVEL_IMAGE_LOOP) {
            level -= Shape.LEVEL_IMAGE_LOOP;
        }
        if (next) {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
                if (i == 0) {
                    t.setCoordinates(450, 225);
                } else if (i == 1) {
                    t.setCoordinates(435, 225);
                } else if (i == 2) {
                    t.setCoordinates(435, 210);
                } else {
                    t.setCoordinates(420, 210);
                }
                tiles.add(t);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Tile t = new Tile();
                t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
                if (i == 0) {
                    t.setCoordinates(330, 90);
                } else if (i == 1) {
                    t.setCoordinates(315, 90);
                } else if (i == 2) {
                    t.setCoordinates(315, 75);
                } else {
                    t.setCoordinates(300, 75);
                }
                tiles.add(t);
            }
            row = 0;
            column = 4;
        }
    }

    public void updateImage(int level) {
        while (level >= Shape.LEVEL_IMAGE_LOOP) {
            level -= Shape.LEVEL_IMAGE_LOOP;
        }
        for (Tile t : tiles) {
            t.setImage(new Image("\\Assets\\Z_L_" + level + ".png"));
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
                t.setCoordinates(t.getX(), t.getY() - 30);
                t = tiles.get(1);
                t.setCoordinates(t.getX() + 15, t.getY() - 15);
                t = tiles.get(3);
                t.setCoordinates(t.getX() + 15, t.getY() + 15);
                break;
            case 1:
                rotation--;
                t = tiles.get(0);
                t.setCoordinates(t.getX(), t.getY() + 30);
                t = tiles.get(1);
                t.setCoordinates(t.getX() - 15, t.getY() + 15);
                t = tiles.get(3);
                t.setCoordinates(t.getX() - 15, t.getY() - 15);
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
