package Control;

import Client.NetworkRelay;
import Control.Commands.SendMessageCommand;
import Model.Message;
import View.TextChannel;

import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageController implements ActionListener {

    TextChannel textChannel;

    public MessageController(TextChannel textChannel) {
        this.textChannel = textChannel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textChannel.sendButton) {
            /*
                Test the observer pattern
             */
            Message message = new Message(textChannel.getMessageField(),
                                          textChannel.getUser().getUsername());
            //Create command to send message
            SendMessageCommand sendMessageCommand =
                    new SendMessageCommand(message,
                                           textChannel.getNetworkRelay());
            sendMessageCommand.execute();

            textChannel.setMessageField("");

        }
    }
}
