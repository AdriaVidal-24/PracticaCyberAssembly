package tasks;

import java.util.Random;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;

public class RobotTask implements Runnable {
    // ATRIBUTS
    private int id;
    private CentralSoldadura centralSoldadura;
    private CintaTransportadora cintaTransportadora;
    private SupervisorCentral supervisorCentral;
    private Random rand = new Random();
    private volatile String estat = "INICIANT";
    private int cotxosFetsRobot = 0;
    private int percentatgeBateria = 0;

    // CONSTRUCTOR
    public RobotTask(int id, CentralSoldadura centralSoldadura, CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral) {
        this.id = id;
        this.centralSoldadura = centralSoldadura;
        this.cintaTransportadora = cintaTransportadora;
        this.supervisorCentral = supervisorCentral;
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public String getEstat() {
        return estat;
    }

    public int getPercentatgeBateria() {
        return percentatgeBateria;
    }

    public int getCotxosFetsRobot() {
        return cotxosFetsRobot;
    }

    // FUNCIONS
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (supervisorCentral.getAlerta()) {
                    estat = "PAUSAT";
                }
                supervisorCentral.verificarEstat(id);
                estat = "ESPERANT_BATERIA";
                if (percentatgeBateria <= 20) {
                    cintaTransportadora.retirarBateria();
                    percentatgeBateria = 100;
                }
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
                cotxosFetsRobot++;
                percentatgeBateria -= rand.nextInt(15) + 5;
            }
        } catch (InterruptedException e) {
            estat = "ATURAT";
            Thread.currentThread().interrupt();
        }
    }
}
