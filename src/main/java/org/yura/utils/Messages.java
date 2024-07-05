package org.yura.utils;

public class Messages {
    public static String reverseMsg(String msg){
        String from = msg.split(":")[0];
        String to = msg.split(":")[1];
        String text = msg.split(":")[2];

        return to + ":" + from + ":" + text;
    }
}
