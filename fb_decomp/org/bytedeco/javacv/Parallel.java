/*
 * Decompiled with CFR 0.152.
 */
package org.bytedeco.javacv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static final String NUM_THREADS = "org.bytedeco.javacv.numthreads";

    public static int getNumThreads() {
        try {
            String s = System.getProperty(NUM_THREADS);
            if (s != null) {
                return Integer.valueOf(s);
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return Parallel.getNumCores();
    }

    public static void setNumThreads(int numThreads) {
        System.setProperty(NUM_THREADS, Integer.toString(numThreads));
    }

    public static int getNumCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void run(Runnable ... runnables) {
        if (runnables.length == 1) {
            runnables[0].run();
            return;
        }
        Future[] futures = new Future[runnables.length];
        for (int i = 0; i < runnables.length; ++i) {
            futures[i] = threadPool.submit(runnables[i]);
        }
        Throwable error = null;
        try {
            for (Future f : futures) {
                if (f.isDone()) continue;
                f.get();
            }
        }
        catch (Throwable t) {
            error = t;
        }
        if (error != null) {
            for (Future f : futures) {
                f.cancel(true);
            }
            throw new RuntimeException(error);
        }
    }

    public static void loop(int from, int to, Looper looper) {
        Parallel.loop(from, to, Parallel.getNumThreads(), looper);
    }

    public static void loop(int from, int to, int numThreads, final Looper looper) {
        int numLoopers = Math.min(to - from, numThreads > 0 ? numThreads : Parallel.getNumCores());
        Runnable[] runnables = new Runnable[numLoopers];
        for (int i = 0; i < numLoopers; ++i) {
            final int subFrom = (to - from) * i / numLoopers + from;
            final int subTo = (to - from) * (i + 1) / numLoopers + from;
            final int looperID = i;
            runnables[i] = new Runnable(){

                @Override
                public void run() {
                    looper.loop(subFrom, subTo, looperID);
                }
            };
        }
        Parallel.run(runnables);
    }

    public static interface Looper {
        public void loop(int var1, int var2, int var3);
    }
}

