package Model.Observer;

import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * nwaClient.java (NotWhatsApp Client)
 * The interface for the observer class.
 *
 * @author Paige Grimes
 */
public interface nwaClient {
    String getUsername();

    Color getColor();

    void receiveMessage(String username, String message, Color color)
    throws BadLocationException;
}
