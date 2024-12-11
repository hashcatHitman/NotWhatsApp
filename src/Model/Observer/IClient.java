package Model.Observer;

import Model.Message;

import javax.swing.text.BadLocationException;
import java.awt.Color;

/**
 * IClient.java (Liminal Client)
 * The interface for the observer class.
 *
 * @author Paige Grimes
 */
public interface IClient {
    String getUsername();

    Color getColor();

    void receiveMessage( Message message, Color color)
    throws BadLocationException;
}
