package org.yura.model;

import org.yura.single.process.chat.BlockingQueueTransfer;
import org.yura.utils.Message;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @org.junit.jupiter.api.Test
    void sendMessage() {

        MessageTransfer transport = new BlockingQueueTransfer(1);

        Player player = new Player("test", transport);
        Player reciver = new Player("aaa", transport);

        assertEquals(player.getSentMsgCount(), 0);

        player.sendMessage(new Message(player.getName(), reciver.getName(), "foo"));
        assertEquals(player.getSentMsgCount(), 1);
    }
}
