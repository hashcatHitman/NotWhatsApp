package View;

import Control.Commands.Command;
import Control.Commands.ConnectCommand;
import Control.Commands.StartServerCommand;

import javax.swing.*;
import java.awt.*;

/**
 * StartView class
 * This class is the view for the start screen
 * It allows the user to select if they want to start as a server or client
 * If they select server, they will be prompted to enter a port number
 * If they select client, they will be prompted to enter the server IP and port number
 * The view will then execute the appropriate command
 * The view will close after the command is executed
 *
 * @author Ryan F
 */

public class StartView extends JFrame {
    private JButton serverButton;
    private JButton clientButton;

    public StartView() {
        super("Select Mode");

        serverButton = new JButton("Start as Server");
        clientButton = new JButton("Start as Client");

        setLayout(new FlowLayout());
        add(serverButton);
        add(clientButton);

        serverButton.addActionListener(e -> {
            String portStr =
                    JOptionPane.showInputDialog("Enter server port:"); // Show input dialog
            if (portStr != null && !portStr.isEmpty()) { // Check if user pressed cancel and if they entered a value, and if the value is not empty
                try {
                    int port = Integer.parseInt(portStr); // Parse port number
                    Command startServerCmd = new StartServerCommand(port); // Create command
                    startServerCmd.execute(); // Execute command
                    dispose(); // Close this view
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid port number!");
                }
            }
        });

        clientButton.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog("Enter server IP:"); // Show input dialog
            String portStr = JOptionPane.showInputDialog("Enter server port:");
            if (ip != null && portStr != null && !ip.isEmpty() && !portStr.isEmpty()) { // check if they entered a value, and if the value is not emp
                try {
                    int port = Integer.parseInt(portStr);
                    //Creates connect command
                    Command connectCmd = new ConnectCommand(ip, port);
                    connectCmd.execute();
                    dispose(); // Close this view
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid port " +
                                                        "number!"); // Show error message
                }
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setVisible(true);
    }
}