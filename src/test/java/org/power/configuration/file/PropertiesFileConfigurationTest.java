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
import org.power.configuration.Configuration;

public class PropertiesFileConfigurationTest {

    protected long time = 12345678900L;
    protected Configuration conf;

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
            properties.store(out, "");
        }

        conf = new PropertiesFileConfiguration(file);
    }

    @Test
    public void testGetValue() throws Exception {
        assertThat(conf.get("name")).isEqualTo("power");
    }

    @Test
    public void testGetValueWithDefaultValue() throws Exception {
        assertThat(conf.get("name", "test")).isEqualTo("power");
    }

    @Test
    public void testGetIntValue() throws Exception {
        assertThat(conf.getInt("id")).isEqualTo(1);
    }

    @Test
    public void testGetIntWithDefaultValue() throws Exception {
        assertThat(conf.getInt("id", 5)).isEqualTo(1);
    }

    @Test
    public void testGetLongValue() throws Exception {
        assertThat(conf.getLong("time")).isEqualTo(time);
    }

    @Test
    public void testGetLongWithDefaultValue() throws Exception {
        assertThat(conf.getLong("time", 5L)).isEqualTo(time);
    }

    @Test
    public void testGetDoubleValue() throws Exception {
        assertThat(conf.getDouble("pi")).isEqualTo(Math.PI);
    }

    @Test
    public void testGetDoubleWithDefaultValue() throws Exception {
        assertThat(conf.getDouble("pi", 3.14)).isEqualTo(Math.PI);
    }

    @Test
    public void testGetBooleanValue() throws Exception {
        assertThat(conf.getBoolean("debug")).isTrue();
    }

    @Test
    public void testGetBooleanWithDefaultValue() throws Exception {
        assertThat(conf.getBoolean("debug", false)).isTrue();
    }

    @Test
    public void testGetNonExist() throws Exception {
        assertThat(conf.get("not_exist")).isNull();
    }

    @Test
    public void testGetNonExistWithDefaultValue() throws Exception {
        assertThat(conf.get("not_exist", "test")).isEqualTo("test");
    }

    @Test
    public void testGetIntNonExist() throws Exception {
        assertThat(conf.getInt("not_exist")).isNull();
    }

    @Test
    public void testGetIntNonExistWithDefaultValue() throws Exception {
        assertThat(conf.getInt("not_exist", 5)).isEqualTo(5);
    }

    @Test
    public void testSetValue() throws Exception {
        assertThat(conf.get("not_exist")).isNull();

        assertThat(conf.set("not_exist", "test")).isNull();

        assertThat(conf.get("not_exist")).isEqualTo("test");
    }

    @Test
    public void testSetIntegerValue() throws Exception {
        assertThat(conf.getInt("not_exist")).isNull();

        assertThat(conf.set("not_exist", 1)).isNull();

        assertThat(conf.getInt("not_exist")).isEqualTo(1);
    }

    @Test
    public void testSetLongValue() throws Exception {
        assertThat(conf.getLong("not_exist")).isNull();

        assertThat(conf.set("not_exist", 1L)).isNull();

        assertThat(conf.getLong("not_exist")).isEqualTo(1L);
    }

    @Test
    public void testSetDoubleValue() throws Exception {
        assertThat(conf.getDouble("not_exist")).isNull();

        assertThat(conf.set("not_exist", 2.1e50)).isNull();

        assertThat(conf.getDouble("not_exist")).isEqualTo(2.1e50);
    }

    @Test
    public void testSetBooleanValue() throws Exception {
        assertThat(conf.getBoolean("not_exist")).isNull();

        assertThat(conf.set("not_exist", true)).isNull();

        assertThat(conf.getBoolean("not_exist")).isTrue();
    }

    @Test
    public void testUpdateValue() throws Exception {
        assertThat(conf.get("name")).isEqualTo("power");

        conf.set("name", "test");

        assertThat(conf.get("name")).isEqualTo("test");
    }

    @Test
    public void testUpdateIntegerValue() throws Exception {
        assertThat(conf.getInt("id")).isEqualTo(1);

        conf.set("id", 5);

        assertThat(conf.getInt("id")).isEqualTo(5);
    }

    @Test
    public void testUpdateLongValue() throws Exception {
        assertThat(conf.getLong("time")).isEqualTo(time);

        conf.set("time", 1L);

        assertThat(conf.getLong("id")).isEqualTo(1L);
    }

    @Test
    public void testUpdateBooleanValue() throws Exception {
        assertThat(conf.getBoolean("debug")).isTrue();

        conf.set("debug", false);

        assertThat(conf.getBoolean("debug")).isFalse();
    }

    @Test
    public void testSaveValue() throws Exception {
        assertThat(conf.get("name")).isEqualTo("power");

        assertThat(conf.save("name", "test")).isEqualTo("power");

        assertThat(conf.get("name")).isEqualTo("test");
    }

    @Test
    public void testSaveIntegerValue() throws Exception {
        assertThat(conf.save("id", 5)).isEqualTo(1);

        assertThat(conf.getInt("id")).isEqualTo(5);
    }

    @Test
    public void testSaveLongValue() throws Exception {
        assertThat(conf.save("time", 1L)).isEqualTo(time);

        assertThat(conf.getLong("time")).isEqualTo(1L);
    }

    @Test
    public void testSaveDoubleValue() throws Exception {
        assertThat(conf.save("pi", 3.14)).isEqualTo(Math.PI);

        assertThat(conf.getDouble("pi")).isEqualTo(3.14);
    }

    @Test
    public void testSaveBooleanValue() throws Exception {
        assertThat(conf.save("debug", false)).isTrue();

        assertThat(conf.getBoolean("debug")).isFalse();
    }

    @Test
    public void testAddDoubleValue() throws Exception {
        assertThat(conf.getDouble("not_exist")).isNull();

        assertThat(conf.save("not_exist", 2.1e50)).isNull();

        assertThat(conf.getDouble("not_exist")).isEqualTo(2.1e50);
        assertThat(conf.get("not_exist")).isEqualToIgnoringCase("2.1e50");
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
            properties.store(out, "");
        }
    }

}
