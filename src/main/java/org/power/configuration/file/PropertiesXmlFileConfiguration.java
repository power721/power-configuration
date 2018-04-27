package org.power.configuration.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesXmlFileConfiguration extends PropertiesFileConfiguration {

    public PropertiesXmlFileConfiguration(String pathname) throws IOException {
        this(new File(pathname));
    }

    public PropertiesXmlFileConfiguration(File file) throws IOException {
        super(file);
    }

    @Override
    protected void init() throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.loadFromXML(is);
        }
    }

    @Override
    public void reload() throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.loadFromXML(is);
        }
    }

    @Override
    protected void store(String comment) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            properties.storeToXML(out, comment);
        }
    }

}
