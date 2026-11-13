package view;

import java.util.List;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;
import tasks.RobotTask;

public class Dashboard implements Runnable {
    // ATRIBUTS
    private CentralSoldadura centralSoldadura;
    private CintaTransportadora cintaTransportadora;
    private SupervisorCentral supervisorCentral;
    private List<RobotTask> llistat;

    // CONSTRUCTOR
    public Dashboard(CentralSoldadura centralSoldadura, CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral, List<RobotTask> llistat) {
        this.centralSoldadura = centralSoldadura;
        this.cintaTransportadora = cintaTransportadora;
        this.supervisorCentral = supervisorCentral;
        this.llistat = llistat;
    }

    // FUNCIONS
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                boolean alerta = supervisorCentral.getAlerta();
                int cotxosFets = centralSoldadura.getCotxosFets();
                int bateriesMax = cintaTransportadora.getMaximBateries();
                int bateries = cintaTransportadora.getQuantitatBateries();

                StringBuilder analisiActual = new StringBuilder();
                analisiActual.append("======= TICK =======\n");
                analisiActual.append("Estat d'alerta: " + alerta + "\n");
                analisiActual.append("Estacions actuals: "+formatarEstacions());
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

    public StringBuilder formatarEstacions() {
        StringBuilder estacionsString = new StringBuilder();
        int[] estacions = centralSoldadura.getEstacions();

        estacionsString.append("[");
        for (int i = 0; i < estacions.length; i++) {
            if (i != 0) {
                estacionsString.append(", ");
            }
            switch (estacions[i]) {
                case 0:
                    estacionsString.append("-");
                    break;
                case -1:
                    estacionsString.append("X");
                    break;
                default:
                    estacionsString.append("R" + estacions[i]);
                    break;
            }
        }
        estacionsString.append("]\n");
        return estacionsString;
    }
}
