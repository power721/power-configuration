package org.power.configuration.file;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;

public class PreferencesXmlFileConfigurationTest extends FileConfigurationTest {

    @Before
    public void setUp() throws Exception {
        String dir = this.getClass().getResource("/").getFile();
        System.setProperty("java.util.prefs.userRoot", dir);

        Preferences preferences = Preferences.userRoot();
        preferences.clear();
        preferences.put("name", "power");
        preferences.put("id", "1");
        preferences.put("time", Long.toString(time));
        preferences.put("pi", Double.toString(Math.PI));
        preferences.put("debug", "true");

        Preferences node = preferences.node("org/power/conf");
        node.put("version", "2");

        String file = dir + "app_pref.xml";

        try (OutputStream os = new FileOutputStream(file)) {
            preferences.exportSubtree(os);
        }

        conf = new PreferencesXmlFileConfiguration(file);
    }

    @Test
    public void testSaveConfFile() throws Exception {
        conf.set("name", "test");
        conf.save();

        Preferences preferences = loadPreferences();
        assertThat(preferences.get("name", null)).isEqualTo("test");
    }

    @Test
    public void testSaveUTF8() throws Exception {
        conf.set("name", "中文");
        conf.save();

        Preferences preferences = loadPreferences();
        assertThat(preferences.get("name", null)).isEqualTo("中文");
    }

    @Test
    public void testReadFromNode() throws Exception {
        assertThat(conf.getInt("org.power.conf.version")).isEqualTo(2);
    }

    @Test
    public void testReadFromNodeSlah() throws Exception {
        assertThat(conf.getInt("org/power/conf/version")).isEqualTo(2);
    }

    @Test
    public void testDeleteFromNode() throws Exception {
        assertThat(conf.delete("org.power.conf.version", 2)).isTrue();
    }

    @Test
    public void testSetValueToNode() throws Exception {
        conf.set("org.power.conf.version", 3);
        assertThat(conf.getInt("org.power.conf.version")).isEqualTo(3);
    }

    @Test
    public void testSaveValueToNode() throws Exception {
        conf.save("org.power.conf.version", 3);
        assertThat(conf.getInt("org.power.conf.version")).isEqualTo(3);
    }

    @Test
    public void testReloadConfFile() throws Exception {
        String content = loadFileToString();
        content = content.replace("true", "false");
        saveStringToFile(content);

        assertThat(conf.getBoolean("debug")).isTrue();

        conf.reload();

        assertThat(conf.getBoolean("debug")).isFalse();
    }

    private Preferences loadPreferences() throws IOException, InvalidPreferencesFormatException {
        String file = this.getClass().getResource("/app_pref.xml").getFile();
        Preferences preferences = Preferences.userRoot();
        try (InputStream is = new FileInputStream(file)) {
            Preferences.importPreferences(is);
        }
        return preferences;
    }

    private String loadFileToString() throws IOException {
        String file = this.getClass().getResource("/app_pref.xml").getFile();
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[65536];
        int n = is.read(buffer);
        return new String(buffer, 0, n);
    }

    private void saveStringToFile(String content) throws IOException {
        String file = this.getClass().getResource("/app_pref.xml").getFile();
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(content.getBytes());
        }
    }

}
