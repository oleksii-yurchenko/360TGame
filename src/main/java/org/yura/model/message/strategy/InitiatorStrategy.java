package org.yura.model.message.strategy;

import org.yura.model.Player;
import org.yura.utils.Messages;

public class InitiatorStrategy implements MessageStrategy {
    private final String partner;
    private final int msgLimit;
    private final String startMsg;

    public InitiatorStrategy(String partner, int msgLimit, String startMsg) {
        this.partner = partner;
        this.msgLimit = msgLimit;
        this.startMsg = startMsg;
    }

    @Override
    public void start(Player player) {
        player.sendMessage(player.getName() + ":" + partner + ":" + startMsg);

        while (player.getReceivedMsgCount() < msgLimit){
            String msg = player.receiveMessage();

            if (player.getSentMsgCount() < msgLimit)
                player.sendMessage(Messages.reverseMsg(msg) + " " + player.getSentMsgCount());
        }

        System.out.println("Stop condition has reached.");
    }
}
