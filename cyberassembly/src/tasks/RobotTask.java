package tasks;

import controller.CentralSoldadura;
import controller.SupervisorCentral;

public class RobotTask implements Runnable {
    private CentralSoldadura centralSoldadura;
    private SupervisorCentral supervisorCentral;

    public RobotTask(CentralSoldadura centralSoldadura, SupervisorCentral supervisorCentral) {
        this.centralSoldadura = centralSoldadura;
        this.supervisorCentral = supervisorCentral;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // Agafar bateria
            try {
                centralSoldadura.accedirCentral();
                System.out.println("Robot accedeix a central de Soldadura");
                Thread.sleep(1000);
                System.out.println("Robot lliura la central de Soldadura");
                centralSoldadura.lliurarCentral();
                supervisorCentral.accedirTaulerCentral();
                Thread.sleep(500);
                System.out.println("Robot reb certificat de qualitat");
                supervisorCentral.lliurarTaulerCentral();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
