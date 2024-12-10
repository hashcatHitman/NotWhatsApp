package Model.Sound;
import java.io.File;
import javax.sound.sampled.*;


/*
@author Ryan F
This class is used to play sound effects
//put in where receiveMessage under User.java
https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator

 for sound file I used:
 https://pixabay.com/sound-effects/search/messaging/
 and i converted it to .wav format using:
https://cloudconvert.com/mp3-to-wav
 */
public class Sound {
    private String soundFilePath;

    /**
     * Constructor that sets the sound file path.
     *
     * @param soundFilePath The path to the sound file.
     */
    public Sound(String soundFilePath) {
        this.soundFilePath = soundFilePath;
    }

    public void playSound() throws Exception {
        try {
            // Open an audio input stream.
            File soundFile =
                    new File(soundFilePath);
            if (!soundFile.exists()) { //console statement for no sound file
                // found at path
                throw new Exception("Sound file not found: " + soundFilePath);
            }
            // Get the audio input stream from the sound file
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile);
            // Get clip object
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream
            clip.open(audioStream);

            //play the sound
            clip.start();
            //need delay before closing clip object to allow sound to play
            Thread.sleep(4000); //the sound is about 4 seconds long
            // close the clip object
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
