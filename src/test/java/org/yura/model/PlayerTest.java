package org.yura.model;

import org.yura.single.process.chat.BlockingQueueTransfer;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @org.junit.jupiter.api.Test
    void sendMessage() {

        MessageTransfer transport = new BlockingQueueTransfer(1);

        Player player = new Player("test", transport);
        Player receiver = new Player("aaa", transport);

        transport.addPlayer(player.getName());
        transport.addPlayer(receiver.getName());

        assertEquals(player.getSentMsgCount(), 0);

        player.sendMessage(new Message(player.getName(), receiver.getName(), "foo"));
        assertEquals(player.getSentMsgCount(), 1);
    }
}
