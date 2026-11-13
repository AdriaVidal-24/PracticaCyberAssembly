package tasks;

import controller.CentralSoldadura;

public class TecnicManteniment implements Runnable {
    // ATRIBUTS
    private CentralSoldadura centralSoldadura;

    // CONSTRUCTOR
    public TecnicManteniment(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
    }

    // FUNCIONS
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
