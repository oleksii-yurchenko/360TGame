package org.yura.single.process.chat;

import org.yura.model.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The {@code BlockingQueueMessenger} class implements the {@code Messenger} interface.
 * This class uses blocking queues for message passing between players, with an optional timeout.
 */
public class BlockingQueueMessageService implements MessageService {
    private final Map<String, BlockingQueue<String>> messages = new HashMap<>();
    private final int timeout;

    public BlockingQueueMessageService(int timeout) {
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
