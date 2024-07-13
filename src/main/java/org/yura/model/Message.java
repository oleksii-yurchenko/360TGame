package org.yura.model;

import java.util.Objects;

/**
 * The {@code Messages} class provides utility methods for processing and validating chat messages.
 */
public class Message {

    private String text;
    private String to;
    private String from;


    public Message(String from, String to, String text) {
        this.text = text;
        this.to = to;
        this.from = from;
    }

    public Message(String msg) {
        if (!Message.isValidMsg(msg)){
            throw new IllegalArgumentException("Message format is not correct");
        }
        this.from = msg.split(":")[0];
        this.to = msg.split(":")[1];
        this.text  = msg.split(":")[2];
    }


    @Override
    public String toString() {
        return from + ":" + to + ":" + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text) && Objects.equals(to, message.to) && Objects.equals(from, message.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, to, from);
    }

    public Message reverse() {
        return new Message(to, from, text);
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Boolean isValidMsg(String msg){
        return msg != null && msg.matches("^[^:]+:[^:]+:[^:]+$");
    }
}
