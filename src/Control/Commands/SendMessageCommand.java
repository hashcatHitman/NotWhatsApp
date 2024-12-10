package Control.Commands;

import Client.NetworkRelay;
import Model.Message;
import Model.Observer.MessageNotification;

public class SendMessageCommand implements Command {
    private final NetworkRelay networkRelay;

    private Message message;

    private MessageNotification messageNotification;

    /*
    Concrete Command to send a message
     */
    public SendMessageCommand(Message message, NetworkRelay networkRelay) {
        this.message = message;
        this.networkRelay = networkRelay;
    }

    @Override
    public void execute() {
        try {
            networkRelay.sendCurrentMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}