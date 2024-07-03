package org.yura.threadsImpl;

import org.yura.player.MessageService;
import org.yura.player.Player;

public class TwoThreadedChat {
    public static void main(String[] args) {
        MessageService service = new BlockingQueueMessageService();

        Player initiator = new Player("Alex");
        Player repeater = new Player("Anna");

        initiator.setTransport(service);
        repeater.setTransport(service);

        run(initiator, repeater, "Hello!");
    }

    static public void run(Player initiator, Player repeater, String startMsg){
        new Thread(() -> {
            initiator.sendMessage(startMsg);

            while (initiator.getReceivedMsgCount() < 10){
                String msg = initiator.receiveMessage();
                if (initiator.getSentMsgCount() < 10)
                    initiator.sendMessage(msg + " " + initiator.getSentMsgCount());
            }

            System.out.println("The app has stopped. The message limit has been reached!");
        }).start();

        new Thread(() -> {
            while (initiator.getSentMsgCount() < 10){
                String msg = repeater.receiveMessage();
                repeater.sendMessage(msg + " " + repeater.getSentMsgCount());
            }
        }).start();
    }
}
