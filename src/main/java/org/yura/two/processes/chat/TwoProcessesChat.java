package org.yura.two.processes.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TwoProcessesChat {
    public static void main(String[] args) {
        try {
            ProcessBuilder serverBuilder = new ProcessBuilder("java", "-cp", "target/classes", "org.yura.two.processes.chat.Server");
            ProcessBuilder clientBuilder = new ProcessBuilder("java", "-cp", "target/classes", "org.yura.two.processes.chat.Client");

            Process serverProcess = serverBuilder.start();
            Process clientProcess = clientBuilder.start();

            handleOutputStream(serverProcess);
            handleOutputStream(clientProcess);

            serverProcess.waitFor();
            clientProcess.waitFor();
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
