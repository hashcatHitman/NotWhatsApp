package View;

import Control.MessageController;
import Model.Observer.MessageNotification;
import Model.Observer.User;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Dimension;

/**
 * TODO: Move to Readme with explanations
 * Current Resources:
 * https://stackoverflow.com/questions/31928306/how-to-create-and-use-a-jtextpane
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextPane.html
 * https://stackoverflow.com/questions/4059198/jtextpane-appending-a-new-string
 * https://stackoverflow.com/questions/50152438/jtextpane-wont-display-text-added-by-defaultstyleddocument
 * https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
 */

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

    JTextField messageField;

    public JButton sendButton;

    MessageNotification messageNotification;

    User user;

    /**
     * The constructor for the chat GUI. Creates the JFrame, adds and styles the
     * components.
     *
     * @param username Pass in the username so the client will be subscribed
     *                 to messages
     */
    public TextChannel(String username) {

        super("¬WhatsApp");

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

        // Display the number of users online
        JLabel numUsers =
                new JLabel("Users: " + messageNotification.getNumUsers());

        messageArea = new JTextPane();

        // Add scroll pane for scrolling
        JScrollPane scrollPane = new JScrollPane(messageArea);
        messageArea.setEditable(false);
        messageArea.setPreferredSize(new Dimension(200, 200));

        messageField = new JTextField();
        messageField.requestFocus();
        sendButton = new JButton("Send");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Define horizontal layout - both horizontal and vertical are required.
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(numUsers).addComponent(scrollPane).addGroup(
                              layout.createSequentialGroup()
                                    .addComponent(messageField)
                                    .addComponent(sendButton)));

        // Define vertical layout
        layout.setVerticalGroup(
                layout.createSequentialGroup().addComponent(numUsers)
                      .addComponent(scrollPane).addGroup(
                              layout.createParallelGroup(
                                            GroupLayout.Alignment.BASELINE)
                                    .addComponent(messageField)
                                    .addComponent(sendButton)));

        // Finalize frame
        add(chatPanel);
        pack();
        // TODO: We'll have to change this if we want to go to the main frame
        //  (to enter ip and port again) on exit.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sendButton.addActionListener(messageController);

    }

    /**
     * Adds the users' message to the JTextPane with styles
     *
     * @param message The users' message
     * @param color The color of the users username
     *
     * @throws BadLocationException Needed for handling style document
     * exceptions
     */
    public void addMessage(String message, Color color)
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
                                 user.getUsername() + ": ", usernameStyle);
        messageArea.getDocument()
                   .insertString(messageArea.getDocument().getLength(),
                                 message + "\n", messageStyle);

    }

    /**
     * Getters and Setters
     */
    public String getMessageField() {
        return messageField.getText();
    }

    public void setMessageField(String message) {
        messageField.setText(message);
    }

    public User getUser() {
        return user;
    }

    public MessageNotification getMessage() {
        return messageNotification;
    }

}