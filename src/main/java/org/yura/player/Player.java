package org.yura.player;

public class Player {
    private String name;
    private MessageService transport;
    private int sentMsgCount;
    private int receivedMsgCount;

    public Player(String name) {
        this.name = name;
    }

    public void sendMessage(String msg) {
        checkTransport();
        transport.sendMessage(msg);
        sentMsgCount++;
    }

    public String receiveMessage() {
        checkTransport();
        var msg = transport.receiveMessage();
        System.out.printf("%s <- %s%n", this.name, msg);
        receivedMsgCount++;
        return msg;
    }

    public void checkTransport(){
        if (transport == null)
            throw new IllegalStateException("Transport has not been defined!");
    }

    public String getName() { return name; }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public void setTransport(MessageService transport) {
        this.transport = transport;
    }
}
