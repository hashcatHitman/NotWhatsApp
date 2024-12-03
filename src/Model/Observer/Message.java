package Model.Observer;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Paige Grimes Message.java The Subject
 */
public class Message {

    private final List<nwaClient> users;

    public Message() {
        this.users = new ArrayList<>();
    }

    public void addClient(User user) {
        users.add(user);
    }

    public void removeClient(User user) {
        users.remove(user);
    }

    public void sendMessage(String message) throws BadLocationException {
        for (nwaClient user : users) {
            user.receiveMessage(user.getUsername(), message);
        }
    }
}
