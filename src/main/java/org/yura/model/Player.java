package org.yura.model;

import org.yura.model.message.strategy.MessageStrategy;
import org.yura.utils.Message;

import java.io.IOException;

/**
 * The {@code Player} class represents a player in the messaging system.
 * A player can send and receive messages using a {@code MessageTransfer} and can execute a messaging strategy.
 */
public class Player {
    private final String name;
    private final MessageTransfer transport;
    private int sentMsgCount;
    private int receivedMsgCount;

    public Player(String name, MessageTransfer transport) {
        this.name = name;
        this.transport = transport;
        transport.addPlayer(name);
    }

    public void sendMessage(Message msg) {
        try {
            transport.sendMessage(msg);
            sentMsgCount++;
        } catch (InterruptedException | IllegalArgumentException err){
            err.printStackTrace();
        }
    }

    public Message receiveMessage() {
        try {
            Message msg = transport.receiveMessage(this.name);
            System.out.printf("%s <- %s: %s%n", msg.getTo(), msg.getFrom(), msg.getText());
            receivedMsgCount++;

            return msg;
        } catch (InterruptedException | IOException | IllegalArgumentException err){
            err.printStackTrace();
            return null;
        }
    }

    public void communicate(MessageStrategy strategy){
        strategy.start(this);
    }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public String getName() { return name; }
}
