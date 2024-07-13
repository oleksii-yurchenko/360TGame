package org.yura.single.process.chat;

import org.yura.model.MessageTransfer;
import org.yura.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The {@code BlockingQueueTransfer} class implements the {@code MessageTransfer} interface.
 * This class uses blocking queues for message passing between players, with an optional timeout.
 */
public class BlockingQueueTransfer implements MessageTransfer {
    private final Map<String, BlockingQueue<String>> messages = new HashMap<>();
    private final int timeout;

    public BlockingQueueTransfer(int timeout) {
        this.timeout = timeout;
    }

    public void sendMessage(Message message) throws InterruptedException {
        String to =message.getTo();

        if (!messages.containsKey(to)){
            throw new IllegalArgumentException("Receiver not found!");
        }

        Thread.sleep(timeout);
        messages.get(to).put(message.toString());
    }

    public Message receiveMessage(String to) throws InterruptedException {
        if (!messages.containsKey(to)){
            throw new IllegalArgumentException("Receiver not found!");
        }

        return new Message(messages.get(to).take());
    }

    public void addPlayer(String playerName){
        if (playerName != null){
            messages.put(playerName, new ArrayBlockingQueue<>(1));
        }
    }
}
