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

            String username = JOptionPane.showInputDialog("What is your name?");

            TextChannel textChannel = new TextChannel(username);
            textChannel.setVisible(true);
        }
    }
}
