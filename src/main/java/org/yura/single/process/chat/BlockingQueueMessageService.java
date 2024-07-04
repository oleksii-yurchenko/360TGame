package org.yura.single.process.chat;

import org.yura.model.MessageService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueMessageService implements MessageService {
    private final BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1);
    private final int timeout;

    public BlockingQueueMessageService(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void sendMessage(String msg) {
        try {
            Thread.sleep(timeout);
            messageQueue.put(msg);
        } catch (InterruptedException err) {
            throw new IllegalStateException("Failed to send message", err);
        }
    }

    @Override
    public String receiveMessage() {
        try{
            return messageQueue.take();
        } catch (InterruptedException err){
            throw new IllegalStateException("Failed to receive message", err);
        }
    }

}
