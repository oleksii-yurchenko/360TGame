package org.yura.model;

import org.yura.single.process.chat.BlockingQueueTransfer;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @org.junit.jupiter.api.Test
    void sendMessage() {

        MessageTransfer transport = new BlockingQueueTransfer(1);

        Player player = new Player("test", transport);

        assertEquals(player.getSentMsgCount(), 0);
    }
}
