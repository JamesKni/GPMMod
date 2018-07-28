package com.cannonmc.gpmm.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MusicModThreadFactory implements ThreadFactory{
	
private final AtomicInteger threadNumber;
    
    public MusicModThreadFactory() {
        this.threadNumber = new AtomicInteger(1);
    }
    
    @Override
    public Thread newThread(final Runnable r) {
        return new Thread(r, "gpmm" + this.threadNumber.getAndIncrement());
    }

}
