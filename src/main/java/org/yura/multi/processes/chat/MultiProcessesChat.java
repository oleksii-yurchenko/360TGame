package org.yura.multi.processes.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code ThreeProcessesChat} class is responsible for starting and managing three separate processes:
 * a server, an initiator client, and a repeater client. It uses {@link ProcessBuilder} to start each process
 * and handles their output streams.
 */
public class MultiProcessesChat {
    public static void main(String[] args) {
        try {
            ProcessBuilder serverBuilder = new ProcessBuilder("java", "-cp", "target/classes", "org.yura.multi.processes.chat.SocketServer");
            ProcessBuilder initiatorBuilder = new ProcessBuilder("java", "-cp", "target/classes", "org.yura.multi.processes.chat.InitiatorClient");
            ProcessBuilder repeaterBuilder = new ProcessBuilder("java", "-cp", "target/classes", "org.yura.multi.processes.chat.RepeaterClient");

            Process serverProcess = serverBuilder.start();
            Process initiatorProcess = initiatorBuilder.start();
            Process repeaterProcess = repeaterBuilder.start();

            handleOutputStream(serverProcess);
            handleOutputStream(initiatorProcess);
            handleOutputStream(repeaterProcess);

            initiatorProcess.waitFor();
            repeaterProcess.waitFor();
            serverProcess.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void handleOutputStream(Process process){
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("PID " + process.pid() + ": " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
