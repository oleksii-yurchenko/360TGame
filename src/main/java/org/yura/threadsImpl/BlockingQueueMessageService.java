package org.yura.threadsImpl;

import org.yura.player.MessageService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueMessageService implements MessageService {
    private BlockingQueue<String> messagesQueue = new ArrayBlockingQueue<>(1);

    @Override
    public void sendMessage(String msg) {
        try {
            messagesQueue.put(msg);
        } catch (InterruptedException err) {
            throw new IllegalStateException("Failed to send message", err);
        }
    }

    @Override
    public String receiveMessage() {
        try{
            Thread.sleep(1000);
            return messagesQueue.take();
        } catch (InterruptedException err){
            throw new IllegalStateException("Failed to receive message", err);
        }
    }

}
