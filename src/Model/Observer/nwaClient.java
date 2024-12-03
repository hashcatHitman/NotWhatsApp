package Model.Observer;

import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * Paige Grimes nwaClient.java (NotWhatsApp Client) The interface for the users
 */
public interface nwaClient {
    String getUsername();

    Color getColor();

    void receiveMessage(String username, String message, Color color)
    throws BadLocationException;
}
