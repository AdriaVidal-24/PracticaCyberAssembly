package tasks;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;

public class RobotTask implements Runnable {
    private int id;
    private CentralSoldadura centralSoldadura;
    private CintaTransportadora cintaTransportadora;
    private SupervisorCentral supervisorCentral;
    private volatile String estat = "INICIANT";

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
                if (supervisorCentral.getAlerta()) {
                    estat = "PAUSAT";
                }
                supervisorCentral.verificarEstat(id);
                estat = "ESPERANT_BATERIA";
                cintaTransportadora.retirarBateria();
                estat = "ESPERANT_ESTACIÓ";
                int numEstacio = centralSoldadura.accedirCentral(id);
                try {
                    estat = "SOLDANT";
                    Thread.sleep(1000);
                } finally {
                    centralSoldadura.lliurarCentral(id, numEstacio);
                }
                estat = "REGISTRANT";
                centralSoldadura.registreCentral(id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public String getEstat() {
        return estat;
    }

}
