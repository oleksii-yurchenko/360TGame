package org.yura.model;

import org.yura.model.message.strategy.MessageStrategy;

/**
 * The {@code Player} class represents a player in the messaging system.
 * A player can send and receive messages using a {@code MessageTransfer} and can execute a messaging strategy.
 */
public class Player {
    private final String name;
    private MessageTransfer transport;
    private int sentMsgCount;
    private int receivedMsgCount;

    public Player(String name){
        this.name = name;
    }

    public void sendMessage(Message msg) {
        if (transport == null) {
            throw new IllegalStateException("Transport has not been set!");
        }

        transport.sendMessage(msg);
        sentMsgCount++;
    }

    public Message receiveMessage() {
        if (transport == null) {
            throw new IllegalStateException("Transport has not been set!");
        }

        Message msg = transport.receiveMessage(name);
        System.out.printf("%s <- %s: %s%n", msg.getTo(), msg.getFrom(), msg.getContent());
        receivedMsgCount++;

        return msg;
    }

    public void communicate(MessageStrategy strategy){
        strategy.start(this);
    }

    public void setTransport(MessageTransfer transport) {
        this.transport = transport;
        transport.addPlayer(name);
    }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public String getName() { return name; }
}
