package ir.akhbar;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseThread extends Thread {

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private Queue<Runnable> queue = new LinkedList<>();

    @Override
    public void run() {
        isStarted.set(true);
        while (!queue.isEmpty()) {
            Runnable work = queue.poll();
            if (work != null) {
                work.run();
            }
        }
        isStarted.set(false);
    }

    public void addRunnable(Runnable runnable) {
        queue.offer(runnable);
        if (!isStarted.get()) {
            start();
        }
    }
}
