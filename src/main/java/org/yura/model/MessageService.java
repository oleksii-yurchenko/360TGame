package org.yura.model;

/**
 * The {@code Messenger} interface defines methods for sending and receiving messages
 */
public interface MessageService {

    void sendMessage(String msg);

    String receiveMessage(String to);

    void addPlayer(String playerName);
}
