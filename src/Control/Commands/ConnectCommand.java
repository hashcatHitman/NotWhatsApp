package Control.Commands;

import Client.Client;
import View.TextChannel;

import javax.swing.JOptionPane; // Import JOptionPane to show input dialog

public class ConnectCommand implements Command {
    private String host;
    private int port;

    public ConnectCommand(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void execute() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        if (username == null || username.trim().isEmpty()) { //check if
            // username is null or just a space (trim gets rid of it)
            JOptionPane.showMessageDialog(null, "No username provided.");
            return;
        }

        Client client = new Client(host, port, username); // Create a new client
        Thread clientThread = new Thread(client, "Client-" + username); // Create a new thread for the client
        clientThread.start(); // Start the client thread

        // Open the chat window
        TextChannel textChannel = new TextChannel(username);
        textChannel.setVisible(true);
    }
}