package tasks;

import java.util.Random;

import controller.CentralSoldadura;

public class SensorAvariesTask implements Runnable {
    // ATRIBUTS
    private CentralSoldadura centralSoldadura;
    private Random rand = new Random();

    // CONSTRUCTOR
    public SensorAvariesTask(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
    }

    // FUNCIONS
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(2000 + rand.nextInt(3000));
                centralSoldadura.rompreEstacio();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
