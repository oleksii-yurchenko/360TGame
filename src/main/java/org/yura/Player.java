package org.yura;

public class Player {
    private String name;
    private MessageService transport;
    private int sentMsgCount;
    private int receivedMsgCount;
    private String partner;

    public Player(String name) {
        this.name = name;
    }

    public void sendMessage(String msg) {
        if (transport == null)
            throw new IllegalStateException("Transport has not been defined!");

        transport.sendMessage(this.name, partner, msg);

        sentMsgCount++;
    }

    public String receiveMessage() {
        var msg = transport.receiveMessage(partner, this.name);

        System.out.printf("%s -> %s: %s%n", partner, this.name, msg);

        receivedMsgCount++;

        return msg;
    }

    static public Player getPlayer(String name, String partner, MessageService service){
        Player player = new Player(name);
        player.setPartner(partner);
        service.addPlayer(player);

        return player;
    }

    public String getName() { return name; }

    public int getSentMsgCount() { return sentMsgCount; }

    public int getReceivedMsgCount() { return receivedMsgCount; }

    public void setTransport(MessageService transport) {
        this.transport = transport;
    }

    public void setPartner(String partner){
        this.partner = partner;
    }
}
