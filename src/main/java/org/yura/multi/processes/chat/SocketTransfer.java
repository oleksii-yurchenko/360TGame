package org.yura.multi.processes.chat;

import java.io.*;
import java.net.Socket;
import java.nio.channels.NotYetBoundException;
import java.util.function.Consumer;

import org.yura.model.MessageTransfer;
import org.yura.model.Message;

/**
 * The {@code SocketTransfer} class implements the {@code MessageTransfer} interface.
 * This class uses a socket connection for sending and receiving messages between players.
 */
public class SocketTransfer implements MessageTransfer {
    private final Socket socketClient;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final int timeout;
    private final String host;
    private final int port;

    public SocketTransfer(String host, int port, int timeout) throws IOException {
        this.socketClient = new Socket(host, port);
        this.writer = new PrintWriter(socketClient.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        this.timeout = timeout;
        this.host = host;
        this.port = port;
    }

    @Override
    public void sendMessage(Message msg) {
        try {
            Thread.sleep(timeout);
            writer.println(msg);
        }catch (InterruptedException exp) {
            exp.printStackTrace();
        }

    }

    @Override
    public Message receiveMessage(String to) throws IOException {
        return new Message(reader.readLine());
    }

    /**
     * @deprecated see 'addListener'
     * @param playerName
     */

    @Override
    public void addPlayer(String playerName) {
        writer.println("CMD_REGISTER:" + playerName);
    }


    @Override
    public void onReceive(String name, Consumer<Message> handler) {
        throw new NotYetBoundException();
    }

    /**
     * This command causes the server to break out of its accept loop and perform shutdown procedures.
     */
    public void stopServer() throws IOException {
        Socket unlock = new Socket(host, port);

        OutputStream unlockOutput = unlock.getOutputStream();
        PrintWriter unlockWriter = new PrintWriter(unlockOutput, true);

        unlockWriter.println("CMD_STOP_SERVER");
        unlock.close();
    }
}
