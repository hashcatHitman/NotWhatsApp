package Control;

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
            try {
                Message message = new Message(textChannel.getMessageField(),
                                              textChannel.getUser().getUsername());
                textChannel.getMessage().sendMessage(message); // Get the users
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
            textChannel.setMessageField("");

        }
    }
}
