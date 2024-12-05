package Model.Singleton;

import Model.ConnectToServer;

import java.io.IOException;

public class Singleton {
    private static Singleton instance = null;

    private ConnectToServer connectToServer;

    private Singleton(int port) throws IOException {
        this.connectToServer = new ConnectToServer(port);
    }

    public static Singleton getInstance(int port) throws IOException {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(port);
                }
            }
        }
        return instance;
    }

    public ConnectToServer getConnectToServer() {
        return connectToServer;
    }
}
