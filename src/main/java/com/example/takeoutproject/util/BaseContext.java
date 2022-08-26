package com.example.takeoutproject.util;

/**
 * util base on threadlocal, user save and get current user id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * set current user id
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * get current user id
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
