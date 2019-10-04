package Game;

import javafx.application.Platform;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.io.JavaSoundAudioIO;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.SamplePlayer;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Music {
    private static final String[] BACKGROUND_TRACK_PATHS = {System.getProperty("user.dir") + "\\audio\\Theme_1.wav",
            System.getProperty("user.dir") + "\\audio\\Theme_2.wav",
            System.getProperty("user.dir") + "\\audio\\register_high_scores.wav",
            null,
            System.getProperty("user.dir") + "\\audio\\special_song.wav"};

    private AudioContext audioContext;
    private boolean play = false;

    public Music() {
        selectTrack(0);
    }

    public void play() {
        if (!play) {
            play = true;
            audioContext.start();
        }
    }

    public void selectTrack(int trackNumber) {
        if (!play) {
            JavaSoundAudioIO io = new JavaSoundAudioIO();
            try (Scanner in = new Scanner(new File(System.getProperty("user.home") + "\\AppData" +
                    "\\Roaming\\Tetris\\Audio.txt"))) {
                io.selectMixer(in.nextInt());
            } catch (IOException | InputMismatchException e) {
                io.selectMixer(3);
            }
//            io.selectMixer(3);
            audioContext = new AudioContext(io);
            SamplePlayer samplePlayer = new SamplePlayer(audioContext, SampleManager.sample(BACKGROUND_TRACK_PATHS[trackNumber]));
            samplePlayer.getLoopStartUGen().setValue(0);
            samplePlayer.getLoopEndUGen().setValue((float) SampleManager.sample(BACKGROUND_TRACK_PATHS[trackNumber]).getLength());
            samplePlayer.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);
            Gain gain = new Gain(audioContext, 1, 0.1f);
            gain.addInput(samplePlayer);
            audioContext.out.addInput(gain);
        }
    }

    public void stop() {
        if (play) {
            play = false;
            audioContext.stop();
        }
    }

    public void kill() {
        if (play) {
            play = false;
            audioContext.stop();
        }
        Platform.exit();
    }
}