package org.yura.model;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * The {@code MessageTransfer} interface defines methods for sending and receiving messages,
 * as well as managing player registrations in a chat application.
 * The correct message format is {@code from:to:text}. This format was chosen to avoid the need
 * for object serialization/deserialization while transferring through processes in a socket solution.
 */
public interface MessageTransfer {

    void sendMessage(Message msg);

    Message receiveMessage(String to) throws InterruptedException, IOException;

    void addPlayer(String playerName);

    /**
     * Supersede `addPlayer` with more generic registration of handler
     * @param name - player uniq name
     * @param handler - handler to act on message
     */

    void onReceive(String name, Consumer<Message> handler);

}
