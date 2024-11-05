package edu.augustana.RadioModel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public void startRepeatingTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public void stopRepeatingTask() {
        scheduler.shutdown();
    }
}
