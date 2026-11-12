package tasks;

import controller.CintaTransportadora;

public class AGVTask implements Runnable {
    private CintaTransportadora cintaTransportadora;

    public AGVTask(CintaTransportadora cintaTransportadora) {
        this.cintaTransportadora = cintaTransportadora;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                cintaTransportadora.afegirBateria();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
