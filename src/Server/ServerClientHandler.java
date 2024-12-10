package Server;

import Model.Crypto.EncryptionService;
import Model.Crypto.KeyManager;
import Model.Crypto.ShiftCipher;
import Model.Message;
import Model.Utility.TabInserter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * ServerClientHandler threads are spawned by the Server to independently handle
 * dealing with a single Client.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class ServerClientHandler extends Thread {
// Attributes

    /**
     * <p>
     * The EncryptionService used to perform encryption and decryption.
     * </p>
     */
    private final EncryptionService encryptionService;

    /**
     * <p>
     * The ObjectInputStream, used to receive Objects sent over the network.
     * </p>
     */
    private final ObjectInputStream in;

    /**
     * <p>
     * The ObjectOutputStream, used to send Objects over the network.
     * </p>
     */
    private final ObjectOutputStream out;

    /**
     * <p>
     * The BlockingQueue this ServerClientHandler uses to receive PublicKeys
     * sent by other ServerClientHandlers.
     * </p>
     */
    private final BlockingQueue<PublicKey> receiveQueue;

    /**
     * <p>
     * The BlockingQueue this ServerClientHandler uses to send PublicKeys to
     * other ServerClientHandlers.
     * </p>
     */
    private final BlockingQueue<PublicKey> sendQueue;

    /**
     * <p>
     * The Server that spawned this ServerClientHandler.
     * </p>
     */
    private final Server server;

// Getters and Setters

    /**
     * <p>
     * Gets this ServerClientHandler's EncryptionService used to perform
     * encryption and decryption.
     * </p>
     *
     * @return The EncryptionService used to perform encryption and decryption.
     */
    public EncryptionService getEncryptionService() {
        return this.encryptionService;
    }

    /**
     * <p>
     * Gets this ServerClientHandler's ObjectInputStream, used to receive
     * Objects sent over the network.
     * </p>
     *
     * @return The ObjectInputStream, used to receive Objects sent over the
     * network.
     */
    public ObjectInputStream getIn() {
        return this.in;
    }

    /**
     * <p>
     * Gets this ServerClientHandler's ObjectOutputStream, used to send Objects
     * over the network.
     * </p>
     *
     * @return The ObjectOutputStream, used to send Objects over the network.
     */
    public ObjectOutputStream getOut() {
        return this.out;
    }

    /**
     * <p>
     * Gets the BlockingQueue this ServerClientHandler uses to receive
     * PublicKeys sent by other ServerClientHandlers.
     * </p>
     *
     * @return The BlockingQueue this ServerClientHandler uses to receive
     * PublicKeys sent by other ServerClientHandlers.
     */
    public BlockingQueue<PublicKey> getReceiveQueue() {
        return this.receiveQueue;
    }

    /**
     * <p>
     * Gets the BlockingQueue this ServerClientHandler uses to send PublicKeys
     * to other ServerClientHandlers.
     * </p>
     *
     * @return The BlockingQueue this ServerClientHandler uses to send
     * PublicKeys to other ServerClientHandlers.
     */
    public BlockingQueue<PublicKey> getSendQueue() {
        return this.sendQueue;
    }

    /**
     * <p>
     * Gets the Server that spawned this ServerClientHandler.
     * </p>
     *
     * @return The Server that spawned this ServerClientHandler.
     */
    public Server getServer() {
        return this.server;
    }

// Constructors

    /**
     * <p>
     * Constructs a new ServerClientHandler.
     * </p>
     *
     * @param name         The name of the new ServerClientHandler Thread, as a
     *                     String.
     * @param clientSocket The Socket connected to this ServerClientHandler's
     *                     Client.
     * @param keyManager   The Server's KeyManager.
     * @param receiveQueue The BlockingQueue this ServerClientHandler uses to
     *                     receive PublicKeys sent by other
     *                     ServerClientHandlers.
     * @param sendQueue    The BlockingQueue this ServerClientHandler uses to
     *                     send PublicKeys to other ServerClientHandlers.
     * @param server       The Server that spawned this ServerClientHandler.
     */
    public ServerClientHandler(String name, Socket clientSocket,
                               KeyManager keyManager,
                               BlockingQueue<PublicKey> receiveQueue,
                               BlockingQueue<PublicKey> sendQueue,
                               Server server) throws IOException {
        super(name);
        this.receiveQueue = receiveQueue;
        this.sendQueue = sendQueue;
        this.server = server;
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.encryptionService =
                new EncryptionService(keyManager, new ShiftCipher(),
                                      this.getIn(), this.getOut());
        System.out.println(
                Thread.currentThread().getName() + " connected to client:\t" +
                clientSocket.getInetAddress());
    }

// Methods

    /**
     * <p>
     * Establishes a shared secret between the Server and this
     * ServerClientHandler's Client, facilitates the exchange of PublicKeys
     * between Clients, and facilitates the exchange of Messages.
     * </p>
     */
    @Override
    public void run() {
        try {
            // Establish a shared secret with our Client...
            this.getEncryptionService().establishSecretServer();

            // Exchange PublicKeys with the other Client...
            this.getSendQueue().put(this.getEncryptionService().getClientKey());
            PublicKey otherClientPublicKey = this.getReceiveQueue().take();
            this.getOut().writeObject(otherClientPublicKey);
            System.out.println(
                    Thread.currentThread().getName() + " received key:\n" +
                    TabInserter.insertTabs(otherClientPublicKey.toString()));

            while (true) {
                // Read an Object...
                Object readObject = this.getIn().readObject();

                if (readObject instanceof Message message) {
                    // If it's a Message, forward it to the other Client.
                    System.out.println(
                            Thread.currentThread().getName() + " got:\t" +
                            message);

                    Message decrypted =
                            this.getEncryptionService().decryptServer(message);
                    System.out.println(Thread.currentThread().getName() +
                                       " got (decrypted):\t" + decrypted);

                    this.getServer()
                        .send(decrypted, Thread.currentThread().getName());

                    Message encrypted = this.getEncryptionService()
                                            .encryptServer(decrypted);
                    System.out.println(Thread.currentThread().getName() +
                                       " sent (self, encrypted):\t" +
                                       encrypted);
                    this.getOut().writeObject(encrypted);
                    this.getOut().flush();
                } else if (readObject instanceof PublicKey key) {
                    // If it's a PublicKey, echo it back.
                    this.getOut().writeObject(key);
                    this.getOut().flush();
                } else {
                    /*
                     * TODO
                     *  Additional elseif to check for instance of some sort
                     *  of "Control message" class uniquely used for commands to
                     *  the Server/Client? IE, "Disconnect".
                     */
                    // Else, Oh No.
                    System.out.println(Thread.currentThread().getName() +
                                       " received an unknown object type.");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <p>
     * Used to send a Message to this ServerClientHandler from another context.
     * </p>
     *
     * @param message The Message being sent.
     *
     * @throws IOException              Any exception thrown by the underlying
     *                                  OutputStream.
     * @throws NoSuchAlgorithmException If no Provider supports an
     *                                  implementation for one or more of the
     *                                  underlying specified algorithms.
     * @throws InvalidKeyException      If the PublicKey previously given to
     *                                  this ServerClientHandler is
     *                                  inappropriate for the underlying
     *                                  KeyAgreement.
     */
    public void send(Message message)
    throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(Thread.currentThread().getName() +
                           " got from another handler:\t" + message);

        Message encryptedMessage =
                this.getEncryptionService().encryptServer(message);
        System.out.println(Thread.currentThread().getName() +
                           " sent from another handler, encrypted:\t" +
                           encryptedMessage);

        this.getOut().writeObject(encryptedMessage);
        this.getOut().flush();
    }

}
