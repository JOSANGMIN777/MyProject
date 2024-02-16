package com.ssafy.exhale.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {

    private static final String BUNDLE_NAME = "messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());

    public static String getMes(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (Exception e) {
            return "[ " + key + "] 없음";
        }
    }
}
