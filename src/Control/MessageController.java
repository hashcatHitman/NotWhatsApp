package Control;

import Model.ConnectToClient;

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
            String message = TextChannel.getMessageField();
            try {
                ConnectToClient.sendMessage(message);
                TextChannel.getMessage().sendMessage(
                        TextChannel.getMessageField()); // Get the users
                TextChannel.messageField.setText("");
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}

