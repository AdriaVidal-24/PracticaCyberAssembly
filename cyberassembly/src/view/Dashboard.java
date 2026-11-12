package view;

import java.util.Arrays;
import java.util.List;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;
import tasks.RobotTask;

public class Dashboard implements Runnable {
    private CentralSoldadura centralSoldadura;
    private CintaTransportadora cintaTransportadora;
    private SupervisorCentral supervisorCentral;
    private List<RobotTask> llistat;

    public Dashboard(CentralSoldadura centralSoldadura, CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral, List<RobotTask> llistat) {
        this.centralSoldadura = centralSoldadura;
        this.cintaTransportadora = cintaTransportadora;
        this.supervisorCentral = supervisorCentral;
        this.llistat = llistat;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                boolean alerta = supervisorCentral.getAlerta();
                int[] estacions = centralSoldadura.getEstacions();
                int cotxosFets = centralSoldadura.getCotxosFets();
                int bateriesMax = cintaTransportadora.getMaximBateries();
                int bateries = cintaTransportadora.getQuantitatBateries();

                StringBuilder analisiActual = new StringBuilder();
                analisiActual.append("======= TICK =======\n");
                analisiActual.append("Estat d'alerta: " + alerta + "\n");
                analisiActual.append("Estacions actuals: " + Arrays.toString(estacions) + "\n");
                analisiActual.append("Cotxos fets: " + cotxosFets + "\n");
                analisiActual.append("Quantitat de bateries: " + bateries + "\n");
                analisiActual.append("Màxim de bateries: " + bateriesMax + "\n");
                for (RobotTask r : llistat) {
                    analisiActual.append("Robot " + r.getId() + " estat: " + r.getEstat() + "\n");
                }
                System.out.println(analisiActual);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
