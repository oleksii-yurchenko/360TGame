package org.yura.multi.processes.chat;

import org.yura.Config;
import org.yura.model.Message;
import org.yura.multi.processes.chat.common.ChatCommand;
import org.yura.multi.processes.chat.common.SocketStream;
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
    private final Map<String, SocketStream> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.run();
    }

    /**
     * Starts the server to listen for client connections and their commands, as well
     * as forwarding messages between clients. Finally, it closes all connections and
     * the server socket upon receiving a stop command.
     */
    private void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            handleConnections(serverSocket);

            closeClients();
            serverSocket.close();

            System.out.println("The server has been stopped...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleConnections(ServerSocket serverSocket) throws IOException{
        while (true) {
            Socket connection = serverSocket.accept();
            SocketStream client = new SocketStream(connection);
            String msg = client.readLine();

            if (msg.startsWith(ChatCommand.CHAT_REGISTER)){
                String playerName = msg.split(":")[1];
                clients.put(playerName, client);
                client.writeLine(ChatCommand.CHAT_REGISTER_ACK);

                handleMessageForwarding(client, playerName);
            }

            if (msg.equals(ChatCommand.CHAT_STOP_SERVER)){
                break;
            }
        }
    }

    private void handleMessageForwarding(SocketStream client, String playerName){
        new Thread(() -> {
            try {
                while (true){
                    String strMsg = client.readLine();

                    if (strMsg == null) {
                        System.out.printf("Player %s disconnected...%n", playerName);
                        break;
                    }

                    if (!Message.isValidMsgString(strMsg)){
                        throw new IllegalArgumentException("Message format is not correct.");
                    }

                    String to = strMsg.split(":")[1];

                    if (!clients.containsKey(to)){
                        throw new IllegalArgumentException("Receiver not found! " + to);
                    }

                    clients.get(to).writeLine(strMsg);
                }
            } catch (IOException | IllegalArgumentException err) {
                err.printStackTrace();
            }
        }).start();
    }

    private void closeClients() throws IOException {
        for (SocketStream client: clients.values()){
            client.close();
        }
    }
}
