package org.yura.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @Test
    void createMessage() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(from, to, text);

        assertEquals(message.getFrom(), from);
    }


    @Test
    void serializeToString() {

        String text = "foo";
        String to = "ivan";
        String from = "fedor";

        Message message = new Message(from, to, text);

        assertEquals(message.toString(), from + ":" + to + ":" + text);
    }


    @Test
    void canCreateFromString() {

    }
}
