package org.yura.model;

import java.util.Objects;

/**
 * The {@code Message} class represents a message in a chat system. Each message has a sender,
 * a receiver, and content.
 */
public class Message {
    private String from;
    private String to;
    private String content;

    public Message(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public Message(String strMsg) {
        if (!isValidMsgString(strMsg)){
            throw new IllegalArgumentException("Message format is not valid.");
        }

        this.from = strMsg.split(":")[0];
        this.to = strMsg.split(":")[1];
        this.content = strMsg.split(":")[2];
    }

    public static boolean isValidMsgString(String strMsg){
        return strMsg != null && strMsg.matches("^[^:]+:[^:]+:[^:]+$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(from, message.from) && Objects.equals(to, message.to) && Objects.equals(content, message.content);
    }

    @Override
    public String toString() {
        return from + ":" + to + ":" + content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, content);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }
}
