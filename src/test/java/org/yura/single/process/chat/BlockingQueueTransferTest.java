package org.yura.single.process.chat;

import org.junit.jupiter.api.Test;
import org.yura.model.Message;
import org.yura.model.MessageTransfer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockingQueueTransferTest {

    @Test
    void subscribeForMessages() {
        var ref = new Object() {
            Message actual;
        };

        Message initMessage = new Message("initiator", "receiver", "booob");

        MessageTransfer transfer = new BlockingQueueTransfer(1);


        transfer.onReceive("receiver", (message) -> {
            ref.actual = message;
        });

        transfer.sendMessage(initMessage);

        assertEquals(initMessage, ref.actual);
    }
}
