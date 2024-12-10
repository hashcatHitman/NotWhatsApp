package Control.Commands;

import Client.NetworkRelay;
import Model.Message;
import Model.Observer.MessageNotification;
/*
 * @author: Ryan Fortune
 * The SendMessageCommand class implements the Command interface
 * and represents a concrete command for sending a message.

 * When executed, this command uses a NetworkRelay to send the given
 * Message object to the server, ensuring that it will be relayed
 * to other clients. This approach decouples the message sending
 * logic from the UI layer, adhering to the Command design pattern.
 * This class is used by the MessageController class to send messages.
 */
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
            networkRelay.sendCurrentMessage(message); //calls the network
            // relay to send the message through the server
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}