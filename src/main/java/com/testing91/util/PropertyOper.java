package com.testing91.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tesla on 16/6/17.
 */
public class PropertyOper {

    public static String getProperties(String key) {

        Properties properties = new Properties();
        InputStream in = PropertyOper.class.getResourceAsStream("/perf.properties");
        String p = "";
        try {
            properties.load(in);
            p = (String) properties.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

}
