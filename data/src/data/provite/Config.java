package data.provite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties config = new Properties();

    /**
     * Load/Read values of config file
     */
    public Config() {
        // Determine config file
        String configFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);

        try {
            if (inputStream != null) {
                // Load/Read contents of config file
                this.config.load(inputStream);

                // Make sure input stream is close
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a map list containing the values of the config
     *
     * @return Map with config values
     */
    public final String getValue(String key) {
        String value = null;

        // Add config values to Map
        for (String configKey : this.config.stringPropertyNames()) {
            if (configKey.equals(key)) {
                value = this.config.getProperty(key);
            }
        }
        return value;
    }
}
