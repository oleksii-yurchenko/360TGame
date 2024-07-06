package org.yura.single.process.chat;

import org.yura.Config;
import org.yura.model.MessageService;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import org.yura.model.message.strategy.RepeaterStrategy;

/**
 * The {@code SingleProcessChat} class sets up and runs a single process chat application
 * using two players and blocking queues for message passing.
 */
public class SingleProcessChat {
    public static Config config = new Config();

    public static String playerName = config.getFirstPlayerName();
    public static String partnerName = config.getSecondPlayerName();
    public static int limit = config.getMsgLimit();
    public static String startMsg = config.getStartMsg();
    public static int timeout = config.getTimeout();

    public static void main(String[] args) {
        MessageService transport = new BlockingQueueMessageService(timeout);

        Player initiator = new Player(playerName, transport);
        Player repeater = new Player(partnerName, transport);

        run(initiator, repeater);
    }

    static public void run(Player player1, Player player2){
       new Thread(() -> player1.communicate(new InitiatorStrategy(partnerName, limit, startMsg))).start();
       new Thread(() -> player2.communicate(new RepeaterStrategy(limit))).start();
    }
}
