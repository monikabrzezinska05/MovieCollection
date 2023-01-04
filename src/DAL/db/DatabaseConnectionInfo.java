package DAL.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnectionInfo {

    private final Properties properties;

    public DatabaseConnectionInfo(String path) {
        properties = new Properties();

        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            System.out.println("Could not load properties file: " + path);
            System.exit(-1);
        }
    }

    public String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

}