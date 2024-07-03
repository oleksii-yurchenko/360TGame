package org.yura.socketsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.yura.player.MessageService;

public class SocketMessageService implements MessageService {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public SocketMessageService(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void waitForConnection() throws IOException {
        clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void connectToServer(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public String receiveMessage() {
        try {
            Thread.sleep(1000);
            return in.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to receive message", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
