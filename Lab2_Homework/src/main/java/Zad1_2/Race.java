package Lab2_Homework.Zad1_2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Race {
    private long counter = 0;
    private final Semaphore semaphore;

    public Race(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    private void increase() {
        semaphore.P();
        counter++;
        semaphore.V();
    }

    private void decrease() {
        semaphore.P();
        counter--;
        semaphore.V();
    }

    public long getCounter() {
        return counter;
    }

    void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void start(final int threadsCount, final long iterCount) {
        List<Thread> incThreads = Stream.generate(() -> new Thread(() -> {
            for (int i = 0; i < iterCount; i++) {
                increase();
            }
        })).limit(threadsCount).collect(Collectors.toCollection(ArrayList::new));

        List<Thread> decThreads = Stream.generate(() -> new Thread(() -> {
            for (int i = 0; i < iterCount; i++) {
                decrease();
            }
        })).limit(threadsCount).collect(Collectors.toCollection(ArrayList::new));


        incThreads.forEach(Thread::start);
        decThreads.forEach(Thread::start);

        incThreads.forEach(this::joinThread);
        decThreads.forEach(this::joinThread);
    }
}