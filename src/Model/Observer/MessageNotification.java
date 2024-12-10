package Model.Observer;

import Model.Message;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageNotification.java The publisher of the observer
 *
 * @author Paige Grimes
 */
public class MessageNotification {

    private final List<nwaClient> users;
    private int numUsers;

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
        numUsers++;
    }

    /**
     * Removes the subscriber for the chat notifications.
     *
     * @param user The User object that will be subscribed to notifications.
     */
    public void removeClient(User user) {
        users.remove(user);
        numUsers--;
    }
    public int getNumUsers() {
        return numUsers;
    }

    /**
     * Iterates through the list of Users and sends them the message
     *
     * @param message A String of the users' message
     *
     * @throws BadLocationException Needed for a handling style document
     * exceptions in addMessage() in TextChannel
     */
    public void sendMessage(Message message) throws BadLocationException {
        for (nwaClient user : users) {
            user.receiveMessage(message, user.getColor());
        }
    }
}
