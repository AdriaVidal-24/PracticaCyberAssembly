import java.util.ArrayList;
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

        supervisorTaskThread.join();
        for (int i = 0; i < robots.size(); i++) {
            robots.get(i).join();
            agvs.get(i).join();
        }
    }
}
