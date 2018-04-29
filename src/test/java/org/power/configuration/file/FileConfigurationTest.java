package org.power.configuration.file;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.power.configuration.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileConfigurationTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected long time = 12345678900L;
    protected FileConfiguration conf;

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
    public void testDeleteKey() throws Exception {
        assertThat(conf.get("name")).isEqualTo("power");

        assertThat(conf.delete("name")).isTrue();

        assertThat(conf.get("name")).isNull();
    }

    @Test
    public void testDeleteValue() throws Exception {
        assertThat(conf.get("name")).isEqualTo("power");

        assertThat(conf.delete("name", "power")).isTrue();

        assertThat(conf.get("name")).isNull();
    }

    @Test
    public void testDeleteValueNotMatch() throws Exception {
        assertThat(conf.getInt("id")).isEqualTo(1);

        assertThat(conf.delete("id", 5)).isFalse();

        assertThat(conf.getInt("id")).isEqualTo(1);
    }

    @Test
    public void testDeleteDoubleValue() throws Exception {
        conf.set("test", 2.0E5);
        assertThat(conf.delete("test", 200000.0)).isTrue();

        assertThat(conf.get("test")).isNull();
    }

    @Test
    public void testDeleteBooleanValue() throws Exception {
        assertThat(conf.delete("debug", true)).isTrue();

        assertThat(conf.get("debug")).isNull();
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

    protected class MyObserver<ConfigurationEvent> implements Observer<ConfigurationEvent> {

        @Override
        public void onNext(ConfigurationEvent item) {
            logger.info(item.toString());
        }

        @Override
        public void onError(Throwable throwable) {
            logger.warn("", throwable);
        }

        @Override
        public void onComplete() {
            logger.info("complete");
        }

    }

}
