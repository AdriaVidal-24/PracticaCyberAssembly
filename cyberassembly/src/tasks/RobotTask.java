package tasks;

import controller.CentralSoldadura;

public class RobotTask implements Runnable {
    CentralSoldadura centralSoldadura;

    public RobotTask(CentralSoldadura sC) {
        this.centralSoldadura = sC;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //Agafar bateria
            try {
                centralSoldadura.accedirCentral();
                System.out.println("Robot accedeix a central de Soldadura");
                Thread.sleep(1000);
                System.out.println("Robot lliura la central de Soldadura");
                centralSoldadura.lliurarCentral();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
