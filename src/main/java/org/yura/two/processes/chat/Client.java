package org.yura.two.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.InitiatorStrategy;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Config config = new Config();

        try (Socket client = new Socket(config.getHost(), config.getPort())) {

            SocketMessageService service = new SocketMessageService(client, config.getTimeout());

            Player player = new Player(config.getFirstPlayerName());
            player.setTransport(service);
            player.communicate(new InitiatorStrategy(config.getMsgLimit(), config.getStartMsg()));

            System.out.println("The app has stopped. The message limit has been reached!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
