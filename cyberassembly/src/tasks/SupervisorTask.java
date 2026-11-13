package tasks;

import java.util.Random;

import controller.SupervisorCentral;

public class SupervisorTask implements Runnable {
    // ATRIBUTS
    private SupervisorCentral supervisorCentral;
    private Random rand = new Random();

    // CONSTRUCTOR
    public SupervisorTask(SupervisorCentral supervisorCentral) {
        this.supervisorCentral = supervisorCentral;
    }

    // FUNCIONS
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(rand.nextInt(2000) + 4000);
                    supervisorCentral.activarAlerta();
                    Thread.sleep(rand.nextInt(3000) + 2000);
                } finally {
                    supervisorCentral.resoldreAlerta();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
