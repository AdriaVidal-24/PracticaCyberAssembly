package tasks;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;

public class RobotTask implements Runnable {
    private int id;
    private CentralSoldadura centralSoldadura;
    private CintaTransportadora cintaTransportadora;
    private SupervisorCentral supervisorCentral;

    public RobotTask(int id, CentralSoldadura centralSoldadura, CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral) {
        this.id = id;
        this.centralSoldadura = centralSoldadura;
        this.cintaTransportadora = cintaTransportadora;
        this.supervisorCentral = supervisorCentral;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                supervisorCentral.verificarEstat(id);
                String bateria = cintaTransportadora.retirarBateria(id);
                int numEstacio = centralSoldadura.accedirCentral(id);
                Thread.sleep(1000);
                centralSoldadura.lliurarCentral(id, numEstacio);
                centralSoldadura.registreCentral(id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }
    }

}
