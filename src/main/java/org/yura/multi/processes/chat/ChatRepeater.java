package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.RepeaterStrategy;
import java.io.IOException;

/**
 * The {@code RepeaterClient} class sets up and runs a repeater client for a Socket chat application.
 * It configures the player with a socket messenger, sends a start message to register the player's data on the server,
 * and then starts communication using a repeater strategy.
 */
public class ChatRepeater {
    public static void main(String[] args) throws IOException {
        Config config = new Config();

        String host = config.getHost();
        int port = config.getPort();
        String playerName = config.getSecondPlayerName();
        int limit = config.getMsgLimit();
        int timeout = config.getTimeout();

        SocketMessageService transport = new SocketMessageService(host, port, timeout);
        Player player = new Player(playerName, transport);
        player.communicate(new RepeaterStrategy(limit));
    }
}
