package org.yura.model.message.strategy;

import org.yura.model.Player;
import org.yura.utils.Message;

/**
 * The {@code InitiatorStrategy} class implements the {@code MessageStrategy} interface.
 * This strategy initiates a conversation with a specified partner, sending a start message
 * and responding to received messages until a message limit is reached.
 */
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
        player.sendMessage(new Message(player.getName(), partner, startMsg));

        while (player.getReceivedMsgCount() < msgLimit){
            Message msg = player.receiveMessage();

            if (player.getSentMsgCount() < msgLimit) {
                Message newMessage = msg.reverse();
                newMessage.setText(newMessage.getText() + " " + player.getSentMsgCount());
                player.sendMessage(newMessage);
            }
        }

        System.out.println("Stop condition has reached.");
    }
}
