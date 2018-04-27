package org.power.configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

public class PropertiesFileConfigurationTest {

    private long time = 12345678900L;

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
    }

    @Test
    public void testGetValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("name")).isEqualTo("power");
    }

    @Test
    public void testGetValueWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("name", "test")).isEqualTo("power");
    }

    @Test
    public void testGetIntValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("id")).isEqualTo(1);
    }

    @Test
    public void testGetIntWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("id", 5)).isEqualTo(1);
    }

    @Test
    public void testGetLongValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getLong("time")).isEqualTo(time);
    }

    @Test
    public void testGetLongWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getLong("time", 5L)).isEqualTo(time);
    }

    @Test
    public void testGetDoubleValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getDouble("pi")).isEqualTo(Math.PI);
    }

    @Test
    public void testGetDoubleWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getDouble("pi", 3.14)).isEqualTo(Math.PI);
    }

    @Test
    public void testGetBooleanValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getBoolean("debug")).isTrue();
    }

    @Test
    public void testGetBooleanWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getBoolean("debug", false)).isTrue();
    }

    @Test
    public void testGetNonExist() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("not_exist")).isNull();
    }

    @Test
    public void testGetNonExistWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("not_exist", "test")).isEqualTo("test");
    }

    @Test
    public void testGetIntNonExist() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("not_exist")).isNull();
    }

    @Test
    public void testGetIntNonExistWithDefaultValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("not_exist", 5)).isEqualTo(5);
    }

    @Test
    public void testSetValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("not_exist")).isNull();

        assertThat(conf.set("not_exist", "test")).isNull();

        assertThat(conf.get("not_exist")).isEqualTo("test");
    }

    @Test
    public void testSetIntegerValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("not_exist")).isNull();

        assertThat(conf.set("not_exist", 1)).isNull();

        assertThat(conf.getInt("not_exist")).isEqualTo(1);
    }

    @Test
    public void testSetLongValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getLong("not_exist")).isNull();

        assertThat(conf.set("not_exist", 1L)).isNull();

        assertThat(conf.getLong("not_exist")).isEqualTo(1L);
    }

    @Test
    public void testSetDoubleValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getDouble("not_exist")).isNull();

        assertThat(conf.set("not_exist", 2.1e50)).isNull();

        assertThat(conf.getDouble("not_exist")).isEqualTo(2.1e50);
    }

    @Test
    public void testSetBooleanValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getBoolean("not_exist")).isNull();

        assertThat(conf.set("not_exist", true)).isNull();

        assertThat(conf.getBoolean("not_exist")).isTrue();
    }

    @Test
    public void testUpdateValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("name")).isEqualTo("power");

        conf.set("name", "test");

        assertThat(conf.get("name")).isEqualTo("test");
    }

    @Test
    public void testUpdateIntegerValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getInt("id")).isEqualTo(1);

        conf.set("id", 5);

        assertThat(conf.getInt("id")).isEqualTo(5);
    }

    @Test
    public void testUpdateLongValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getLong("time")).isEqualTo(time);

        conf.set("time", 1L);

        assertThat(conf.getLong("id")).isEqualTo(1L);
    }

    @Test
    public void testUpdateBooleanValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getBoolean("debug")).isTrue();

        conf.set("debug", false);

        assertThat(conf.getBoolean("debug")).isFalse();
    }

    @Test
    public void testSaveValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.get("name")).isEqualTo("power");

        assertThat(conf.save("name", "test")).isEqualTo("power");

        assertThat(conf.get("name")).isEqualTo("test");
    }

    @Test
    public void testSaveIntegerValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        assertThat(conf.save("id", 5)).isEqualTo(1);

        assertThat(conf.getInt("id")).isEqualTo(5);
    }

    @Test
    public void testSaveLongValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        assertThat(conf.save("time", 1L)).isEqualTo(time);

        assertThat(conf.getLong("time")).isEqualTo(1L);
    }

    @Test
    public void testSaveDoubleValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        assertThat(conf.save("pi", 3.14)).isEqualTo(Math.PI);

        assertThat(conf.getDouble("pi")).isEqualTo(3.14);
    }

    @Test
    public void testSaveBooleanValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        assertThat(conf.save("debug", false)).isTrue();

        assertThat(conf.getBoolean("debug")).isFalse();
    }

    @Test
    public void testAddDoubleValue() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);
        assertThat(conf.getDouble("not_exist")).isNull();

        assertThat(conf.save("not_exist", 2.1e50)).isNull();

        assertThat(conf.getDouble("not_exist")).isEqualTo(2.1e50);
        assertThat(conf.get("not_exist")).isEqualToIgnoringCase("2.1e50");
    }

    @Test
    public void testSaveConfFile() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        conf.set("name", "test");
        conf.save();

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        }

        assertThat(properties.getProperty("name")).isEqualTo("test");
    }

    @Test
    public void testReloadConfFile() throws Exception {
        String file = this.getClass().getResource("/app.properties").getFile();
        Configuration conf = new PropertiesFileConfiguration(file);

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        }

        properties.setProperty("name", "test");
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "");
        }

        assertThat(properties.getProperty("name")).isEqualTo("test");

        assertThat(conf.get("name")).isEqualTo("power");

        conf.reload();

        assertThat(conf.get("name")).isEqualTo("test");
    }

}
