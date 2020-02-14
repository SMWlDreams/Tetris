package Game;

public class Move {
    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean downDown = false;
    private boolean spaceDown = false;
    private boolean rotateLeftDown = false;
    private boolean rotateRightDown = false;

    public void setLeftDown(Boolean b) {
        leftDown = b;
    }

    public void setRightDown(Boolean b) {
        rightDown = b;
    }

    public void setDownDown(Boolean b) {
        downDown = b;
    }

    public void setSpaceDown(Boolean b) {
        spaceDown = b;
    }

    public void setRotateLeftDown(Boolean b) {
        rotateLeftDown = b;
    }

    public void setRotateRightDown(Boolean b) {
        rotateRightDown = b;
    }

    public boolean getLeftDown() {
        return leftDown;
    }

    public boolean getRightDown() {
        return rightDown;
    }

    public boolean getDownDown() {
        return downDown;
    }

    public boolean getSpaceDown() {
        return spaceDown;
    }

    public boolean getRotateLeftDown() {
        return rotateLeftDown;
    }

    public boolean getRotateRightDown() {
        return rotateRightDown;
    }

    public int toInt() {
        int i = 0;
        if (leftDown) i += 128;
        if (rightDown) i += 64;
        if (downDown) i += 32;
        if (spaceDown) i += 16;
        if (rotateLeftDown) i += 8;
        if (rotateRightDown) i += 4;
        return i;
    }
}
