package Control;

import View.*;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// I just created this class for now to test the GUI...
public class FormController implements ActionListener {
    ServerView view;

    public FormController(ServerView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.connectButton) {
            // Open the chat window
            view.setVisible(false);

            // Creates a popup window that asks the user for their desired
            // username then saves that username to the String variable
            // username.
            String username = JOptionPane.showInputDialog("What would you " +
                                                          "like your " +
                                                          "username to be?");
            // This is just a simple setup to create the chat window
            if (!username.isEmpty()) {
                TextChannel textChannel = new TextChannel(username);
                textChannel.setVisible(true);
            } else {

                JOptionPane.showMessageDialog(view, "Error: You did not enter" +
                                                    " a username.");
                view.setVisible(true); //redisplay the server view
            }
        }
    }
}
