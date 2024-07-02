package org.yura;

public interface MessageService {
    void addPlayer(Player player);
    void sendMessage(String from, String to, String msg);
    String receiveMessage(String from, String to);
}
