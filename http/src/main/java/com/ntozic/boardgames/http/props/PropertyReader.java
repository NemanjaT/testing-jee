package com.ntozic.boardgames.http.props;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class PropertyReader {
    private Properties prop = new Properties();

    public PropertyReader() {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String string(String property) {
        return string(property, null);
    }

    public String string(String property, String fallback) {
        return Optional.ofNullable(prop.getProperty(property))
                .orElse(fallback);
    }

    public Integer integer(String property) {
        return integer(property, null);
    }

    public Integer integer(String property, Integer fallback) {
        return Optional.ofNullable(prop.getProperty(property))
                .flatMap(val -> Optional.of(Integer.valueOf(val)))
                .orElse(fallback);
    }
}
