package Lab3_Homework.Java;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {

    int philosopherId;
    int iterations;
    Semaphore forkLeft;
    Semaphore forkRight;
    long waitTime = 0;
    int eatingCount = 0;

    public Philosopher(int philosopherId, int iterations, Semaphore forkLeft, Semaphore forkRight) {
        this.philosopherId = philosopherId;
        this.iterations = iterations;
        this.forkLeft = forkLeft;
        this.forkRight = forkRight;
    }

    @Override
    public void run() {
        while (eatingCount < iterations){
            think();
            try {
                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        for (int i = 0; i < iterations; i++) {
//            think();
//            try {
//                eat();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void getResult() {
        System.out.println("PHILOSOPHER ID: " + philosopherId + "\tAVG WAIT TIME: " + waitTime / (double) (iterations));
    }

    public static void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void think() {
        try {
            Thread.sleep(new Random().nextInt(100));  // More diverse and generally longer wait time for thinking
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void eat() throws InterruptedException {
    }
}
