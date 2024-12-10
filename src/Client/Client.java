package Client;

import Model.Crypto.Cipher;
import Model.Crypto.EncryptionService;
import Model.Crypto.KeyManager;
import Model.Crypto.KeyManagerShiftDH;
import Model.Crypto.ShiftCipher;
import View.TextChannel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <p>
 * A Client task that can be run to connect to a Server and exchange encrypted
 * Messages.
 * </p>
 *
 * @author Sam K
 * @author Ryan F - made changes to allow for observer pattern (worked with
 * Paige)
 * @version 12/9/2024
 */
public class Client implements Runnable {
// Attributes

    /**
     * <p>
     * The host name to connect to, as a String.
     * </p>
     */
    private final String host;

    /**
     * <p>
     * The port number to connect to the host on, as an integer.
     * </p>
     */
    private final int port;

    /**
     * <p>
     * This Client's username, as a String.
     * </p>
     */
    private final String username;
// Getters and Setters

    /**
     * <p>
     * Gets the host name, as a String.
     * </p>
     *
     * @return The host name, as a String.
     */
    private String getHost() {
        return this.host;
    }

    /**
     * <p>
     * Gets the port number, as an integer.
     * </p>
     *
     * @return The port number, as an integer.
     */
    private int getPort() {
        return this.port;
    }

    /**
     * <p>
     * Gets this Client's username, as a String.
     * </p>
     *
     * @return This Client's username, as a String.
     */
    private String getUsername() {
        return this.username;
    }

// Constructors

    /**
     * <p>
     * Constructs a new Client and attempts to connect to the host.
     * </p>
     *
     * @param host     The host name to connect to, as a String.
     * @param port     The port number to connect to the host on, as an
     *                 integer.
     * @param username The Client's username, as a String.
     */
    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

// Methods

    /**
     * <p>
     * Establishes a shared secret between this Client and it's
     * ServerClientHandler. Does this with another Client as well. Spawns a
     * NetworkListener and NetworkRelay to exchange Messages with the Server.
     * </p>
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(this.getHost(), this.getPort())) {
            System.out.println(Thread.currentThread().getName() +
                               " connected to server:\t" +
                               socket.getInetAddress());

            // Create ObjectOutputStream to send objects
            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());
            // Create ObjectInputStream to receive objects
            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());

            // Set up Cryptography and establish shared secrets
            KeyManager myKeys = new KeyManagerShiftDH();
            Cipher cipher = new ShiftCipher();
            EncryptionService encryptionService =
                    new EncryptionService(myKeys, cipher, in, out);
            encryptionService.establishSecret();

            // Creating the textChannel object
            TextChannel textChannel = new TextChannel(this.getUsername());
            textChannel.setVisible(true);
            // Create a listener and relay
            NetworkListener listener =
                    new NetworkListener(in, encryptionService,
                                        textChannel.getMessage());
            NetworkRelay relay = new NetworkRelay(encryptionService, out,
                                                  this.getUsername());

            // Let listener update the GUI using the observer pattern
            listener.setMessageNotification(textChannel.getMessage());

            // Allows message controller (using TextChannel) to send messages
            // using a relay approach
            textChannel.setNetworkRelay(relay);

            // Spawn listener and relay Threads
            Thread listeningThread = new Thread(listener, Thread.currentThread()
                                                                .getName() +
                                                          " Listening Thread");
            Thread relayThread = new Thread(relay,
                                            Thread.currentThread().getName() +
                                            " Relay Thread");

            // Start the threads
            listeningThread.start();
            relayThread.start();

            // Wait for them to finish
            listeningThread.join();
            relayThread.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
