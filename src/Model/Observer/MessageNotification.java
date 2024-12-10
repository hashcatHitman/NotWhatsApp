package Model.Observer;

import Model.Message;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The Publisher of the observer The MessageNotification class keeps track of
 * each active user on the server. When a user sends a message, the
 * sendMessage() method will iterate through each user/client (The observers)
 * and update their individual GUIs.
 * </p>
 *
 * @author Paige Grimes
 */
public class MessageNotification {

    private final List<nwaClient> users;

    /**
     * Create the MessageNotification object and initialize the List to hold the
     * users.
     */
    public MessageNotification() {
        this.users = new ArrayList<>();
    }

    /**
     * Subscribes the client/user to chat notifications.
     *
     * @param user The User object that will be subscribed to notifications.
     */
    public void addClient(User user) {
        users.add(user);
    }

    /**
     * <p>
     * Iterates through the list of Users and sends them the new message
     * </p>
     *
     * @param message A String of the users' message
     *
     * @throws BadLocationException Needed for a handling style document
     *                              exceptions in addMessage() in TextChannel
     */
    public void sendMessage(Message message) throws BadLocationException {
        // For each active user on the server, call receiveMessage() to notify
        // them of a new message
        for (nwaClient user : users) {
            user.receiveMessage(message, user.getColor());
        }
    }
}
