package Lab2_Homework.Zad3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralSemaphoreTest {
    SharedResource sharedResource;

    public GeneralSemaphoreTest(int threadCount, int resourcesCount) {
        sharedResource = new SharedResource(resourcesCount);

        List<Thread> threads = Stream.generate(() -> new Thread(() -> sharedResource.performActivity())).limit(threadCount).collect(Collectors.toCollection(ArrayList::new));

        threads.forEach(Thread::start);
        threads.forEach(this::joinThread);
    }

    void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}