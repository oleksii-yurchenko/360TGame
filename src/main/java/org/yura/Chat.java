package org.yura;

public class Chat {
    public static void main(String[] args) {
        MessageService service = new MessageServiceImpl();

        Player initiator = Player.getPlayer("Alex", "John", service);
        Player repeater = Player.getPlayer("John", "Alex", service);

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
