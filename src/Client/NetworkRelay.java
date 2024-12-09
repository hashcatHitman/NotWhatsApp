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
            // Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Prompt user for input
                System.out.print("Enter message (type 'exit' to quit):\t");
                String userInput = scanner.nextLine();

                // Create a new Message object with the user input
                Message message = new Message(userInput, this.getUsername());
                System.out.println(
                        Thread.currentThread().getName() + " wants to send:\t" +
                        message);

                // Encrypt it
                Message encrypted =
                        this.getEncryptionService().encrypt(message);
                System.out.println(
                        Thread.currentThread().getName() + " is sending " +
                        "the message, but encrypted. It looks like:\t" +
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
