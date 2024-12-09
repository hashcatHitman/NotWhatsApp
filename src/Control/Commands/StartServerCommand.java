package Control.Commands;

import Model.Crypto.KeyManagerShiftDH;
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

            Server server = Server.getInstance(port, new KeyManagerShiftDH()); //create
            // check above for possible error with KeyManagerShiftDH, as was
            // not needed before making constructor private for Server
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
