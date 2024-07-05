package org.yura.single.process.chat;

import org.yura.Config;
import org.yura.model.MessageService;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import org.yura.model.message.strategy.RepeaterStrategy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SingleProcessChat {
    public static Config config = new Config();

    public static String playerName = config.getFirstPlayerName();
    public static String partnerName = config.getSecondPlayerName();
    public static int limit = config.getMsgLimit();
    public static String startMsg = config.getStartMsg();
    public static int timeout = config.getTimeout();

    public static void main(String[] args) {
        Player initiator = new Player(playerName);
        Player repeater = new Player(partnerName);

        BlockingQueue<String> input = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> output = new ArrayBlockingQueue<>(1);

        MessageService initiatorTransport = new BlockingQueueMessageService(input, output, timeout);
        MessageService repeaterTransport = new BlockingQueueMessageService(output, input, timeout);

        initiatorTransport.addPlayer(initiator);
        repeaterTransport.addPlayer(repeater);

        run(initiator, repeater);
    }

    static public void run(Player player1, Player player2){
       new Thread(() -> player1.communicate(new InitiatorStrategy(partnerName, limit, startMsg))).start();
       new Thread(() -> player2.communicate(new RepeaterStrategy(limit))).start();
    }
}
