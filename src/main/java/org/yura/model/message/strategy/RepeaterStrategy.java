package org.yura.model.message.strategy;

import org.yura.model.Player;
import org.yura.utils.Messages;

/**
 * The {@code RepeaterStrategy} class implements the {@code MessageStrategy} interface.
 * This strategy continuously receives messages and sends them back reversed, until a message limit is reached.
 */
public class RepeaterStrategy implements MessageStrategy {
    private final int msgLimit;

    public RepeaterStrategy(int msgLimit) {
        this.msgLimit = msgLimit;
    }

    @Override
    public void start(Player player) {
        while (player.getSentMsgCount() < msgLimit) {
            String msg = player.receiveMessage();
            player.sendMessage(Messages.reverseMsg(msg) + " " + player.getSentMsgCount());
        }
    }
}
