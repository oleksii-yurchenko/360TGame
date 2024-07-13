package org.yura.utils;

/**
 * The {@code Messages} class provides utility methods for processing and validating chat messages.
 */
public class Message {

    private String text;
    private String to;
    private String from;


    public Message(String text, String to, String from) {
        this.text = text;
        this.to = to;
        this.from = from;
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





    public static String reverseMsg(String msg){
        String from = msg.split(":")[0];
        String to = msg.split(":")[1];
        String text = msg.split(":")[2];

        return to + ":" + from + ":" + text;
    }





    public static Boolean isValidMsg(String msg){
        return msg != null && msg.matches("^[^:]+:[^:]+:[^:]+$");
    }
}
