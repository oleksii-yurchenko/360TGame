package org.yura.single.process.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yura.model.Message;

import static org.junit.jupiter.api.Assertions.*;

public class BlockingQueueTransferTest {
    private BlockingQueueTransfer transfer;

    @BeforeEach
    public void setUp() {
        transfer = new BlockingQueueTransfer(100);
    }

    @Test
    public void testReceiveMessage() {
        transfer.addPlayer("Alex");
        transfer.addPlayer("Anna");

        Message sentMessage = new Message("Alex", "Anna", "Hello");
        transfer.sendMessage(sentMessage);

        Message receivedMessage = transfer.receiveMessage("Anna");
        assertNotNull(receivedMessage);
        assertEquals("Hello", receivedMessage.getContent());
        assertEquals("Alex", receivedMessage.getFrom());
        assertEquals("Anna", receivedMessage.getTo());
    }

    @Test
    public void testSendMessageToUnknownPlayer() {
        Message msg = new Message("Alex", "Unknown", "Hello");
        assertThrows(IllegalArgumentException.class, () -> {
            transfer.sendMessage(msg);
        });
    }

    @Test
    public void testReceiveMessageFromUnknownPlayer() {
        assertThrows(IllegalArgumentException.class, () -> {
            transfer.receiveMessage("Unknown");
        });
    }
}
