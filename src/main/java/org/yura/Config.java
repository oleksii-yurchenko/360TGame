package org.yura;

import java.io.InputStream;
import java.util.Properties;

/**
 * The {@code Config} class is responsible for loading and providing access to configuration properties
 * from the {@code application.properties} file.
 */
public class Config {
    private final Properties properties = new Properties();

    public Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getMsgLimit() {
        return Integer.parseInt(properties.getProperty("msg_limit"));
    }

    public int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public String getStartMsg() {
        return properties.getProperty("start_msg");
    }

    public String getFirstPlayerName() {
        return properties.getProperty("first_player");
    }

    public String getSecondPlayerName() {
        return properties.getProperty("second_player");
    }

    public String getHost() { return properties.getProperty("host"); }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }
}
