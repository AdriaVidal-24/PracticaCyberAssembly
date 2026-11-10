package tasks;

import controller.CintaTransportadora;

public class AGVTask implements Runnable {
    private CintaTransportadora cintaTransportadora;
    private int id;
    private int comptadorBateries = 0;

    public AGVTask(CintaTransportadora cintaTransportadora, int id) {
        this.cintaTransportadora = cintaTransportadora;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                cintaTransportadora.afegirBateria(id, comptadorBateries);
                comptadorBateries++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }
    }

}
