import java.util.ArrayList;
import java.util.List;

import controller.CentralSoldadura;
import controller.CintaTransportadora;
import controller.SupervisorCentral;
import tasks.AGVTask;
import tasks.RobotTask;
import tasks.SupervisorTask;

public class App {
    public static void main(String[] args) throws InterruptedException {
        CentralSoldadura centralSoldadura = new CentralSoldadura(3);
        CintaTransportadora cintaTransportadora = new CintaTransportadora();
        SupervisorCentral supervisorCentral = new SupervisorCentral();
        SupervisorTask supervisorTask = new SupervisorTask(supervisorCentral);

        List<Thread> robots = new ArrayList<>();
        List<Thread> agvs = new ArrayList<>();
        Thread supervisorTaskThread = new Thread(supervisorTask);
        for (int i = 1; i <= 10; i++) {
            robots.add(new Thread(new RobotTask(i, centralSoldadura, cintaTransportadora, supervisorCentral)));
            agvs.add(new Thread(new AGVTask(cintaTransportadora)));
        }

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
