package Model.Observer;

import javax.swing.text.BadLocationException;

/**
 * Paige Grimes nwaClient.java (NotWhatsApp Client) The interface for the users
 */
public interface nwaClient {
    String getUsername();

    void receiveMessage(String username, String message)
    throws BadLocationException;
}
