package View;

import javax.swing.*;
import java.awt.Dimension;

public class View extends JFrame {
    JLabel IPAddress, portAddress;
    JTextField serverIP;
    JTextField serverPort;
    JButton connectButton;

    public View() {
        super("¬WhatsApp");
        FormController formController = new FormController(this);
        // Window features
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(250, 200));
        setResizable(true);

        add(connectServerView(), SwingConstants.CENTER);

        // Display the window
        pack();
        setVisible(true);

        connectButton.addActionListener(formController);
    }
    public JPanel connectServerView() {
        JPanel serverPanel = new JPanel();

        // https://docs.oracle.com/javase/tutorial/uiswing/layout/group.html
        GroupLayout layout = new GroupLayout(serverPanel);

        IPAddress = new JLabel("IP Address: ");
        serverIP = new JTextField();
        portAddress = new JLabel("Server Port: ");
        serverIP.setColumns(10);
        serverPort = new JTextField();
        serverPort.setColumns(10);
        connectButton = new JButton("Connect");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                      .addComponent(IPAddress)
                                      .addComponent(serverIP))
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                      .addComponent(portAddress)
                                      .addComponent(serverPort))
                      .addComponent(connectButton)
                                 );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                      .addComponent(IPAddress)
                                      .addComponent(portAddress))
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                      .addComponent(serverIP)
                                      .addComponent(serverPort))
                      .addComponent(connectButton)
                               );


        return serverPanel;

    }
    public JFrame textChannel() {
        // Create a new JFrame
        JFrame channelFrame = new JFrame();
        channelFrame.setTitle("¬WhatsApp");

        JPanel chatPanel = new JPanel();

        GroupLayout layout = new GroupLayout(chatPanel);
        chatPanel.setLayout(layout);

        // Components
        JLabel numUsers = new JLabel("Users: 1");
        JTextArea textArea = new JTextArea(15, 30); // Specify rows and columns for initial size
        textArea.setEditable(false); // User cannot enter text into Text area
        JScrollPane scrollPane = new JScrollPane(textArea); // Add JScrollPane for scrolling

        JTextField textField = new JTextField();
        JButton sendButton = new JButton("Send");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Define horizontal layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(numUsers)
                      .addComponent(scrollPane)
                      .addGroup(layout.createSequentialGroup()
                                      .addComponent(textField)
                                      .addComponent(sendButton))
                                 );

        // Define vertical layout
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                      .addComponent(numUsers)
                      .addComponent(scrollPane)
                      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                      .addComponent(textField)
                                      .addComponent(sendButton))
                               );

        this.setVisible(false); // Close the other window
        // Finalize frame
        channelFrame.add(chatPanel);
        channelFrame.pack();
        // TODO: We'll have to change this if we want to go to the main frame
        //  (to enter ip and port again) on exit.
        channelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return channelFrame;
    }
}
