package org.yura.multi.processes.chat;

import java.io.*;
import java.net.Socket;
import org.yura.model.MessageService;

/**
 * The {@code SocketMessenger} class implements the {@code Messenger} interface.
 * This class uses a socket connection for sending and receiving messages between players.
 */
public class SocketMessageService implements MessageService{
    private final Socket socketClient;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final int timeout;
    private final String host;
    private final int port;

    public SocketMessageService(String host, int port, int timeout) throws IOException {
        this.socketClient = new Socket(host, port);
        this.writer = new PrintWriter(socketClient.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        this.timeout = timeout;
        this.host = host;
        this.port = port;
    }

    @Override
    public void sendMessage(String msg) {
        try {
            Thread.sleep(timeout);
            writer.println(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayer(String playerName) {
        writer.println("CMD_REGISTER:" + playerName);
    }

    @Override
    public String receiveMessage(String to) {
        String msg = null;

        try {
            msg = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public void stopServer() throws IOException {
        Socket unlock = new Socket(host, port);

        OutputStream unlockOutput = unlock.getOutputStream();
        PrintWriter unlockWriter = new PrintWriter(unlockOutput, true);

        unlockWriter.println("CMD_STOP_SERVER");
        unlock.close();
    }
}
