package org.yura.multi.processes.chat.common;

import java.io.*;
import java.net.Socket;

/**
 * The {@code SocketStream} class provides a wrapper around a {@code Socket},
 * encapsulating input and output streams to facilitate reading from and writing to the socket.
 * It implements the {@code AutoCloseable} interface to allow usage in try-with-resources statements.
 */
public class SocketStream implements AutoCloseable{
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public SocketStream(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void writeLine(String message) {
        writer.println(message);
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
}
