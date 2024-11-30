package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// I just created this class for now to test the GUI...
public class FormController implements ActionListener {
    private final View view;
    public FormController(View view) {this.view = view;}
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.connectButton) {
            // Open the chat window
            view.textChannel().setVisible(true);
        }
    }
}
