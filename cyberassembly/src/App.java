import java.util.ArrayList;
import java.util.List;

import controller.*;
import tasks.AGVTask;
import tasks.RobotTask;
import view.Dashboard;;

public class App {
    public static void main(String[] args) throws InterruptedException {
        CentralSoldadura centralSoldadura = new CentralSoldadura(3);
        CintaTransportadora cintaTransportadora = new CintaTransportadora();
        SupervisorCentral supervisorCentral = new SupervisorCentral();

        Dashboard dashboard = new Dashboard();
        // dashboard.setDaemon(true);

        List<Thread> Robots = new ArrayList<>();
        List<Thread> AGVs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Robots.add(new Thread(new RobotTask(i, centralSoldadura, cintaTransportadora, supervisorCentral)));
            AGVs.add(new Thread(new AGVTask(cintaTransportadora, i)));
        }

        for (int i = 0; i < Robots.size(); i++) {
            Robots.get(i).start();
            AGVs.get(i).start();
        }

        for (int i = 0; i < Robots.size(); i++) {
            Robots.get(i).join();
            AGVs.get(i).join();
        }
    }
}
