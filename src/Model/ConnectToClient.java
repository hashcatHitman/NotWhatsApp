package Model;

import View.TextChannel;

import javax.swing.text.BadLocationException;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Will create an endless loop - had fixed it but lost the course
 * Connect to the server with just the port and then the client with ip and
 * port
 */

public class ConnectToClient implements Runnable {
    private final Socket socket;

    private ConnectToServer server;

    private static PrintWriter out;

    public ConnectToClient(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);

    }

    public ConnectToClient(Socket socket, ConnectToServer server)
    throws IOException {
        this.socket = socket;
        this.server = server;
        this.out = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void run() {
        try {
            String clientMessage = "";
            while ((clientMessage) != null) {
                clientMessage = TextChannel.getMessageField();
                System.out.println("Client received: " + clientMessage);
                server.broadcastMessage(clientMessage);
            }
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        } finally {
            cleanup();
        }
    }

    public static void sendMessage(String message) throws BadLocationException {
        if (out != null) {
            out.println(message);
            TextChannel.getMessage().sendMessage(TextChannel.getMessageField());
        }
    }

    private void cleanup() {
        try {
            server.removeClient(this);
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
/**
 * try {
 * <p>
 * // Step 1: Connect to the server (replace "SERVER_IP" with the server's IP
 * address) Socket socket = new Socket("172.16.10.215", 12345);
 * System.out.println("Connected to the server!");
 * <p>
 * // Step 2: Set up input and output streams PrintWriter out = new
 * PrintWriter(socket.getOutputStream(), true); BufferedReader in = new
 * BufferedReader(new InputStreamReader(socket.getInputStream()));
 * <p>
 * while (true) { Scanner sc = new Scanner(System.in);
 * <p>
 * String you = socket.getInetAddress().toString(); String me =
 * socket.getLocalAddress().toString(); String message = sc.nextLine(); String
 * send = "SERVER IP:\t" + you + "\t" + "CLIENT IP:\t" + me + "\t" +
 * "MESSAGE:\t" + message; out.println(send); String serverResponse =
 * in.readLine(); System.out.println("Server says: " + serverResponse);
 * <p>
 * if (serverResponse.endsWith("exit")) { socket.close();
 * System.out.println("Client connection closed."); break; } }
 * <p>
 * /* // Step 3: Send a message to the server out.println("Hello, Ryan!");
 * <p>
 * // Step 4: Receive a response from the server String serverResponse =
 * in.readLine(); System.out.println("Server says: " + serverResponse);
 * <p>
 * <p>
 * // Step 5: Close the connection
 * <p>
 * }catch(
 * <p>
 * IOException e){ e.
 * <p>
 * printStackTrace(); } }
 */