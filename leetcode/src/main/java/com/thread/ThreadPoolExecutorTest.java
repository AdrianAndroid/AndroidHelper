package com.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorTest {
    
    
    
    
    void test() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.shutdown();
        executorService.shutdownNow();
    }
    
    
    
    
}
