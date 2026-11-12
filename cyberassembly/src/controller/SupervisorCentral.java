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
    }
    
    public synchronized void resoldreAlerta() {
        alerta = false;
        notifyAll();
    }

    public synchronized boolean getAlerta() {
        return alerta;
    }
}
