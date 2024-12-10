package Server;

import Model.Crypto.KeyManager;
import Model.Crypto.KeyManagerShiftDH;
import Model.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 * A Server task that can be run to allow two Clients to connect and exchange
 * encrypted Messages.
 * </p>
 *
 * @author Sam K
 * @author Ryan F - singleton implementation (private constructor, getInstance
 * method, instance variable)
 * @version 12/9/2024
 */
public class Server implements Runnable {
// Attributes

    /**
     * <p>
     * The Singleton instance.
     * </p>
     */
    private static volatile Server instance;

    /**
     * <p>
     * The ServerClientHandlers spawned by this Server to manage each Client,
     * stored in a HashMap with their Thread name as a key.
     * </p>
     */
    private final HashMap<String, ServerClientHandler> handlers;

    /**
     * <p>
     * This Server's KeyManager, which manages it's Private and Public keys and
     * allows it to derive shared secrets.
     * </p>
     */
    private final KeyManager keyManager;

    /**
     * <p>
     * The port number this Server is open on, as an integer.
     * </p>
     */
    private final int port;

// Getters and Setters

    /**
     * <p>
     * Gets the ServerClientHandlers spawned by this Server to manage each
     * Client, stored in a HashMap with their Thread name as a key.
     * </p>
     *
     * @return The ServerClientHandlers spawned by this Server to manage each
     * Client, stored in a HashMap with their Thread name as a key.
     */
    public HashMap<String, ServerClientHandler> getHandlers() {
        return this.handlers;
    }

    /**
     * <p>
     * Gets this Server's KeyManager, which manages it's Private and Public keys
     * and allows it to derive shared secrets.
     * </p>
     *
     * @return This Server's KeyManager, which manages it's Private and Public
     * keys and allows it to derive shared secrets.
     */
    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    /**
     * <p>
     * Gets the port number this Server is open on, as an integer.
     * </p>
     *
     * @return The port number this Server is open on, as an integer.
     */
    public int getPort() {
        return this.port;
    }

// Constructors

    /**
     * <p>
     * Constructs a new Server with the given KeyManager.
     * </p>
     *
     * @param port       The port number to open a Server on, as an integer.
     * @param keyManager The KeyManager to assign to this Server.
     */
    private Server(int port, KeyManager keyManager) {
        this.port = port;
        this.handlers = new HashMap<>();
        this.keyManager = keyManager;
    }

    /**
     * <p>
     * Constructs a new Server with the default KeyManager and the given port.
     * </p>
     *
     * @param port The port number to open a Server on, as an integer.
     *
     * @throws NoSuchAlgorithmException If no Provider supports a
     *                                  KeyPairGeneratorSpi implementation for
     *                                  the default specified algorithm.
     */
    private Server(int port) throws NoSuchAlgorithmException {
        this(port, new KeyManagerShiftDH());
    }

// Methods

    /**
     * <p>
     * Gets the Singleton instance of the Server.
     * </p>
     *
     * @param port The port number to open a Server on, as an integer.
     *
     * @return The Singleton instance of the Server.
     *
     * @throws NoSuchAlgorithmException If no Provider supports a
     *                                  KeyPairGeneratorSpi implementation for
     *                                  the default specified algorithm.
     */
    public static Server getInstance(int port) throws NoSuchAlgorithmException {
        // First check
        if (Server.instance == null) {
            // Synchronize on the class
            synchronized (Server.class) {
                // Double check (WITH synchronization)
                if (Server.instance == null) {
                    // which is thread safe because of the synchronized block
                    // that is used to get the instance
                    Server.instance = new Server(port);
                }
            }
        }
        return Server.instance;
    }

    /**
     * <p>
     * Opens a Socket on this Server's port and spawns two ServerClientHandlers
     * to accept connections from Clients.
     * </p>
     */
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.getPort())) {
            System.out.println(
                    "Server started and waiting for client connection...");

            // BlockingQueues for PublicKey exchange between Threads
            BlockingQueue<PublicKey> publicKeyChannelAToB =
                    new LinkedBlockingQueue<>();
            BlockingQueue<PublicKey> publicKeyChannelBToA =
                    new LinkedBlockingQueue<>();

            // Spawn handlers
            ServerClientHandler serverClientHandler0 =
                    new ServerClientHandler("ServerClientHandler-0",
                                            serverSocket.accept(),
                                            this.getKeyManager().clone(),
                                            publicKeyChannelBToA,
                                            publicKeyChannelAToB, this);
            ServerClientHandler serverClientHandler1 =
                    new ServerClientHandler("ServerClientHandler-1",
                                            serverSocket.accept(),
                                            this.getKeyManager().clone(),
                                            publicKeyChannelAToB,
                                            publicKeyChannelBToA, this);

            this.getHandlers()
                .put(serverClientHandler0.getName(), serverClientHandler0);
            this.getHandlers()
                .put(serverClientHandler1.getName(), serverClientHandler1);

            // Start the handler threads
            for (ServerClientHandler handler : this.getHandlers().values()) {
                handler.start();
            }

            // Wait for them to finish
            for (ServerClientHandler handler : this.getHandlers().values()) {
                handler.join();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <p>
     * Relays a Message to the ServerClientHandlers that did not send it.
     * </p>
     *
     * @param message The Message to relay.
     * @param sender  The name of the ServerClientHandler that send the
     *                Message.
     *
     * @throws IOException              Any exception thrown by the underlying
     *                                  OutputStream.
     * @throws NoSuchAlgorithmException If no Provider supports an
     *                                  implementation for one or more of the
     *                                  underlying specified algorithms.
     * @throws InvalidKeyException      If the PublicKey previously given to a
     *                                  receiving ServerClientHandler is
     *                                  inappropriate for the underlying
     *                                  KeyAgreement.
     */
    public void send(Message message, String sender)
    throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(
                Thread.currentThread().getName() + " got from " + sender +
                ":\t" + message);
        for (String recipient : this.getHandlers().keySet()) {
            if (!recipient.equals(sender)) {
                this.getHandlers().get(recipient).send(message);
            }
        }
    }
}
