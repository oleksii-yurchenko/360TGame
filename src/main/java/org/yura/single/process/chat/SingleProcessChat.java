package org.yura.single.process.chat;

import org.yura.Config;
import org.yura.model.MessageTransfer;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import org.yura.model.message.strategy.RepeaterStrategy;

/**
 * The {@code SingleProcessChat} class sets up and runs a single process chat application
 * using two players and blocking queues for message passing.
 */
public class SingleProcessChat {
    public static void main(String[] args) {
        Config config = new Config();

        String playerName = config.getFirstPlayerName();
        String partnerName = config.getSecondPlayerName();
        int limit = config.getMsgLimit();
        String startMsg = config.getStartMsg();
        int timeout = config.getTimeout();

        MessageTransfer transport = new BlockingQueueTransfer(timeout);

        Player player1 = new Player(playerName);
        Player player2 = new Player(partnerName);

        player1.setTransport(transport);
        player2.setTransport(transport);

        new Thread(() -> player1.communicate(new InitiatorStrategy(partnerName, limit, startMsg))).start();
        new Thread(() -> player2.communicate(new RepeaterStrategy(limit))).start();
    }
}
