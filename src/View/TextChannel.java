package View;

import Client.*;
import Control.Commands.PlaySoundCommand;
import Control.MessageController;
import Model.Message;
import Model.Observer.MessageNotification;
import Model.Observer.User;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Dimension;

/**
 * This class controls the GUI for the chat window.
 *
 * @author Paige G
 * @author Sam K
 * @author Ryan F
 */

public class TextChannel extends JFrame {
    // Declare the components of the GUI
    JTextPane messageArea;

    static JTextField messageField;

    public JButton sendButton;

    static MessageNotification messageNotification;

    private final User user;

    TextChannel textChannel;

    // Network relay field
    private NetworkRelay networkRelay;

    /**
     * The constructor for the chat GUI. Creates the JFrame, adds and styles the
     * components.
     *
     * @param username Pass in the username so the client will be subscribed to
     *                 messages
     */
    public TextChannel(String username) throws BadLocationException {
        super("Liminal");
        this.textChannel = this;
        /**
         * Create the user and message objects here so that the user is not
         * created on each button send.
         */
        this.user = new User(username, this);
        messageNotification = new MessageNotification();
        messageNotification.addClient(getUser());

        // Create the MessageController that hold the button action listeners.
        MessageController messageController = new MessageController(this);
        JPanel chatPanel = new JPanel();

        // Create a GroupLayout to organize the components.
        GroupLayout layout = new GroupLayout(chatPanel);
        chatPanel.setLayout(layout);

        messageArea = new JTextPane();

        // Add scroll pane for scrolling
        JScrollPane scrollPane = new JScrollPane(messageArea);
        messageArea.setEditable(false);
        messageArea.setPreferredSize(new Dimension(200, 200));
        messageArea.getDocument()
                   .insertString(messageArea.getDocument().getLength(),
                                 "Hello " + getUser().getUsername() + "!\n" +
                                 "Try starting a conversation by sending a " +
                                 "message.\n", null);

        messageField = new JTextField();
        messageField.requestFocus();
        sendButton = new JButton("Send");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Define horizontal layout - both horizontal and vertical are required.
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(scrollPane).addGroup(
                              layout.createSequentialGroup()
                                    .addComponent(messageField)
                                    .addComponent(sendButton)));

        // Define vertical layout
        layout.setVerticalGroup(
                layout.createSequentialGroup().addComponent(scrollPane)
                      .addGroup(layout.createParallelGroup(
                                              GroupLayout.Alignment.BASELINE)
                                      .addComponent(messageField)
                                      .addComponent(sendButton)));

        // Finalize frame
        add(chatPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add the ActionListener
        sendButton.addActionListener(messageController);

    }

    /**
     * Adds the users' message to the JTextPane with styles
     *
     * @param message The users' message
     * @param color   The color of the users username
     *
     * @throws BadLocationException Needed for handling style document
     *                              exceptions
     */
    public void addMessage(Message message, Color color)
    throws BadLocationException {
        Style usernameStyle =
                messageArea.getStyledDocument().addStyle("usernameStyle", null);
        // Add color to the username
        StyleConstants.setForeground(usernameStyle, color);
        Style messageStyle =
                messageArea.getStyledDocument().addStyle("messageStyle", null);

        // Add the username and message to the document (JTextPane)
        messageArea.getDocument()
                   .insertString(messageArea.getDocument().getLength(),
                                 message.getSender() + ": ", usernameStyle);
        messageArea.getDocument()
                   .insertString(messageArea.getDocument().getLength(),
                                 message + "\n", messageStyle);
    }

    /**
     * Getters and Setters
     */
    // Get the users' message as a String
    public static String getMessageField() {
        return messageField.getText();
    }

    // Used for clearing the messageField after the user sent a message
    public void setMessageField(String message) {
        messageField.setText(message);
    }

    // Returns the user for 'this' instance of the TextChannel
    public User getUser() {
        return user;
    }

    // Returns the message notification object to be called by other classes
    public static MessageNotification getMessage() {
        return messageNotification;
    }

    // Setter for networkRelay
    public void setNetworkRelay(NetworkRelay networkRelay) {
        this.networkRelay = networkRelay;
    }

    // Return the NetworkRelay
    public NetworkRelay getNetworkRelay() {
        return networkRelay;
    }
}
