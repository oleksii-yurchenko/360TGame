package org.yura.three.processes.chat;

import java.io.*;
import java.net.Socket;
import org.yura.model.MessageService;
import org.yura.model.Player;

public class SocketMessageService implements MessageService {
    private final Socket socketClient;
    private final int timeout;

    public SocketMessageService(String host, int port, int timeout) throws IOException {
        this.socketClient = new Socket(host, port);
        this.timeout = timeout;
    }

    @Override
    public void sendMessage(String msg) {
        try {
            Thread.sleep(timeout);

            OutputStream output = socketClient.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(msg);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveMessage() {
        String msg = null;

        try {
            InputStream input = socketClient.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            msg = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public void addPlayer(Player player) {
        player.setTransport(this);

        try {
            OutputStream output = socketClient.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

             writer.println(player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
