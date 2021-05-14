package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

    private static PropertiesService instance;
    private final Properties prop = new Properties();

    private PropertiesService() {
        try (InputStream input = PropertiesService.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static PropertiesService getInstance() {
        if (instance == null) {
            instance = new PropertiesService();
        }
        return instance;
    }

    public Properties getProp() {
        return prop;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
