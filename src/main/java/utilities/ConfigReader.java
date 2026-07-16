package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Config file not found or unreadable", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
