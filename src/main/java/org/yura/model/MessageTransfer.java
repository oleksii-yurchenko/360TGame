package org.yura.model;

/**
 * The {@code MessageTransfer} interface defines methods for sending and receiving messages,
 * as well as managing player registrations in a chat application.
 */
public interface MessageTransfer {

    void sendMessage(Message msg);

    Message receiveMessage(String to);

    void addPlayer(String playerName);
}
