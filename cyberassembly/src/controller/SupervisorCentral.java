package controller;

public class SupervisorCentral {
    private boolean alerta = false;

    public synchronized void verificarEstat(int id) throws InterruptedException {
        while (alerta) {
            wait();
        }
    }

    public synchronized void activarAlerta() {
        alerta = true;
        System.out.println("ALERTA!");
    }
    
    public synchronized void resoldreAlerta() {
        System.out.println("NO ALERTA!");
        alerta = false;
        notifyAll();
    }

    public synchronized boolean getAlerta() {
        return alerta;
    }
}
