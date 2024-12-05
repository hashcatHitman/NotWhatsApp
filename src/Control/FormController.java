package Control;

import Model.ConnectToClient;
import Model.ConnectToServer;
import Model.Singleton.Singleton;
import View.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
            new Thread(() -> {
                try {

                    if (ServerView.getServerPort() != null &&
                        ServerView.getServerIP().isEmpty()) {
                        ConnectToServer connectToServer = Singleton.getInstance(
                                Integer.parseInt(ServerView.getServerPort()))
                                                                   .getConnectToServer();
                        connectToServer.connectToServer(
                                connectToServer.getPort());
                    } else if (ServerView.getServerPort() != null &&
                               ServerView.getServerIP() != null) {
                        ConnectToClient connectToClient =
                                new ConnectToClient(ServerView.getServerIP(),
                                                    Integer.parseInt(ServerView.getServerPort()));
                        connectToClient.run();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

            // https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-user-input-with-a-Swing-JOptionPane-example
            SwingUtilities.invokeLater(() -> {
                String username =
                        JOptionPane.showInputDialog("What is your name?");
                if (username != null && !username.isEmpty()) {
                    TextChannel textChannel = new TextChannel(username);
                    textChannel.setVisible(true);
                }
            });
        }
    }
}
