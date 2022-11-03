package Lab3_Homework.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static List<Semaphore> createForkSemaphores(int philosophersNumber) {
        return Stream.generate(() -> new Semaphore((1))).limit(philosophersNumber).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void runPhilosophersStarving(int philosophersNumber, int iterations) {
        List<Semaphore> forks = createForkSemaphores(philosophersNumber);
        List<Philosopher> philosophers = new ArrayList<>();

        for (int i = 0; i < philosophersNumber; i++) {
            philosophers.add(new PhilosopherStarving(i, iterations, forks.get(i), forks.get((i + 1) % philosophersNumber)));
        }

        philosophers.forEach(Philosopher::start);
        philosophers.forEach(Philosopher::joinThread);
        philosophers.forEach(Philosopher::getResult);
    }

    public static void runPhilosophersArbiter(int philosophersNumber, int iterations) {
        List<Semaphore> forks = createForkSemaphores(philosophersNumber);
        List<Philosopher> philosophers = new ArrayList<>();
        Semaphore arbiter = new Semaphore(philosophersNumber - 1);

        for (int i = 0; i < philosophersNumber; i++) {
            philosophers.add(new PhilosopherArbiter(i, iterations, forks.get(i), forks.get((i + 1) % philosophersNumber), arbiter));
        }

        philosophers.forEach(Philosopher::start);
        philosophers.forEach(Philosopher::joinThread);
        philosophers.forEach(Philosopher::getResult);
    }

    public static void main(String[] args) {
        for (String methodName : new String[]{"starving", "arbiter"}) {
            for (int philosophersNumber : new int[]{5, 10}) {
                for (int iterations : new int[]{50, 100}) {
                    System.out.println(methodName + "-" + philosophersNumber + "-" + iterations + "-java");
                    if (methodName.equals("starving")) {
                        runPhilosophersStarving(philosophersNumber, iterations);
                    } else {
                        runPhilosophersArbiter(philosophersNumber, iterations);
                    }
                    System.out.println();
                }
            }
        }
    }
}
