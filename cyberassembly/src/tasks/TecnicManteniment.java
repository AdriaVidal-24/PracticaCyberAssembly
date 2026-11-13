package tasks;

import controller.CentralSoldadura;

public class TecnicManteniment implements Runnable {
    private CentralSoldadura centralSoldadura;

    public TecnicManteniment(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int estacio = centralSoldadura.esperarAvaria();
                Thread.sleep(3000);
                centralSoldadura.repararEstacio(estacio);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
