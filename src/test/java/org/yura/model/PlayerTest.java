package org.yura.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.yura.model.message.strategy.MessageStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayerTest {
    private Player player;
    private MessageTransfer transport;

    @BeforeEach
    public void setUp() {
        player = new Player("Alex");
        transport = Mockito.mock(MessageTransfer.class);
        player.setTransport(transport);
    }

    @Test
    public void testSendMessageWithoutTransport() {
        Player newPlayer = new Player("Anna");
        Message msg = new Message("Anna", "Alex", "Hello");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            newPlayer.sendMessage(msg);
        });

        assertEquals("Transport has not been set!", exception.getMessage());
    }

    @Test
    public void testSendMessageWithTransport() {
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        Message msg = new Message("Alex", "Anna", "Hello");
        player.sendMessage(msg);

        verify(transport, times(1)).sendMessage(messageCaptor.capture());
        assertEquals(msg, messageCaptor.getValue());
    }

    @Test
    public void testReceiveMessageWithoutTransport() {
        Player newPlayer = new Player("Anna");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            newPlayer.receiveMessage();
        });

        assertEquals("Transport has not been set!", exception.getMessage());
    }

    @Test
    public void testReceiveMessageWithTransport() {
        Message msg = new Message("Anna", "Alex", "Hello");
        when(transport.receiveMessage("Alex")).thenReturn(msg);

        Message receivedMsg = player.receiveMessage();

        assertEquals(msg, receivedMsg);
    }

    @Test
    public void testCommunicate() {
        MessageStrategy strategy = mock(MessageStrategy.class);
        player.communicate(strategy);

        verify(strategy, times(1)).start(player);
    }

    @Test
    public void testSetTransport() {
        Player newPlayer = new Player("Anna");
        MessageTransfer newTransport = mock(MessageTransfer.class);

        newPlayer.setTransport(newTransport);

        verify(newTransport, times(1)).addPlayer("Anna");
    }

    @Test
    public void testGetSentMsgCount() {
        assertEquals(0, player.getSentMsgCount());
        player.sendMessage(new Message("Alex", "Anna", "Hello"));
        assertEquals(1, player.getSentMsgCount());
    }

    @Test
    public void testGetReceivedMsgCount() {
        assertEquals(0, player.getReceivedMsgCount());
        when(transport.receiveMessage("Alex")).thenReturn(new Message("Anna", "Alex", "Hello"));
        player.receiveMessage();
        assertEquals(1, player.getReceivedMsgCount());
    }
}
