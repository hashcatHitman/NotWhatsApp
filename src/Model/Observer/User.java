package Model.Observer;
/**
 * User.java The Observer object (Users) that will receive notifications
 *
 * @author Paige
 */

import View.TextChannel;

import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.util.Random;

public class User implements nwaClient {

    private final String username;

    private final TextChannel textChannel;

    private final Color color;

    /**
     * Creates a User object and sets their username, the color their name will
     * appear as, and sets the location to receive notifications as the chat
     * windows JFame.
     *
     * @param username Sets the users' username
     * @param textChannel Sets the location to receive notifications
     */

    public User(String username, TextChannel textChannel) {
        this.username = username;
        this.color = setColor();
        this.textChannel = textChannel;
    }

    /**
     * Getters for the username and color so other classes can access them
     */
    public String getUsername() {
        return username;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * setColor() assigns the user a random color that will be assigned in the
     * classes constructor then applied to their username in the GUI.
     */
    public Color setColor() {
        Random rand = new Random();

        float r = (rand.nextFloat());
        float g = (rand.nextFloat());
        float b = (rand.nextFloat());

        return new Color(r, g, b);
    }

    /**
     * receiveMessage() is called from the MessageNotification class then calls
     * the textChannel's addMessage() method which will update the GUI with the
     * user's username, assigned color, and their message.
     *
     * @throws BadLocationException Needed for handling style document styling
     * exceptions in addMessage() in TextChannel
     */
    @Override
    public void receiveMessage(String username, String message, Color color)
    throws BadLocationException {
        textChannel.addMessage(message, getColor());
    }

}

