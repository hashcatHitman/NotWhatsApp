package Client;

import Model.Crypto.Cipher;
import Model.Crypto.EncryptionService;
import Model.Crypto.KeyManager;
import Model.Crypto.KeyManagerShiftDH;
import Model.Crypto.ShiftCipher;
import Model.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * <p>
 * A Client task that can be run to connect to a Server and exchange encrypted
 * Messages.
 * </p>
 *
 * @author Sam K
 * @version 12/7/2024
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
     * ServerClientHandler. Does this with another Client as well. Exchanges
     * Messages with the Server.
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

            /*
             * TODO
             *  Change how input is received, output is displayed, and how
             *  you exit the loop/program to be linked to the GUI!
             */
            // Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Prompt user for input
                if (Thread.currentThread().getName().endsWith("A")) {
                    System.out.print("Enter message (type 'exit' to quit):\t");
                    String userInput = scanner.nextLine();

                    // If the user types 'exit', break the loop and close
                    // the connection.
                    if (userInput.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    }

                    // Create a new Message object with the user input
                    Message message =
                            new Message(userInput, this.getUsername());
                    System.out.println(Thread.currentThread().getName() +
                                       " wants to send:\t" + message);

                    // Encrypt it
                    Message encrypted = encryptionService.encrypt(message);
                    System.out.println(
                            Thread.currentThread().getName() + " is sending " +
                            "the message, but encrypted. It looks like:\t" +
                            encrypted);

                    // Send it
                    out.writeObject(encrypted);
                    out.flush();
                }

                // Wait for the server's response
                System.out.println(Thread.currentThread().getName() +
                                   " is waiting for an Object....");
                Object object = in.readObject();
                System.out.println(Thread.currentThread().getName() +
                                   " received an Object!");

                // If it's a Message, decrypt it and display
                if (object instanceof Message response) {
                    System.out.println(Thread.currentThread().getName() +
                                       " received an encrypted response from " +
                                       "the server:\t" + response);
                    Message decrypted = encryptionService.decrypt(response);
                    System.out.println(Thread.currentThread().getName() +
                                       " decrypted the server response and " +
                                       "got:\t" + decrypted);
                }
            }

            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
