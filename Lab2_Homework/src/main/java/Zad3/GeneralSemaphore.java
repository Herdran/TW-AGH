package Lab2_Homework.Zad3;

import Lab2_Homework.Zad1_2.BinSemaphoreWhile;
import Lab2_Homework.Zad1_2.Semaphore;

class GeneralSemaphore implements Semaphore {
    private int freeResources;
    private final BinSemaphoreWhile accessToValue = new BinSemaphoreWhile();
    private final BinSemaphoreWhile accessToResource = new BinSemaphoreWhile();

    public GeneralSemaphore(int freeResources) {
        if (freeResources <= 0) {
            this.freeResources = 1;
        } else {
            this.freeResources = freeResources;
        }
    }

    @Override
    public void P() {
        accessToResource.P();
        accessToValue.P();
        freeResources--;

        if (freeResources > 0) {
            accessToResource.V();
        }

        accessToValue.V();
    }

    @Override
    public void V() {
        accessToValue.P();

        freeResources++;

        accessToResource.V();

        accessToValue.V();
    }
}