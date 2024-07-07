package org.yura.single.process.chat;

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
    private final Map<String, BlockingQueue<String>> messages = new HashMap<>();
    private final int timeout;

    public BlockingQueueTransfer(int timeout) {
        this.timeout = timeout;
    }

    public void sendMessage(String msg) {
        try {
            Thread.sleep(timeout);

            String to = msg.split(":")[1];
            messages.get(to).put(msg);
        } catch (InterruptedException err) {
            throw new IllegalStateException("Failed to send message", err);
        }
    }

    public String receiveMessage(String to) {
       try{
            return messages.get(to).take();
        } catch (InterruptedException err){
            throw new IllegalStateException("Failed to receive message", err);
        }
    }

    public void addPlayer(String playerName){
        messages.put(playerName, new ArrayBlockingQueue<>(1));
    }
}
