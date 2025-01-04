package org.example.config;

import org.example.utils.Constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(Constant.CONFIG_PROPERTIES_PATH);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + Constant.CONFIG_PROPERTIES_PATH, e);
        }
    }

    public static String getAppiumServerUrl() {
        return properties.getProperty("appium.server.url");
    }

    public static boolean isVideoRecordingEnabled() {
        String value = properties.getProperty("video.recording", "false");
        return Boolean.parseBoolean(value);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}