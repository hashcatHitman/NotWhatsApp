package Control.Commands;

import Server.Server;
import Server.GetIP;

import javax.swing.JOptionPane;
import java.security.NoSuchAlgorithmException; //to handle server errors

/*
@author: Ryan F
//doc string of class
This class is a command that starts server
 */
public class StartServerCommand implements Command {
    private int port;

    public StartServerCommand(int port) {
        this.port = port; //set port
    }

    @Override
    public void execute() {
        try {
            String localIP = GetIP.getLocalHostIP(); //get local ip
            System.out.println("Starting server on IP: " + localIP + ", Port: " + port);

            Server server = new Server(port); //create server
            Thread serverThread = new Thread(server, "Server");
            serverThread.start();

            // Show server info to the user
            JOptionPane.showMessageDialog(null,
                                          "Server started.\nIP: " + localIP + "\nPort: " + port);

        } catch (NoSuchAlgorithmException e) { //to handle server erros
            e.printStackTrace();
        }
    }
}
