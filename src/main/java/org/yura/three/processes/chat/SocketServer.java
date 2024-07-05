package org.yura.three.processes.chat;

import org.yura.Config;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer {
    private static final Map<String, Socket> clients = new ConcurrentHashMap<>();
    private static final int port = new Config().getPort();

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
