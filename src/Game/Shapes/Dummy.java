package Game.Shapes;

import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Dummy shape for tiles that were not part of a shape
 */
public class Dummy implements Shape {
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

    }

    @Override
    public void updateImage(int level) {

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

    @Override
    public void unload(Pane pane) {

    }

    @Override
    public void undoRotation() {

    }

    @Override
    public List<Tile> getTiles() {
        return null;
    }
}
