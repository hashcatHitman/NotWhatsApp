package Control.Commands;

public class SendMessageCommand implements Command{
    private String messageText;

    /*
    Concrete Command to send a message
     */
    public SendMessageCommand(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public void execute(){
        //TODO: Implement logic to send message
        System.out.println("Sending message: " + messageText);
    }

}
