package Client;

import Model.Crypto.EncryptionService;
import Model.Message;

import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * <p>
 * The NetworkRelay task sends Objects over the network.
 * </p>
 *
 * @author Sam K
 * @author Ryan F
 * @version 12/9/2024
 */
public class NetworkRelay implements Runnable {
// Attributes

    /**
     * <p>
     * The owner's EncryptionService, for encrypting and decrypting messages.
     * </p>
     */
    private final EncryptionService encryptionService;

    /**
     * <p>
     * The owner's ObjectOutputStream, for sending Objects over the network.
     * </p>
     */
    private final ObjectOutputStream out;

    /**
     * <p>
     * The owner's username, as a String.
     * </p>
     */
    private final String username;

    /**
     * <p>
     * The current message object
     * </p>
     */
    private Message currentMessage;

    /**
     * <p>
     *     A setter for the current Message. This notifies the relay thread it
     *     got a message to send
     *     Ryan F
     * </p>
     */
    public synchronized void sendCurrentMessage(Message message) {
        this.currentMessage = message;
        notify(); //notify the relay thread that it has a message to send
    }

// Getters and Setters

    /**
     * <p>
     * Gets the owner's EncryptionService, for encrypting and decrypting
     * messages.
     * </p>
     *
     * @return The owner's EncryptionService, for encrypting and decrypting
     * messages.
     */
    private EncryptionService getEncryptionService() {
        return this.encryptionService;
    }

    /**
     * <p>
     * Gets the owner's ObjectOutputStream, for sending Objects over the
     * network.
     * </p>
     *
     * @return The owner's ObjectOutputStream, for sending Objects over the
     * network.
     */
    private ObjectOutputStream getOut() {
        return this.out;
    }

    /**
     * <p>
     * Gets the owner's username, as a String.
     * </p>
     *
     * @return The owner's username, as a String.
     */
    private String getUsername() {
        return this.username;
    }

// Constructors

    /**
     * <p>
     * Constructs a new NetworkRelay task.
     * </p>
     *
     * @param encryptionService The owner's EncryptionService, for encrypting
     *                          and decrypting messages.
     * @param out               The owner's ObjectOutputStream, for sending
     *                          Objects over the network.
     * @param username          The owner's username, as a String.
     */
    public NetworkRelay(EncryptionService encryptionService,
                        ObjectOutputStream out, String username) {
        this.encryptionService = encryptionService;
        this.out = out;
        this.username = username;
    }

// Methods

    /**
     * <p>
     * Sends Objects over the network.
     * </p>
     */
    @Override
    public void run() {
        try {
            //loop for sending messages
            while (true) {
                // Wait until we have a currentMessage to send (notified from
                // setCurrentMessage())
                synchronized (this) { // syncrhonized does not allow multiple
                    // threads to access the same block of code
                    while (currentMessage == null) {
                        wait(); // Wait until setCurrentMessage() is called
                    }
                }

                // Create a copy of the current message to send
                Message messageToSend = currentMessage;
                currentMessage = null; // Reset current message for next
                // time, so the thread waits for the next message, otherwise
                // it will keep sending the same message

                // Print out the message the user wants to send
                System.out.println(
                        Thread.currentThread().getName() + " wants to send:\t" +
                        messageToSend);

                // Encrypt it
                Message encrypted =
                        this.getEncryptionService().encrypt(messageToSend);
                System.out.println(Thread.currentThread().getName() +
                                   " is sending the message (encrypted):\t" +
                                   encrypted);

                // Send it
                this.getOut().writeObject(encrypted);
                this.getOut().flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}