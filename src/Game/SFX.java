package Game;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.IOException;

public class SFX {
    private static final String[] SFX_PATHS = {System.getProperty("user.dir") + "\\audio\\blockmove.wav",
            System.getProperty("user.dir") + "\\audio\\blockplace.wav",
            System.getProperty("user.dir") + "\\audio\\blockrotate.wav",
            System.getProperty("user.dir") + "\\audio\\gameover.wav",
            System.getProperty("user.dir") + "\\audio\\lineclear.wav",
            System.getProperty("user.dir") + "\\audio\\linecleartetris.wav",
            System.getProperty("user.dir") + "\\audio\\levelup.wav"};

    /**
     * Plays the desired audio clip gotten from the audio folder
     * @param identifier    The identifier code for the desired audio clip starting at 0
     */
    public void playClip(int identifier) {
        try {
            File file = new File(SFX_PATHS[identifier]);
            AudioClip clip = new AudioClip(file.toURI().toURL().toString());
            clip.play();
        } catch (IOException e) {
            System.out.println("Audio files are missing or corrupted. Please redownload the audio" +
                    " folder.");
        }
    }
}
