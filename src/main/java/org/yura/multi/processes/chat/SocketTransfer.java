package org.yura.multi.processes.chat;

import java.io.*;
import java.net.Socket;
import org.yura.model.Message;
import org.yura.model.MessageTransfer;
import org.yura.multi.processes.chat.common.ChatCommand;
import org.yura.multi.processes.chat.common.SocketStream;

/**
 * The {@code SocketTransfer} class implements the {@code MessageTransfer} interface.
 * This class uses a socket connection for sending and receiving messages between players.
 */
public class SocketTransfer implements MessageTransfer, AutoCloseable {
    private final SocketStream socketClient;
    private final int timeout;
    private final String host;
    private final int port;

    public SocketTransfer(String host, int port, int timeout) throws IOException {
        this.socketClient = new SocketStream(new Socket(host, port));
        this.timeout = timeout;
        this.host = host;
        this.port = port;
    }

    @Override
    public void sendMessage(Message msg){
        try {
            Thread.sleep(timeout);
            socketClient.writeLine(msg.toString());
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Message receiveMessage(String to) {
        try {
            String strMsg = socketClient.readLine();
            return new Message(strMsg);
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
    }
    }

    /**
     * Registers a new player by sending a registration command to the server and waiting
     * for an acknowledgment.
     */
    @Override
    public void addPlayer(String playerName) {
        String ackMsg = "";

        try {
            while (!(ackMsg.equals(ChatCommand.CHAT_REGISTER_ACK))){
                socketClient.writeLine(ChatCommand.CHAT_REGISTER + ":" + playerName);
                ackMsg = socketClient.readLine();
            }

            System.out.println(playerName + " has joined the Chat.");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * This command causes the server to break out of its accept loop and perform shutdown procedures.
     */
    public void stopServer(){
        try {
        SocketStream unlock =  new SocketStream(new Socket(host, port));

        unlock.writeLine(ChatCommand.CHAT_STOP_SERVER);
        unlock.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        socketClient.close();
    }
}
