package org.yura.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @org.junit.jupiter.api.Test
    void createMessage() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(from, to, text);

        assertEquals(message.getFrom(), from);
    }


    @org.junit.jupiter.api.Test
    void serializeToString() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(from, to, text);

        assertEquals(message.toString(), from + ":" + to + ":" + text);
    }
}
