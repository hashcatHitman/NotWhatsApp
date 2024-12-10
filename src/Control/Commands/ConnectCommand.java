package Control.Commands;

import Client.Client;
import View.TextChannel;

import javax.swing.JOptionPane; // Import JOptionPane to show input dialog

/*
    * Author: Ryan Fortune
    * ConnectCommand class
    * This class is a command to connect to a server
    * It prompts the user for a username and then creates a new client
    * It then creates a new thread for the client and starts it
    * It then opens a chat window
    * The command is executed when the user selects to connect as a client
    * The command is executed by the StartView class
    *
 */

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
        if (username.isEmpty()) { //check if the user entered a username
            JOptionPane.showMessageDialog(null, "No username provided.");
            return;
        }

        Client client = new Client(host, port, username); // Create a new client
        //NetworkRelay
        Thread clientThread = new Thread(client, "Client-" + username); // Create a new thread for the client
        clientThread.start(); // Start the client thread

        // Open the chat window - originally used before connecting everything
        TextChannel textChannel = new TextChannel(username);
        textChannel.setVisible(true);
    }
}