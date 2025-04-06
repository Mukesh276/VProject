package com.daas.demo.dassDemo.utility;

public class RequestContextHolder {
    private static final ThreadLocal<Long> subIdHolder = new ThreadLocal<>();

    public static void setSubId(Long subId) {
        subIdHolder.set(subId);
    }

    public static Long getSubId() {
        return subIdHolder.get();
    }

    public static void clear() {
        subIdHolder.remove();
    }
}
