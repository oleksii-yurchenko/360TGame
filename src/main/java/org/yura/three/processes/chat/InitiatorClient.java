package org.yura.three.processes.chat;

import org.yura.Config;
import org.yura.model.MessageService;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import java.io.IOException;

public class InitiatorClient {
    public static Config config = new Config();

    public static String host = config.getHost();
    public static int port = config.getPort();
    public static String playerName = config.getFirstPlayerName();
    public static String partnerName = config.getSecondPlayerName();
    public static int limit = config.getMsgLimit();
    public static String startMsg = config.getStartMsg();
    public static int timeout = config.getTimeout();

    public static void main(String[] args) throws IOException {
        Player player = new Player(playerName);
        MessageService transport = new SocketMessageService(host, port, timeout);
        transport.addPlayer(player);
        player.communicate(new InitiatorStrategy(partnerName, limit, startMsg));
    }
}
