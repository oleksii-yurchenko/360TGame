package org.yura.model;

public interface MessageService {
    void sendMessage(String msg);
    String receiveMessage();
}
