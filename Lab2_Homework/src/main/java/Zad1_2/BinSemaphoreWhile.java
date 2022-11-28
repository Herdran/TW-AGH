package Lab2_Homework.Zad1_2;

public class BinSemaphoreWhile implements Semaphore {
    private boolean state = true;
    private int awaits = 0;

    @Override
    public synchronized void P() {
        awaits++;
        while (!state) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = false;
        awaits--;
    }

    @Override
    public synchronized void V() {
        if (awaits > 0) {
            this.notify();
        }
        state = true;
    }
}
