package Game.Shapes;

import javafx.scene.layout.Pane;

import java.util.List;

public interface Shape {
    double[] VALID_X_COORDINATES = {285, 300, 315, 330};
    double[] VALID_Y_COORDINATES = {75, 90};
    double[] VALID_NEXT_X_COORDINATES = {420, 435, 450, 465};
    double[] VALID_NEXT_Y_COORDINATES = {210, 225};
    double[] VALID_COORDINATE_MODIFIERS = {15, 30};
    int LEVEL_IMAGE_LOOP = 10;

    int getColumn();

    int getRow();

    void spawn(Pane pane);

    void updateImage(int level);

    void updateCoordinates(int col, int row);

    void leftRotate();

    void rightRotate();

    void unload(Pane pane);

    List<Tile> getTiles();
}
