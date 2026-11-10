package controller;

import java.util.concurrent.Semaphore;

public class SupervisorCentral {
    private Semaphore mutex = new Semaphore(1);

    public void accedirTaulerCentral() throws InterruptedException {
        mutex.acquire();
    }

    public void lliurarTaulerCentral() throws InterruptedException {
        mutex.release();
    }
}
