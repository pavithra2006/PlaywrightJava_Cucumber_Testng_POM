package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = load();

    private ConfigReader() {}

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Missing config/config.properties");
            }
            properties.load(input);
            String environment = System.getProperty("env", properties.getProperty("environment", "demo"));
            try (InputStream environmentInput = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config/config-" + environment + ".properties")) {
                if (environmentInput != null) {
                    properties.load(environmentInput);
                }
            }
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load framework configuration", exception);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, PROPERTIES.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
