package org.power.configuration.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.junit.Before;

public class PropertiesXmlFileConfigurationTest extends PropertiesFileConfigurationTest {

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("name", "power");
        properties.setProperty("id", "1");
        properties.setProperty("time", Long.toString(time));
        properties.setProperty("pi", Double.toString(Math.PI));
        properties.setProperty("debug", "true");
        String file = this.getClass().getResource("/").getFile() + "app.xml";
        try (OutputStream out = new FileOutputStream(file)) {
            properties.storeToXML(out, "");
        }

        conf = new PropertiesXmlFileConfiguration(file);
    }

    protected Properties loadProperties() throws IOException {
        String file = this.getClass().getResource("/app.xml").getFile();
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(file)) {
            properties.loadFromXML(is);
        }
        return properties;
    }

    protected void saveProperties(Properties properties) throws IOException {
        String file = this.getClass().getResource("/app.xml").getFile();
        try (OutputStream out = new FileOutputStream(file)) {
            properties.storeToXML(out, "");
        }
    }

}