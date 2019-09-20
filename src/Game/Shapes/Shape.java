package Game.Shapes;

import javafx.scene.layout.Pane;

import java.util.List;

public interface Shape {
    public int[] VALID_X_COORDINATES = {285, 300, 315, 330};
    public int[] VALID_Y_COORDINATES = {75, 90};
    public int[] VALID_NEXT_X_COORDINATES = {420, 435, 450, 465};
    public int[] VALID_NEXT_Y_COORDINATES = {225, 210};
    public int[] VALID_COORDINATE_MODIFIERS = {15, 30};
    public int LEVEL_IMAGE_LOOP = 10;

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
