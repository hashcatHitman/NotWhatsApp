package Control;

import View.*;

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
            TextChannel textChannel = new TextChannel();
            textChannel.setVisible(true);
        }
    }
}
