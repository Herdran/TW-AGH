package Lab3_Homework.Java;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PhilosopherStarving extends Philosopher {
    public PhilosopherStarving(int philosopherId, int iterations, Semaphore forkLeft, Semaphore forkRight) {
        super(philosopherId, iterations, forkLeft, forkRight);
    }

    @Override
    void eat() throws InterruptedException {
        long waitStart = System.currentTimeMillis();

        forkLeft.acquire();
        boolean acquireBoolean = forkRight.tryAcquire();

        waitTime += System.currentTimeMillis() - waitStart;

        if (acquireBoolean) {
            eatingCount++;
            Thread.sleep(new Random().nextInt(10));
            forkRight.release();
        }
        forkLeft.release();
    }
}
