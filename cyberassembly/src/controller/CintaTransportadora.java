package controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CintaTransportadora {
    // ATRIBUTS
    private int quantitatBateries = 0;
    private int maximBateries;
    private ReentrantLock lock = new ReentrantLock();
    private final Condition cintaNoPlena = lock.newCondition();
    private final Condition cintaNoBuida = lock.newCondition();

    // CONSTRUCTOR
    public CintaTransportadora(int maximBateries) {
        this.maximBateries = maximBateries;
    }

    // GETTERS & SETTERS
    public int getMaximBateries() {
        return maximBateries;
    }

    public int getQuantitatBateries() {
        lock.lock();
        try {
            return quantitatBateries;
        } finally {
            lock.unlock();
        }
    }

    // FUNCIONS
    public void afegirBateria() throws InterruptedException {
        lock.lock();
        try {
            while (quantitatBateries == maximBateries)
                cintaNoPlena.await();
            quantitatBateries++;
            cintaNoBuida.signal();
        } finally {
            lock.unlock();
        }
    }

    public void retirarBateria() throws InterruptedException {
        lock.lock();
        try {
            while (quantitatBateries == 0)
                cintaNoBuida.await();
            quantitatBateries--;
            cintaNoPlena.signal();
        } finally {
            lock.unlock();
        }
    }
}
