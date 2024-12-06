package View;

import Control.FormController;

import javax.swing.*;
import java.awt.Dimension;

/**
 * This class creates the GUI where the user will insert the required port and
 * IP addresses to connect to the server.
 *
 * @author Paige G
 * @author Sam K
 * @author Ryan F
 */
public class ServerView extends JFrame {
    // Declare the GUI components
    JLabel IPAddress, portAddress;

    JTextField serverIP, serverPort;

    public JButton connectButton;

    /**
     * Creates the View for the Server
     */
    public ServerView() {
        super("¬WhatsApp");
        FormController formController = new FormController(this);
        // Window features
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(250, 200));
        setResizable(true);

        // Add the JPanel that will contain the components.
        add(connectServerView(), SwingConstants.CENTER);

        // Display the window
        pack();
        setVisible(true);

        // Add an action listener for the buttons
        connectButton.addActionListener(formController);
    }

    public JPanel connectServerView() {
        JPanel serverPanel = new JPanel();

        // https://docs.oracle.com/javase/tutorial/uiswing/layout/group.html
        GroupLayout layout = new GroupLayout(serverPanel);

        // Initialize the fields
        IPAddress = new JLabel("IP Address: ");
        serverIP = new JTextField();
        portAddress = new JLabel("Server Port: ");
        serverIP.setColumns(10);
        serverPort = new JTextField();
        serverPort.setColumns(10);
        connectButton = new JButton("Connect");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        // Both Horizontal and Vertical groups are required.
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                      .addComponent(IPAddress).addComponent(serverIP)).addGroup(
                                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                      .addComponent(portAddress).addComponent(serverPort))
                                        .addComponent(connectButton));

        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
                                              layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(IPAddress).addComponent(portAddress))
                                      .addGroup(layout.createParallelGroup(
                                                              GroupLayout.Alignment.BASELINE)
                                                      .addComponent(serverIP)
                                                      .addComponent(serverPort))
                                      .addComponent(connectButton));

        return serverPanel;

    }

    public JTextField getServerIP() {
        return serverIP;
    }

    public JTextField getServerPort() {
        return serverPort;
    }

}
