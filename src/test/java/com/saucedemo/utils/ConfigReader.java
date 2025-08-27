package com.saucedemo.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties props;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        props = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(props.getProperty("implicit.wait"));
    }

    public static String getBrowser() {
        return props.getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(props.getProperty("headless"));
    }
}
