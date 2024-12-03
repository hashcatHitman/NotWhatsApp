package Control;

import Model.Observer.Message;
import Model.Observer.User;
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
            User newUser = new User("janedoe", this.textChannel);
            Message message = new Message();
            message.addClient(newUser);

            try {
                message.sendMessage(
                        textChannel.getMessageField()); // Get the users
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
