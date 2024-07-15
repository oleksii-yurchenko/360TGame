package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.RepeaterStrategy;
import java.io.IOException;

/**
 * The {@code ChatRepeater} class sets up and runs a repeater client for a Socket chat application.
 * It configures the player with a socket transfer mechanism, and then starts communication using a repeater strategy.
 */
public class ChatRepeater {
    public static void main(String[] args) {
        Config config = new Config();

        String playerName = config.getSecondPlayerName();
        String host = config.getHost();
        int port = config.getPort();
        int limit = config.getMsgLimit();
        int timeout = config.getTimeout();

        try (SocketTransfer transport = new SocketTransfer(host, port, timeout)){

            Player player = new Player(playerName);
            player.setTransport(transport);
            player.communicate(new RepeaterStrategy(limit));

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
