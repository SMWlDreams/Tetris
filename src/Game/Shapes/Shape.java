package Game.Shapes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public interface Shape {
    void spawn(Pane pane);

    void updateImage(int level);
}
