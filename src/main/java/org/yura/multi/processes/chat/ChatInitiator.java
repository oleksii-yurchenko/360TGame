package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import java.io.*;

/**
 * The {@code ChatInitiator} class sets up and runs an initiator client for a socket chat application.
 * It configures the player with a socket transfer mechanism, and then starts communication using an initiator strategy.
 * After finishing, it sends a stop signal to the chat server to initiate its shutdown.
 */
public class ChatInitiator {
    public static void main(String[] args) throws IOException {
        Config config = new Config();

        String host = config.getHost();
        int port = config.getPort();
        String playerName = config.getFirstPlayerName();
        String partnerName = config.getSecondPlayerName();
        int limit = config.getMsgLimit();
        String startMsg = config.getStartMsg();
        int timeout = config.getTimeout();

        SocketTransfer transport = new SocketTransfer(host, port, timeout);
        Player player = new Player(playerName, transport);

        transport.addPlayer(player.getName());
        player.communicate(new InitiatorStrategy(partnerName, limit, startMsg));

        transport.stopServer();
    }
}
