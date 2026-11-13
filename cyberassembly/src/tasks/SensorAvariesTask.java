package tasks;

import controller.CentralSoldadura;

public class SensorAvariesTask implements Runnable {
    private CentralSoldadura centralSoldadura;

    public SensorAvariesTask(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(2000);
                centralSoldadura.rompreEstacio();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
