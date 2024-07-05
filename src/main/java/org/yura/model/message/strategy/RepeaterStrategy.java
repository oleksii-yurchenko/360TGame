package org.yura.model.message.strategy;

import org.yura.model.Player;
import org.yura.utils.Messages;

public class RepeaterStrategy implements MessageStrategy {
    private final int msgLimit;

    public RepeaterStrategy(int msgLimit) {
        this.msgLimit = msgLimit;
    }

    @Override
    public void start(Player player) {
        while (player.getSentMsgCount() < msgLimit){
            String msg = player.receiveMessage();
            player.sendMessage(Messages.reverseMsg(msg) + " " + player.getSentMsgCount());
        }
    }
}
