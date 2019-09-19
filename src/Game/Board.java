package Game;

import Game.Shapes.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Shape> shapes = new ArrayList<>();
    int level = 0;

    public void nextFrame(Pane pane) {
        level++;
        for (Shape s : shapes) {
            s.updateImage(level);
        }
    }

    public void drawAllShapes(Pane pane) {
        Shape shape = new I(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new J(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new L(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new Z(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new S(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new Cube(0);
        shape.spawn(pane);
        shapes.add(shape);
        shape = new T(0);
        shape.spawn(pane);
        shapes.add(shape);
    }
}
