package Client;

import Model.Crypto.EncryptionService;
import Model.Message;
import Model.Observer.MessageNotification;

import java.io.ObjectInputStream;

/**
 * <p>
 * The NetworkListener task listens for Objects being sent over the network.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class NetworkListener implements Runnable {
// Attributes

    /**
     * <p>
     * The owner's EncryptionService, for encrypting and decrypting messages.
     * </p>
     */
    private final EncryptionService encryptionService;

    /**
     * <p>
     * The owner's ObjectInputStream, for receiving Objects sent over the
     * network.
     * </p>
     */
    private final ObjectInputStream in;

    //field for storing messagenotification
    private MessageNotification messageNotification;

    //setter for messageNotification
    public void setMessageNotification(MessageNotification notification) {
        this.messageNotification = notification;
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
     * Gets the owner's ObjectInputStream, for receiving Objects sent over the
     * network.
     * </p>
     *
     * @return the owner's ObjectInputStream, for receiving Objects sent over
     * the network.
     */
    private ObjectInputStream getIn() {
        return this.in;
    }

// Constructors

    /**
     * <p>
     * Constructs a new NetworkListener task.
     * </p>
     *
     * @param in                The owner's ObjectInputStream, for receiving
     *                          Objects sent over the network.
     * @param encryptionService The owner's EncryptionService, for encrypting
     *                          and decrypting messages.
     */
    public NetworkListener(ObjectInputStream in,
                           EncryptionService encryptionService) {
        this.in = in;
        this.encryptionService = encryptionService;
    }

// Methods

    /**
     * <p>
     * Listens for Objects being sent over the network and handles them
     * appropriately.
     * </p>
     */
    @Override
    public void run() {
        try {
            while (true) {
                // Wait for the server's response
                System.out.println(Thread.currentThread().getName() +
                                   " is waiting for an Object....");
                Object readObject = this.getIn().readObject();
                System.out.println(Thread.currentThread().getName() +
                                   " received an Object!");

                /*
                 * TODO
                 *  - Actually display received messages in the GUI.
                 *  - Listen for special Client-Server communication Objects?
                 */
                // If it's a Message, decrypt it and display
                if (readObject instanceof Message response) {
                    System.out.println(Thread.currentThread().getName() +
                                       " received an encrypted response from " +
                                       "the server:\t" + response);
                    Message decrypted =
                            this.getEncryptionService().decrypt(response);
                    System.out.println(Thread.currentThread().getName() +
                                       " decrypted the server response and " +
                                       "got:\t" + decrypted);
                    //Update the gui with the message
                    if (messageNotification != null) {
                        messageNotification.sendMessage(decrypted); //this
                        // triggers the update method in the gui
                        // (receiveMessage)
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
