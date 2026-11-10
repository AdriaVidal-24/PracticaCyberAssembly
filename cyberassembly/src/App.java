import java.util.ArrayList;
import java.util.List;

import controller.*;
import tasks.RobotTask;;


public class App {
    public static void main(String[] args) {
        CentralSoldadura centralSoldadura = new CentralSoldadura();
        SupervisorCentral supervisorCentral = new SupervisorCentral();
        RobotTask robotTask = new RobotTask(centralSoldadura, supervisorCentral);

        List<Thread> llistat = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            llistat.add(new Thread(robotTask));
        }

        for (Thread thread : llistat) {
            thread.start();
        }
    }
}
