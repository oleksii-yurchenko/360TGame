package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.utils.Messages;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code ChatServer} class implements a basic chat server that listens for client connections
 * and handles message forwarding between connected clients.
 */
public class ChatServer {
    private final int port = new Config().getPort();
    private final Map<String, Socket> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }

    /**
     * Starts the server to listen for client connections and handle incoming messages.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *     <li>Registers new clients based on the received messages.</li>
     *     <li>Reads and forwards messages from clients.</li>
     *     <li>Closes all client connections and the server socket upon receiving a stop command.</li>
     * </ul>
     */
    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket client = serverSocket.accept();
                String msg = readMessage(client);

                if (msg.startsWith("CMD_REGISTER:")){
                    String playerName = msg.split(":")[1];
                    clients.put(playerName, client);
                    System.out.println("New client connected: " + playerName);
                }

                if (msg.startsWith("CMD_STOP_SERVER")){
                    break;
                }

                handleMessageForwarding(client);
            }

            closeClients();
            serverSocket.close();
            System.out.println("The server has been stopped.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles communication with a connected client in a new thread.
     * It splits messages to identify the receiver and forward the message to them.
     */
    private void handleMessageForwarding(Socket client){
        new Thread(() -> {
            try {
                while (true){
                    String msg = readMessage(client);

                    if (!Messages.isValidMsg(msg)){
                        throw new IllegalArgumentException("Message format is not correct.");
                    }

                    String to = msg.split(":")[1];

                    if (!clients.containsKey(to)){
                        throw new IllegalArgumentException("Receiver not found!");
                    }

                    writeMessage(clients.get(to), msg);
                }
            } catch (IOException | IllegalArgumentException err) {
                err.printStackTrace();
            }
        }).start();
    }


    private void writeMessage(Socket client, String msg) throws IOException {
        OutputStream output = client.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        writer.println(msg);
    }

    private String readMessage(Socket client) throws IOException {
        InputStream input = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return reader.readLine();
    }

    private void closeClients() throws IOException {
        for (Socket client: clients.values()){
            client.close();
        }
    }

}