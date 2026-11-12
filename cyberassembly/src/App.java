import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;
import tasks.AGVTask;
import tasks.RobotTask;
import tasks.SupervisorTask;
import view.Dashboard;

public class App {
    public static void main(String[] args) throws InterruptedException {
        CentralSoldadura centralSoldadura = new CentralSoldadura(3);
        CintaTransportadora cintaTransportadora = new CintaTransportadora();
        SupervisorCentral supervisorCentral = new SupervisorCentral();
        SupervisorTask supervisorTask = new SupervisorTask(supervisorCentral);
        int duracioPrograma = 20000;

        List<RobotTask> robotTasks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            robotTasks.add(new RobotTask(i, centralSoldadura, cintaTransportadora, supervisorCentral));
        }
        List<Thread> robots = new ArrayList<>();
        List<Thread> agvs = new ArrayList<>();
        Thread supervisorTaskThread = new Thread(supervisorTask);
        for (int i = 1; i <= 10; i++) {
            robots.add(new Thread(robotTasks.get(i - 1)));
            agvs.add(new Thread(new AGVTask(cintaTransportadora)));
        }
        Dashboard dashboard = new Dashboard(centralSoldadura, cintaTransportadora, supervisorCentral, robotTasks);

        Thread dashboardThread = new Thread(dashboard);
        dashboardThread.setDaemon(true);
        dashboardThread.start();
        supervisorTaskThread.start();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).start();
            agvs.get(i).start();
        }

        Thread.sleep(duracioPrograma);
        dashboardThread.interrupt();
        supervisorTaskThread.interrupt();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).interrupt();
            agvs.get(i).interrupt();
        }

        supervisorTaskThread.join();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).join();
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
}
