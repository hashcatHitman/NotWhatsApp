package Model.Observer;
/*
 * Paige Grimes User.java The Observer
 */

import View.TextChannel;

import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.util.Random;

public class User implements nwaClient {

    private String username;

    TextChannel textChannel;

    Color color;

    public User(String username, TextChannel textChannel) {
        this.username = username;
        this.color = setColor();
        this.textChannel = textChannel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color setColor() {
        // Logic to get a random color
        Random rand = new Random();

        float r = (rand.nextFloat());
        float g = (rand.nextFloat());
        float b = (rand.nextFloat());

        return new Color(r, g, b);
    }
    public Color getColor() {
        return this.color;
    }

    @Override
    public void receiveMessage(String username, String message, Color color)
    throws BadLocationException {
        textChannel.addMessage(message, color);
    }

}

