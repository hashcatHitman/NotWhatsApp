package Control.Commands;

/*
author: Ryan F
This class is used to play sound effects using the Sound class
 */

import Model.Sound.Sound;

public class PlaySoundCommand implements Command {
    //fields for the sound file name and the sound object
    private String soundFileName;
    private Sound sound;

    public PlaySoundCommand(String soundFileName) {
        this.soundFileName = soundFileName; //This is the sound file name
        this.sound = new Sound(soundFileName); //creates a new sound object
    }
    @Override
    public void execute() {
        try { //plays the sound
            sound.playSound();
        } catch (Exception e) { //runtime error
            throw new RuntimeException(e);
        }
    }
}
