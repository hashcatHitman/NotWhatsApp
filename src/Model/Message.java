package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * Represents a Message sent over the chat.
 * </p>
 *
 * @author Sam K
 * @author Paige G
 * @version 12/5/2024
 */
public class Message implements Serializable {
// Attributes

    /**
     * <p>
     * A version number used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object
     * that are compatible.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * A unique identifier of who sent this Message, as a String.
     * </p>
     */
    private final String sender;

    /**
     * <p>
     * The Message content, as a String.
     * </p>
     */
    private final String text;

    /**
     * <p>
     * The time this Message was sent, as seconds since the Unix Epoch, as a
     * long.
     * </p>
     */
    private final long unixTimeStamp;

// Getters and Setters

    /**
     * <p>
     * Gets a unique identifier of who sent this Message, as a String.
     * </p>
     *
     * @return A unique identifier of who sent this Message, as a String.
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * <p>
     * Gets the Message content, as a String.
     * </p>
     *
     * @return The Message content, as a String.
     */
    public String getText() {
        return this.text;
    }

    /**
     * <p>
     * Gets the time this Message was sent, as seconds since the Unix Epoch, as
     * a long.
     * </p>
     *
     * @return The time this Message was sent, as seconds since the Unix Epoch,
     * as a long.
     */
    public long getUnixTimeStamp() {
        return this.unixTimeStamp;
    }

// Constructors

    /**
     * <p>
     * Constructs a new Message at the current time.
     * </p>
     *
     * @param text   The Message content, as a String.
     * @param sender A unique identifier of who sent this Message, as a String.
     */
    public Message(String text, String sender) {
        this.unixTimeStamp = System.currentTimeMillis() / 1000L;
        this.sender = sender;
        this.text = text;
    }

// Methods

    /**
     * <p>
     * Returns a String representation of this Message. Only provides the
     * Message text, meant to simplify interactions with the Message.
     * </p>
     *
     * @return The Message content, as a String.
     */
    @Override
    public String toString() {
        return this.getText();
    }
}
