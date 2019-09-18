package Game.Shapes;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class J implements Shape {
    private List<Tile> tiles = new ArrayList<>();

    public J(int level) {
        while (level > 10) {
            level -= 10;
        }
        for (int i = 0; i < 4; i++) {
            Tile t = new Tile();
            t.setImage(new Image("Assets.S_J_" + level + ".png"));
            tiles.add(t);
        }
    }

    @Override
    public void spawn() {

    }
}
