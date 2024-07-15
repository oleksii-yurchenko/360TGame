package org.yura.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testConstructorWithParameters() {
        Message message = new Message("Alex", "Anna", "Hello");
        assertEquals("Alex", message.getFrom());
        assertEquals("Anna", message.getTo());
        assertEquals("Hello", message.getContent());
    }

    @Test
    public void testConstructorWithString() {
        Message message = new Message("Alex:Anna:Hello");
        assertEquals("Alex", message.getFrom());
        assertEquals("Anna", message.getTo());
        assertEquals("Hello", message.getContent());
    }

    @Test
    public void testConstructorWithStringInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Message("Alex-Anna-Hello");
        });
    }

    @Test
    public void testIsValidMsgString() {
        assertTrue(Message.isValidMsgString("Alex:Anna:Hello"));
        assertFalse(Message.isValidMsgString("Alex-Anna-Hello"));
    }

    @Test
    public void testEquals() {
        Message message1 = new Message("Alex", "Anna", "Hello");
        Message message2 = new Message("Alex", "Anna", "Hello");
        Message message3 = new Message("Alex", "John", "Hi");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
    }

    @Test
    public void testToString() {
        Message message = new Message("Alex", "Anna", "Hello");
        assertEquals("Alex:Anna:Hello", message.toString());
    }
}
