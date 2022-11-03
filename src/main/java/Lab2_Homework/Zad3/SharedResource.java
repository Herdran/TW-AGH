package Lab2_Homework.Zad3;


import java.util.Random;

class SharedResource {
    private final GeneralSemaphore semaphore;

    SharedResource(int k) {
        semaphore = new GeneralSemaphore(k);
    }

    public void performActivity() {
        semaphore.P();

        System.out.printf("Thread %d accesses the resource\n", Thread.currentThread().getId());

        try {
            Thread.sleep(new Random().nextInt(1, 100)); // Random sleep simulating activity
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("Thread %d releases the resource\n", Thread.currentThread().getId());

        semaphore.V();
    }
}