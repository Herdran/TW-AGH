package Lab2_Homework.Zad3;

public class Main {
    public static void main(String[] args) {
        final int threadCount = 10;
        final int resourcesCount = 4;

        System.out.print("Testing general semaphore:\n");

        new GeneralSemaphoreTest(threadCount, resourcesCount);
    }
}
