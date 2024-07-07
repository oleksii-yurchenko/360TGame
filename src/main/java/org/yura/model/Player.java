package org.yura.model;

import org.yura.model.message.strategy.MessageStrategy;

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

    public void sendMessage(String msg) {
        transport.sendMessage(msg);
        sentMsgCount++;
    }

    public String receiveMessage() {
        String msg = transport.receiveMessage(this.name);

        String from = msg.split(":")[0];
        String to = msg.split(":")[1];
        String text = msg.split(":")[2];

        System.out.printf("%s <- %s: %s%n", to, from, text);
        receivedMsgCount++;

        return msg;
    }

    public void communicate(MessageStrategy strategy){
        strategy.start(this);
    }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public String getName() { return name; }
}
