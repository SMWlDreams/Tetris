package Game.Shapes;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Cube implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public Cube(int level) {
        while (level > 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("Assets.Bar_Box_" + level + ".png"));
            tiles.add(t);
        }
    }

    @Override
    public void spawn() {

    }
}
