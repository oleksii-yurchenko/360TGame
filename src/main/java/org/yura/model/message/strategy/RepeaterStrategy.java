package org.yura.model.message.strategy;

import org.yura.model.Player;
import org.yura.utils.Message;

/**
 * The {@code RepeaterStrategy} class implements the {@code MessageStrategy} interface.
 * This strategy continuously receives messages and sends them back to the partner, until a message limit is reached.
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
            Message newMessage = new Message(msg).reverse();
            newMessage.setText(newMessage.getText() + " " + player.getSentMsgCount());


            player.sendMessage(newMessage);
        }
    }
}
