package org.yura.single.process.chat;

import org.yura.Config;
import org.yura.model.MessageService;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import org.yura.model.message.strategy.RepeaterStrategy;

public class SingleProcessChat {
    public static void main(String[] args) {
        Config config = new Config();

        MessageService service = new BlockingQueueMessageService(config.getTimeout());

        Player player1 = new Player(config.getFirstPlayerName());
        Player player2 = new Player(config.getSecondPlayerName());

        player1.setTransport(service);
        player2.setTransport(service);

        run(player1, player2, config);
    }

    static public void run(Player player1, Player player2, Config config){
        new Thread(() -> {
            player1.communicate(new InitiatorStrategy(config.getMsgLimit(), config.getStartMsg()));
            System.out.println("The app has stopped. The message limit has been reached!");
        }).start();

        new Thread(() -> {
            player2.communicate(new RepeaterStrategy(config.getMsgLimit()));
        }).start();
    }
}
