package controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CintaTransportadora {
    private int quantitatBateries = 0;
    private int maximBateries = 10;
    private ReentrantLock lock = new ReentrantLock();
    private final Condition cintaNoPlena = lock.newCondition();
    private final Condition cintaNoBuida = lock.newCondition();

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

    public int getQuantitatBateries() {
        lock.lock();
        try {
            return quantitatBateries;
        } finally {
            lock.unlock();
        }
    }

    public int getMaximBateries() {
        return maximBateries;
    }
}
