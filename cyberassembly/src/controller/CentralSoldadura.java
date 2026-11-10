package controller;

import java.util.concurrent.Semaphore;

public class CentralSoldadura {
    private Semaphore sem = new Semaphore(3);

    public void accedirCentral() throws InterruptedException {
        sem.acquire();
    }

    public void lliurarCentral() {
        sem.release();
    }
}