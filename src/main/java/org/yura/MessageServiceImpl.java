package org.yura;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MessageServiceImpl implements MessageService {
    private Map<String, Player> players = new HashMap<>();
    private Map<String, Map<String, BlockingQueue<String>>> messageQueues = new HashMap<>();

    @Override
    public void addPlayer(Player player) {
        players.put(player.getName(), player);
        player.setTransport(this);
        messageQueues.put(player.getName(), new ConcurrentHashMap<>());
    }

    @Override
    public void sendMessage(String from, String to, String msg) {
        if (!players.containsKey(from) || !players.containsKey(to))
            throw new IllegalArgumentException("Player is not registered!");

        try {
            messageQueues
                    .get(to)
                    .computeIfAbsent(from, k -> new ArrayBlockingQueue<>(1))
                    .put(msg);
        } catch (InterruptedException err) {
            throw new IllegalStateException("Failed to send message", err);
        }
    }

    @Override
    public String receiveMessage(String from, String to) {
        if (!players.containsKey(from) || !players.containsKey(to))
            throw new IllegalArgumentException("Player is not registered!");

        try{
            Thread.sleep(1000);

            return messageQueues
                    .get(to)
                    .computeIfAbsent(from, k -> new ArrayBlockingQueue<>(1))
                    .take();
        } catch (InterruptedException err){
            throw new IllegalStateException("Failed to receive message", err);
        }
    }

}
