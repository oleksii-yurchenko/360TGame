package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;

import java.io.*;

/**
 * The {@code InitiatorClient} class sets up and runs an initiator client for a Socket chat application.
 * It configures the player with a socket messenger, sends a start message to register the player's data on the server,
 * and then starts communication using an initiator strategy.
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

        SocketMessageService transport = new SocketMessageService(host, port, timeout);
        Player player = new Player(playerName, transport);
        player.communicate(new InitiatorStrategy(partnerName, limit, startMsg));

        transport.stopServer();
    }
}
