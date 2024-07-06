package org.yura.model;

/**
 * The {@code MessageService} interface defines methods for sending and receiving messages,
 * as well as managing players in a messaging system.
 */
public interface MessageService {

    void sendMessage(String msg);

    String receiveMessage();

    void addPlayer(Player player);
}
