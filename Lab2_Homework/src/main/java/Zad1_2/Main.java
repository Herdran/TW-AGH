package Lab2_Homework.Zad1_2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int testCount = 25;
        final int threadCount = 10;
        final int iterCount = 100000;

        List<Long> resultsBinIf = new ArrayList<>();
        List<Long> resultsBinWhile = new ArrayList<>();

        System.out.print("Simulating \"race\" using binary semaphores with if and while:\n");

        for (int i = 0; i < testCount; i++) {
            Race raceBinIf;
            Race raceBinWhile;

            raceBinIf = new Race(new BinSemaphoreIf());
            raceBinWhile = new Race(new BinSemaphoreWhile());

            raceBinIf.start(threadCount, iterCount);
            raceBinWhile.start(threadCount, iterCount);

            resultsBinIf.add(raceBinIf.getCounter());
            resultsBinWhile.add(raceBinWhile.getCounter());
        }

        System.out.print("Final counter values for binary semaphore using if:\n");
        System.out.println(resultsBinIf);
        System.out.print("Final counter values for binary semaphore using while:\n");
        System.out.println(resultsBinWhile);
    }
}