package org.yura.utils;

import org.yura.model.MessageTransfer;
import org.yura.model.Player;
import org.yura.single.process.chat.BlockingQueueTransfer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @org.junit.jupiter.api.Test
    void createMessage() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(text, to, from);

        assertEquals(message.getFrom(), from);
    }


    @org.junit.jupiter.api.Test
    void serializeToString() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(text, to, from);

        assertEquals(message.toString(), from + ":" + to + ":" + text);
    }
}
