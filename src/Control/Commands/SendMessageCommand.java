package Control.Commands;

import Client.NetworkRelay;
import Model.Message;

public class SendMessageCommand implements Command{
    private Message message;
    private NetworkRelay networkRelay;

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
