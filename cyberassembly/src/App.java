import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;
import tasks.AGVTask;
import tasks.RobotTask;
import tasks.SensorAvariesTask;
import tasks.SupervisorTask;
import tasks.TecnicManteniment;
import view.Dashboard;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        int quantCentrals = llegirEnter(scanner, "Quantes centrals de soldadura es volen?");
        int maximBateries = llegirEnter(scanner, "Quina capacitat de bateries té la cinta?");
        int quantRobots = llegirEnter(scanner, "Quants robots es volen a la vegada?");
        int quantAGVs = llegirEnter(scanner, "Quants AGVs es volen a la vegada?");

        scanner.close();
        CentralSoldadura centralSoldadura = new CentralSoldadura(quantCentrals);
        CintaTransportadora cintaTransportadora = new CintaTransportadora(maximBateries);
        SupervisorCentral supervisorCentral = new SupervisorCentral();
        SupervisorTask supervisorTask = new SupervisorTask(supervisorCentral);
        TecnicManteniment tecnicManteniment = new TecnicManteniment(centralSoldadura);
        SensorAvariesTask sensorAvariesTask = new SensorAvariesTask(centralSoldadura);
        int duracioPrograma = 20000;
        
        List<RobotTask> robotTasks = new ArrayList<>();
        for (int i = 1; i <= quantRobots; i++) {
            robotTasks.add(new RobotTask(i, centralSoldadura, cintaTransportadora, supervisorCentral));
        }
        
        List<Thread> robots = new ArrayList<>();
        for (int i = 1; i <= quantRobots; i++) {
            robots.add(new Thread(robotTasks.get(i - 1)));
        }
        
        List<Thread> agvs = new ArrayList<>();
        for (int i = 0; i < quantAGVs; i++) {
            agvs.add(new Thread(new AGVTask(cintaTransportadora)));
        }
        
        Dashboard dashboard = new Dashboard(centralSoldadura, cintaTransportadora, supervisorCentral, robotTasks);
        
        Thread sensorAvariesThread = new Thread(sensorAvariesTask);
        Thread tecnicMantenimentThread = new Thread(tecnicManteniment);
        Thread supervisorTaskThread = new Thread(supervisorTask);
        Thread dashboardThread = new Thread(dashboard);
        dashboardThread.setDaemon(true);
        sensorAvariesThread.setDaemon(true);

        sensorAvariesThread.start();
        tecnicMantenimentThread.start();
        dashboardThread.start();
        supervisorTaskThread.start();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).start();
        }
        for (int i = 0; i < agvs.size(); i++) {
            agvs.get(i).start();
        }

        Thread.sleep(duracioPrograma);
        dashboardThread.interrupt();
        tecnicMantenimentThread.interrupt();
        supervisorTaskThread.interrupt();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).interrupt();
        }
        for (int i = 0; i < agvs.size(); i++) {
            agvs.get(i).interrupt();
        }

        supervisorTaskThread.join();
        tecnicMantenimentThread.join();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).join();
        }
        for (int i = 0; i < agvs.size(); i++) {
            agvs.get(i).join();
        }

        StringBuilder resum = new StringBuilder();
        resum.append("\n======= SIMULACIÓ FINALITZADA =======\n");
        resum.append("Durada: " + (duracioPrograma / 1000) + " s\n");
        resum.append("Cotxos fets: " + centralSoldadura.getCotxosFets() + "\n");
        resum.append("Estacions finals: " + Arrays.toString(centralSoldadura.getEstacions()) + "\n");
        resum.append("Bateries a la cinta: " + cintaTransportadora.getQuantitatBateries()
                + "/" + cintaTransportadora.getMaximBateries() + "\n");
        for (RobotTask r : robotTasks) {
            resum.append("Robot " + r.getId() + ": " + r.getEstat() + "\n");
        }
        System.out.println(resum);
    }

    public static int llegirEnter(Scanner scanner, String pregunta) {
        int resposta = -1;
        while (resposta <= 0) {
            System.out.println(pregunta+" Ha de ser un nombre major que 0.");
            if(scanner.hasNextInt()) {
                resposta = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        return resposta;
    }
}
