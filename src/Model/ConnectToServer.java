package Model;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.text.BadLocationException;

public class ConnectToServer {

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    int port;

    private CopyOnWriteArrayList<ConnectToClient> clients =
            new CopyOnWriteArrayList<>();

    ServerSocket serverSocket;

    public ConnectToServer(int port) throws IOException {
        this.port = port;
    }

    public void connectToServer(int port) throws IOException {
        serverSocket =
                new ServerSocket(port, 5, InetAddress.getByName("0.0.0.0"));
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Client Connected");
            ConnectToClient connectToClient = new ConnectToClient(client, this);
            clients.add(connectToClient);
            new Thread(connectToClient).start();
        }

    }

    public void broadcastMessage(String message) throws BadLocationException {
        for (ConnectToClient client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(ConnectToClient client) {
        clients.remove(client);
    }
}
/**
 * try { // Step 1: Create a server socket bound to a port ServerSocket
 * serverSocket = new ServerSocket(12345); System.out.println("Server is running
 * and waiting for a client to connect...");
 * <p>
 * while (true) { // Keep the server running // Step 2: Accept an incoming
 * client connection Socket clientSocket = serverSocket.accept();
 * System.out.println("Client connected!");
 * <p>
 * // Step 3: Set up input and output streams BufferedReader in = new
 * BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 * PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 * <p>
 * // Step 4: Communicate with the client String clientMessage; while
 * ((clientMessage = in.readLine()) != null) { System.out.println("Client says:
 * " + clientMessage);
 * <p>
 * // Step 5: Respond to the client if (clientMessage.endsWith(("close"))) {
 * out.println("exit"); clientSocket.close(); System.out.println("Client
 * connection closed."); break; // Exit loop for this client } else {
 * out.println("Hello, client! Your message was received: " + clientMessage); }
 * }
 * <p>
 * // Step 6: Close the client connection //clientSocket.close();
 * //System.out.println("Client connection closed."); } } catch (IOException e)
 * { e.printStackTrace(); } }
 */
