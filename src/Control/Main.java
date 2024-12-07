package Control;

import Client.Client;
import Server.Server;
import View.ServerView;

import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * A class containing the main entrypoint of the program.
 * </p>
 *
 * @author Paige G
 * @author Sam K
 * @version 12/7/2024
 * @implNote Currently acts as a sort of scratch paper for testing development
 * code. This is liable to change!
 */
public class Main {
// Methods

    /**
     * <p>
     * The main entrypoint of the program.
     * </p>
     *
     * @param args The arguments passed to the program, as an array of Strings.
     */
    public static void main(String[] args)
    throws NoSuchAlgorithmException, InterruptedException {
        new ServerView();

        // Multithreaded test for a single machine
        // Create Server and Clients.
        final int port = 12345;
        final String host = "127.0.0.1";
        Server server = new Server(port);
        Client clientA = new Client(host, port, "Alice");
        Client clientB = new Client(host, port, "Bob");

        // Spawn Threads for each
        Thread serverThread = new Thread(server, "Server");
        Thread clientAThread = new Thread(clientA, "Client A");
        Thread clientBThread = new Thread(clientB, "Client B");

        // Start the server and give it a second to prepare
        serverThread.start();
        Thread.sleep(1000);

        // Start the clients
        clientAThread.start();
        clientBThread.start();

        // Wait for them to finish
        serverThread.join();
        clientAThread.join();
        clientBThread.join();
    }
}
