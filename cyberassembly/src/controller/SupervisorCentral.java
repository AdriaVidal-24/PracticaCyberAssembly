package controller;

public class SupervisorCentral {
    // ATRIBUTS
    private boolean alerta = false;

    // GETTERS & SETTERS
    public synchronized boolean getAlerta() {
        return alerta;
    }

    // FUNCIONS
    public synchronized void activarAlerta() {
        alerta = true;
    }

    public synchronized void resoldreAlerta() {
        alerta = false;
        notifyAll();
    }

    public synchronized void verificarEstat(int id) throws InterruptedException {
        while (alerta) {
            wait();
        }
    }
}
