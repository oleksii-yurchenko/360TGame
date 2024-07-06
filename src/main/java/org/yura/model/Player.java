package org.yura.model;

import org.yura.model.message.strategy.MessageStrategy;

/**
 * The {@code Player} class represents a player in the messaging system.
 * A player can send and receive messages using a {@code MessageService} and can execute a messaging strategy.
 */
public class Player {
    private final String name;
    private MessageService transport;
    private int sentMsgCount;
    private int receivedMsgCount;

    public Player(String name) {
        this.name = name;
    }

    public void sendMessage(String msg) {
        if (transport == null)
            throw new IllegalStateException("The transport service has not been configured!");

        transport.sendMessage(msg);
        sentMsgCount++;
    }

    public String receiveMessage() {
        if (transport == null)
            throw new IllegalStateException("The transport service has not been configured!");

        String msg = transport.receiveMessage();

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

    public void setTransport(MessageService transport) { this.transport = transport; }
}
