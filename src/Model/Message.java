package Model;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Represents a Message sent over the chat.
 * </p>
 *
 * @author Sam K
 * @author Paige G
 * @version 12/9/2024
 */
public class Message implements Serializable, Cloneable {
// Attributes

    /**
     * <p>
     * A version number used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object
     * that are compatible.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * <p>
     * A unique identifier of who sent this Message, as a String.
     * </p>
     */
    private final String sender;

    /**
     * <p>
     * The time this Message was sent, as seconds since the Unix Epoch, as a
     * long.
     * <br><br>
     * Suggested String formatting looks like the following:
     * {@snippet :
     * Message message = new Message("Hello, world!", "Alice");
     * String pattern = "MMMM dd, yyyy h:mm a";
     * long time = message.getUnixTimeStamp();
     * ZoneOffset offset = Clock.systemDefaultZone().getZone().getRules()
     *                             .getOffset(Instant.now());
     * DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
     * LocalDateTime dateTime = LocalDateTime.ofEpochSecond(time, 0, offset);
     * String result = formatter.format(dateTime);}
     * </p>
     */
    private final long unixTimeStamp;

    /**
     * <p>
     * The Message content, as an array of bytes.
     * </p>
     */
    private byte[] content;

// Getters and Setters

    /**
     * <p>
     * Gets the Message content, as an array of bytes.
     * </p>
     *
     * @return The Message content, as an array of bytes.
     */
    public byte[] getContent() {
        return this.content;
    }

    /**
     * <p>
     * Sets the Message content to the given array of bytes.
     * </p>
     *
     * @param content The array of bytes to set as the content of this Message.
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

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
        return new String(this.getContent(), StandardCharsets.UTF_8);
    }

    /**
     * <p>
     * Sets the Message content to the given String.
     * </p>
     *
     * @param text The String to set as the content of this Message.
     */
    public void setText(String text) {
        this.setContent(text.getBytes(StandardCharsets.UTF_8));
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
        this(text, sender, System.currentTimeMillis() / 1000L);
    }

    /**
     * <p>
     * Constructs a new Message at the given time.
     * </p>
     *
     * @param text          The Message content, as a String.
     * @param sender        A unique identifier of who sent this Message, as a
     *                      String.
     * @param unixTimeStamp The time this Message was sent, as seconds since the
     *                      Unix Epoch, as a long.
     */
    private Message(String text, String sender, long unixTimeStamp) {
        this.unixTimeStamp = unixTimeStamp;
        this.sender = sender;
        this.setText(text);
    }

// Methods

    /**
     * <p>
     * Creates and returns a copy of this Message.
     * </p>
     *
     * @return A copy of this Message.
     */
    @Override
    public Message clone() {
        try {
            return (Message) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

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
