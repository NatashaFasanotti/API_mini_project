package com.sparta.smern;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ApiConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ApiConfig.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Unable to find config.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUri() {
        return properties.getProperty("api_url");
    }

    public static String getToken() {
        return properties.getProperty("api_token");
    }

    public static String getCommonBasePath() {
        return properties.getProperty("common_base_path");
    }


}
