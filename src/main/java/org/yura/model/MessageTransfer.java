package org.yura.model;

/**
 * The {@code MessageTransfer} interface defines the methods for sending and receiving messages
 * and for managing player registrations in a chat application.
 */
public interface MessageTransfer {

    void sendMessage(String msg);

    String receiveMessage(String to);

    void addPlayer(String playerName);
}
