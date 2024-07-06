package org.yura.multi.processes.chat;

import org.yura.Config;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code SocketServer} class implements a basic chat server that listens for client connections
 * and handles message forwarding between connected clients.
 */
public class SocketServer {
    private static final Map<String, Socket> clients = new ConcurrentHashMap<>();
    private static final int port = new Config().getPort();

    /**
     * The server listens on the specified port accepts client connections and handles them in a
     * separate thread.
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                handleClient(socket);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles communication with a connected client in a new thread.
     * The server treats the first message as the client's name and saves it to the server's state map.
     * All subsequent messages are split to identify the receiver and forward the message to them.
     */
    private static void handleClient(Socket socket){
        new Thread(() -> {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String clientName =  reader.readLine();
                clients.put(clientName, socket);

                while (true){
                    String msg = reader.readLine();
                    String to = msg.split(":")[1];

                    OutputStream output = clients.get(to).getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println(msg);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
