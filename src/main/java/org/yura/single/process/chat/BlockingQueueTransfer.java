package org.yura.single.process.chat;

import org.yura.model.Message;
import org.yura.model.MessageTransfer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The {@code BlockingQueueTransfer} class implements the {@code MessageTransfer} interface.
 * This class uses blocking queues for message passing between players, with an optional timeout.
 */
public class BlockingQueueTransfer implements MessageTransfer {
    private final Map<String, BlockingQueue<Message>> messages = new HashMap<>();
    private final int timeout;

    public BlockingQueueTransfer(int timeout) {
        this.timeout = timeout;
    }

    public void sendMessage(Message msg) {
        String to = msg.getTo();

        try {
            if (!messages.containsKey(to)){
                throw new IllegalArgumentException("Receiver not found: " + to);
            }

            Thread.sleep(timeout);
            messages.get(msg.getTo()).put(msg);
        } catch (InterruptedException | IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    public Message receiveMessage(String to) {
        try {
            if (!messages.containsKey(to)){
                throw new IllegalArgumentException("Receiver not found! " + to);
            }

            return messages.get(to).take();
        } catch (InterruptedException | IllegalArgumentException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void addPlayer(String playerName){
        if (playerName != null){
            messages.put(playerName, new ArrayBlockingQueue<>(1));
        }
    }
}
