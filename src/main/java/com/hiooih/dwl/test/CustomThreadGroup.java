package com.hiooih.dwl.test;

/**
 * @author duwenlei
 **/
public class CustomThreadGroup extends ThreadGroup {
    public CustomThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("Thread " + thread.getName() + " died, exception was: ");
        throwable.printStackTrace();
    }
}
