package Control;

import Control.Commands.SendMessageCommand;
import Model.Message;
import View.TextChannel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * <p>
 *     This class implements an ActionListen which controls what happens
 *     whenever the sendButton is pressed.
 * </p>
 * @authors Paige & Ryan F
 */
public class MessageController implements ActionListener {

    public TextChannel textChannel;

    public MessageController(TextChannel textChannel) {
        this.textChannel = textChannel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textChannel.sendButton) {
            // Create a new message object
            Message message = new Message(textChannel.getMessageField(),
                                          textChannel.getUser().getUsername());
            // Create command to send the message
            SendMessageCommand sendMessageCommand =
                    new SendMessageCommand(message,
                                           textChannel.getNetworkRelay());
            // Execute the command
            sendMessageCommand.execute();

            // Clear the input field messageField
            textChannel.setMessageField("");
        }
    }
}
