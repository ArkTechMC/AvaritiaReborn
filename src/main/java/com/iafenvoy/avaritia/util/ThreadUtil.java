package com.iafenvoy.avaritia.util;

public class ThreadUtil {
    public static void execute(Runnable runnable, boolean sync) {
        if (sync) runnable.run();
        else new Thread(runnable).start();
    }
}
