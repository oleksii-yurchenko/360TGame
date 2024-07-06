package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.MessageService;
import org.yura.model.Player;
import org.yura.model.message.strategy.RepeaterStrategy;
import java.io.IOException;

/**
 * The {@code InitiatorClient} class sets up and runs a repeater client for a Socket chat application.
 * It configures the player and the message service, then starts communication using a repeater strategy.
 */
public class RepeaterClient {
    public static Config config = new Config();

    public static String host = config.getHost();
    public static int port = config.getPort();
    public static String playerName = config.getSecondPlayerName();
    public static int limit = config.getMsgLimit();
    public static int timeout = config.getTimeout();

    public static void main(String[] args) throws IOException {
        Player player = new Player(playerName);
        MessageService transport = new SocketMessageService(host, port, timeout);
        transport.addPlayer(player);
        player.communicate(new RepeaterStrategy(limit));
    }
}
