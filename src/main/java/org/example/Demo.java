package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo {
    static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS,
                    new ArrayBlockingQueueRyan<>(1));

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            CountDownLatch c = new CountDownLatch(2);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ignore) {
//            }
            for (int n = 0; n < 2; n++) {
                threadPoolExecutor.execute(() -> {
                    try {
//                         do something
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {
                    } finally {
                        c.countDown();
                    }
                });
            }
            try {
                c.await();
            } catch (InterruptedException ignore) {
            }
            System.err.println("============== task done, batch " + i + " ==============");
        }
        System.err.println("============== all done ==============");
    }
}
