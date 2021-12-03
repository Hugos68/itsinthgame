import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class Audio {

    AudioInputStream titleScreenSoundTrackStream;
    AudioInputStream gameScreenSoundTrackStream;
    AudioInputStream clickSoundStream;
    AudioInputStream buildSoundStream;
    AudioInputStream errorSoundStream;
    Clip titleScreenSoundTrack;
    Clip gameScreenSoundTrack;
    Clip clickSound;
    Clip buildSound;
    Clip errorSound;

    public Audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        titleScreenSoundTrackStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("titlescreensong.wav")));
        gameScreenSoundTrackStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("gamescreensong.wav")));
        clickSoundStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("click.wav")));
        buildSoundStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("build.wav")));
        errorSoundStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("errorsound.wav")));
        titleScreenSoundTrack = AudioSystem.getClip();
        gameScreenSoundTrack = AudioSystem.getClip();
        clickSound = AudioSystem.getClip();
        buildSound = AudioSystem.getClip();
    }

    public void playSoundtrack(int currentScreenState) {
        if (currentScreenState==0) {
            try {
                if (!titleScreenSoundTrack.isOpen()) {
                    titleScreenSoundTrack.open(titleScreenSoundTrackStream);
                    titleScreenSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
                }
                titleScreenSoundTrack.start();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }

        if (currentScreenState==1) {
            try {
                if(!gameScreenSoundTrack.isOpen()) {
                    gameScreenSoundTrack.open(gameScreenSoundTrackStream);
                    gameScreenSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
                }
                gameScreenSoundTrack.start();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void pauseSoundTrack(int currentScreenState) {
        if (currentScreenState==0) {
            titleScreenSoundTrack.stop();
        }
        if (currentScreenState==1) {
            gameScreenSoundTrack.stop();
        }
    }
    public void stopSoundTrack(int currentScreenActive) {
        if (currentScreenActive==0) {
            try {
                titleScreenSoundTrack.stop();
                titleScreenSoundTrack.setMicrosecondPosition(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (currentScreenActive==1) {
            try {
                gameScreenSoundTrack.stop();
                gameScreenSoundTrack.setMicrosecondPosition(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void playClickSound() {
        try {
            if (!clickSound.isOpen()) {
                clickSound.open(clickSoundStream);
            }
            clickSound.setMicrosecondPosition(0);
            clickSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playBuildSound() {
        try {
            if (!buildSound.isOpen()) {
                buildSound.open(buildSoundStream);
            }
            buildSound.setMicrosecondPosition(0);
            buildSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playErrorSound() {
        try {
            if (!errorSound.isOpen()) {
                errorSound.open(errorSoundStream);
            }
            errorSound.setMicrosecondPosition(0);
            errorSound.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
