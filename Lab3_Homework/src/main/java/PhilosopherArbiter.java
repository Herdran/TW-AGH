package Lab3_Homework.Java;


import java.util.Random;
import java.util.concurrent.Semaphore;

public class PhilosopherArbiter extends Philosopher {
    Semaphore arbiter;

    public PhilosopherArbiter(int philosopherId, int iterations, Semaphore forkLeft, Semaphore forkRight, Semaphore arbiter) {
        super(philosopherId, iterations, forkLeft, forkRight);
        this.arbiter = arbiter;
    }

    @Override
    void eat() throws InterruptedException {
        long waitStart = System.currentTimeMillis();

        arbiter.acquire();
        forkLeft.acquire();
        forkRight.acquire();

        waitTime += System.currentTimeMillis() - waitStart;

        eatingCount++;
        Thread.sleep(new Random().nextInt(10));

        forkRight.release();
        forkLeft.release();
        arbiter.release();
    }
}
