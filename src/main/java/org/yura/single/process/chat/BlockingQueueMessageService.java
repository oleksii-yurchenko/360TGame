package org.yura.single.process.chat;

import org.yura.model.MessageService;
import org.yura.model.Player;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueMessageService implements MessageService{
    private final BlockingQueue<String> input;
    private final BlockingQueue<String> output;
    private final int timeout;

    public BlockingQueueMessageService(BlockingQueue<String> input, BlockingQueue<String> output, int timeout) {
        this.input = input;
        this.output = output;
        this.timeout = timeout;
    }

    @Override
    public void sendMessage(String msg) {
        try {
            Thread.sleep(timeout);

           output.put(msg);
        } catch (InterruptedException err) {
            throw new IllegalStateException("Failed to send message", err);
        }
    }

    @Override
    public String receiveMessage() {
       try{
            return input.take();
        } catch (InterruptedException err){
            throw new IllegalStateException("Failed to receive message", err);
        }
    }

    @Override
    public void addPlayer(Player player) {
        player.setTransport(this);
    }

}
