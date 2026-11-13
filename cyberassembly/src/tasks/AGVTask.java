package tasks;

import controller.CintaTransportadora;

public class AGVTask implements Runnable {
    // ATRIBUTS
    private CintaTransportadora cintaTransportadora;

    // CONSTRUCTOR
    public AGVTask(CintaTransportadora cintaTransportadora) {
        this.cintaTransportadora = cintaTransportadora;
    }

    // FUNCIONS
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
