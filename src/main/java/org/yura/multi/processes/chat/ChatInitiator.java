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
    public static void main(String[] args){
        Config config = new Config();

        String playerName = config.getFirstPlayerName();
        String partnerName = config.getSecondPlayerName();
        String startMsg = config.getStartMsg();
        String host = config.getHost();
        int port = config.getPort();
        int limit = config.getMsgLimit();
        int timeout = config.getTimeout();

        try (SocketTransfer transport = new SocketTransfer(host, port, timeout)) {

            Player player = new Player(playerName);
            player.setTransport(transport);
            player.communicate(new InitiatorStrategy(partnerName, limit, startMsg));

            transport.stopServer();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
