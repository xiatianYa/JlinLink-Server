package com.jinlink.core.holder;

/**
 * 上下文持有者
 */
public class ContextHolder {

    private ContextHolder() {

    }

    /**
     * 请求语言
     */
    private static final ThreadLocal<String> REQUEST_LANGUAGE = new ThreadLocal<>();

    public static String language() {
        return REQUEST_LANGUAGE.get();
    }

    public static void setLanguage(String value) {
        REQUEST_LANGUAGE.set(value);
    }

    public static void removeLanguage() {
        REQUEST_LANGUAGE.remove();
    }
}
