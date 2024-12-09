package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class GetIP {

    /**
     * Author: Ryan Fortune
     *
     * Attempts to discover this machine's local IP address by
     * connecting to an external host.
     *
     * @return The local host IP address as a String.
     * Resource used to get this logic:
     *
     * https://www.baeldung.com/java-get-ip-address
     */
    public static String getLocalHostIP() {
        try (Socket socket = new Socket()) {
            //we create a socket ti establish a TCP connection with google
            // Attempt a connection to google
            socket.connect(new InetSocketAddress("google.com", 80));
            //port  80  taken from the URL
            // If the connection is successful, return the ip address
            return socket.getLocalAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
            //Cannot get the ip address
            System.out.println("Cannot get the ip address");
            return "Cannot get the ip address";
        }
    }
}

