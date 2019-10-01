package Game;

import javafx.scene.media.AudioClip;

import java.io.File;

public class SFX {
    private static final String[] SFX_PATHS = {System.getProperty("user.dir") + "\\src\\blockmove.wav",
            System.getProperty("user.dir") + "\\src\\blockplace.wav",
            System.getProperty("user.dir") + "\\src\\blockrotate.wav",
            System.getProperty("user.dir") + "\\src\\gameover.wav",
            System.getProperty("user.dir") + "\\src\\lineclear.wav",
            System.getProperty("user.dir") + "\\src\\linecleartetris.wav",
            System.getProperty("user.dir") + "\\src\\levelup.wav"};

    public void playClip(int identifier) {
        try {
            File file = new File(SFX_PATHS[identifier]);
            AudioClip clip = new AudioClip(file.toURI().toURL().toString());
            clip.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
