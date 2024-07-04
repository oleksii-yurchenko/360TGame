package org.yura.model.message.strategy;

import org.yura.model.Player;

public class InitiatorStrategy implements MessageStrategy {
    private final int msgLimit;
    private final String startMsg;

    public InitiatorStrategy(int msgLimit, String startMsg) {
        this.msgLimit = msgLimit;
        this.startMsg = startMsg;
    }

    @Override
    public void start(Player player) {
        player.sendMessage(startMsg);

        while (player.getReceivedMsgCount() < msgLimit){
            String msg = player.receiveMessage();
            if (player.getSentMsgCount() < msgLimit)
                player.sendMessage(msg + " " + player.getSentMsgCount());
        }
    }
}
