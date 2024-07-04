package org.yura.two.processes.chat;

import org.yura.Config;
import org.yura.model.Player;
import org.yura.model.message.strategy.RepeaterStrategy;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Config config = new Config();

        try (ServerSocket server = new ServerSocket(config.getPort());
            Socket client = server.accept()) {

            SocketMessageService service = new SocketMessageService(client, config.getTimeout());

            Player player = new Player(config.getSecondPlayerName());
            player.setTransport(service);
            player.communicate(new RepeaterStrategy(config.getMsgLimit()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
