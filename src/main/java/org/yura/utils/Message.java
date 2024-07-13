package org.yura.utils;

/**
 * The {@code Messages} class provides utility methods for processing and validating chat messages.
 */
public class Message {
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
