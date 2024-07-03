package org.yura.socketsImpl;

import org.yura.player.Player;
import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        SocketMessageService service = new SocketMessageService(5555);
        service.waitForConnection();

        Player repeater = new Player("John");
        repeater.setTransport(service);

        while (repeater.getSentMsgCount() < 10) {
            String msg = repeater.receiveMessage();
            repeater.sendMessage(msg + " " + repeater.getSentMsgCount());
        }

    }
}
