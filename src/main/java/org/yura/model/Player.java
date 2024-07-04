package org.yura.model;

import org.yura.model.message.strategy.MessageStrategy;

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
            throw new IllegalStateException("Transport has not been defined!");

        transport.sendMessage(msg);
        sentMsgCount++;
    }

    public String receiveMessage() {
        if (transport == null)
            throw new IllegalStateException("Transport has not been defined!");

        var msg = transport.receiveMessage();
        System.out.printf("%s <- %s%n", this.name, msg);
        receivedMsgCount++;
        return msg;
    }

    public void communicate(MessageStrategy strategy){
        strategy.start(this);
    }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public void setTransport(MessageService transport) {
        this.transport = transport;
    }
}
