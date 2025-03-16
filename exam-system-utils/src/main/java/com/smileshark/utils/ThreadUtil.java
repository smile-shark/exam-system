package com.smileshark.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    public static ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(10);
    }
}
