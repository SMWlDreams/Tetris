package Game.Shapes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface Shape {
    int getColumn();

    int getRow();

    void spawn(Pane pane);

    void updateImage(int level);

    void updateCoordinates(int col, int row);

    void leftRotate();

    void rightRotate();
}
