package Control.Commands;

import Model.Message;
import Model.Observer.MessageNotification;

public class SendMessageCommand implements Command{
    private Message message;
    private MessageNotification messageNotification;

    /*
    Concrete Command to send a message
     */
    public SendMessageCommand(Message message, MessageNotification messageNotification) {
        this.message = message;
        this.messageNotification = messageNotification;
    }

    @Override
    public void execute() {
        try {
            messageNotification.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
