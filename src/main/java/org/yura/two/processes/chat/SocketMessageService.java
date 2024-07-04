package org.yura.two.processes.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.yura.model.MessageService;

public class SocketMessageService implements MessageService {
    private final int timeout;
    private final BufferedReader input;
    private final PrintWriter output;

    public SocketMessageService(Socket client, int timeout) throws IOException {
        this.timeout = timeout;
        this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.output = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void sendMessage(String msg){
        try {
            Thread.sleep(timeout);
            output.println(msg);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String receiveMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to receive message", e);
        }
    }
}
