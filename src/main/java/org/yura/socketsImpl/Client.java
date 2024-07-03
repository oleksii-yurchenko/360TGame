package org.yura.socketsImpl;

import org.yura.player.Player;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketMessageService service = new SocketMessageService(0);
        service.connectToServer("localhost", 5555);

        Player initiator = new Player("Alex");
        initiator.setTransport(service);

        initiator.sendMessage("Hello!");

        while (initiator.getReceivedMsgCount() < 10) {
            String msg = initiator.receiveMessage();
            if (initiator.getSentMsgCount() < 10)
                initiator.sendMessage(msg + " " + initiator.getSentMsgCount());
        }

        System.out.println("The app has stopped. The message limit has been reached!");
    }
}
