package org.yura.model;

import java.io.IOException;

/**
 * The {@code MessageTransfer} interface defines methods for sending and receiving messages,
 * as well as managing player registrations in a chat application.
 * The correct message format is {@code from:to:text}. This format was chosen to avoid the need
 * for object serialization/deserialization while transferring through processes in a socket solution.
 */
public interface MessageTransfer {

    void sendMessage(String msg) throws InterruptedException;

    String receiveMessage(String to) throws InterruptedException, IOException;

    void addPlayer(String playerName);
}
