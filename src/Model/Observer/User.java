package Model.Observer;
/**
 * <p>
 * The Users class represents each client on the server. The users choose their
 * username and get a random color assigned to the font of their name which will
 * display the same for other clients on the server. The user will know which
 * message they sent because their username will be in plain text, font color
 * black. The User class implements the interface IClient and defines the
 * receiveNotification() method which sends the Message object to the
 * TextChannel GUI and the users corresponding color.
 * </p>
 *
 * @author Paige
 */

import Control.Commands.PlaySoundCommand;
import Model.Message;
import View.TextChannel;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.util.Random;

public class User implements IClient {

    private final String username;

    private final TextChannel textChannel;

    private final Color color;

    /**
     * Creates a User object and sets their username, the color their name will
     * appear as, and sets the location to receive notifications as the chat
     * windows JFame.
     *
     * @param username    Sets the users' username
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
     *                              exceptions in addMessage() in TextChannel
     */
    @Override
    public void receiveMessage(Message message, Color color) {
        // Professor Salu reminded me about using invokeLater when rendering
        // the Swing GUI
        SwingUtilities.invokeLater(() -> {
            try {
                // If the message is being returned to the receiver, pass the
                // users color so that it displays on the other users
                // TextChannel window
                if (!message.getSender().equals(username)) {
                    textChannel.addMessage(message, getColor());
                    // Play a sound when a message is received
                    String soundFilePath =
                            "src/Model/Sound" + "/messagenotificationSound" +
                            ".wav";
                    // Create a new PlaySoundCommand object and execute it
                    PlaySoundCommand playSoundCommand =
                            new PlaySoundCommand(soundFilePath);
                    playSoundCommand.execute();
                } else {
                    // Otherwise, they sent the message; their font color is
                    // black
                    textChannel.addMessage(message, Color.BLACK);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

}
