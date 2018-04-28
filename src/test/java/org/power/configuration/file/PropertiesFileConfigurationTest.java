package org.power.configuration.file;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

public class PropertiesFileConfigurationTest extends FileConfigurationTest {

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("name", "power");
        properties.setProperty("id", "1");
        properties.setProperty("time", Long.toString(time));
        properties.setProperty("pi", Double.toString(Math.PI));
        properties.setProperty("debug", "true");
        String file = this.getClass().getResource("/").getFile() + "app.properties";
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "initialize");
        }

        conf = new PropertiesFileConfiguration(file);
    }

    @Test
    public void testSaveConfFile() throws Exception {
        conf.set("name", "test");
        conf.save();

        Properties properties = loadProperties();
        assertThat(properties.getProperty("name")).isEqualTo("test");
    }

    @Test
    public void testSaveUTF8() throws Exception {
        conf.set("name", "中文");
        conf.save();

        Properties properties = loadProperties();
        assertThat(properties.getProperty("name")).isEqualTo("中文");
    }

    @Test
    public void testReloadConfFile() throws Exception {

        Properties properties = loadProperties();

        properties.setProperty("name", "test");

        saveProperties(properties);

        assertThat(properties.getProperty("name")).isEqualTo("test");

        assertThat(conf.get("name")).isEqualTo("power");

        conf.reload();

        assertThat(conf.get("name")).isEqualTo("test");
    }

    protected Properties loadProperties() throws IOException {
        String file = this.getClass().getResource("/app.properties").getFile();
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        }
        return properties;
    }

    protected void saveProperties(Properties properties) throws IOException {
        String file = this.getClass().getResource("/app.properties").getFile();
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update");
        }
    }

}
