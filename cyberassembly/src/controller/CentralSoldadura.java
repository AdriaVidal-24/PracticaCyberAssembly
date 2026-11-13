package controller;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class CentralSoldadura {
    private int N;
    private int[] llistatEstacions;
    private volatile int cotxosFets = 0;
    private Semaphore sem;
    private Semaphore mutex = new Semaphore(1);

    public CentralSoldadura(int n) {
        this.N = n;
        this.llistatEstacions = new int[N];
        this.sem = new Semaphore(N);
    }

    public int accedirCentral(int id) throws InterruptedException {
        sem.acquire();
        return accesLlistaEstacions(id);
    }

    public void lliurarCentral(int id, int numEstacio) {
        sortirLlistaEstacions(numEstacio);
        sem.release();
    }

    public void registreCentral(int id) throws InterruptedException {
        mutex.acquire();
        try {
            cotxosFets++;
        } finally {
            mutex.release();
        }
    }

    private synchronized int accesLlistaEstacions(int id) {
        for (int i = 0; i < N; i++) {
            if (llistatEstacions[i] == 0) {
                llistatEstacions[i] = id;
                return i;
            }
        }
        return -1;
    }

    private synchronized void sortirLlistaEstacions(int numEstacio) {
        llistatEstacions[numEstacio] = 0;
    }

    public synchronized int[] getEstacions() {
        return Arrays.copyOf(llistatEstacions, llistatEstacions.length);
    }

    public int getCotxosFets() {
        return cotxosFets;
    }

    public synchronized int esperarAvaria() throws InterruptedException {
        int estacioAvariada = -1;
        while(estacioAvariada == -1) {
            for (int i = 0; i<llistatEstacions.length; i++) {
                if(llistatEstacions[i] == -1) estacioAvariada = i;
            }
            if(estacioAvariada == -1) {
                wait();
            } else {
                return estacioAvariada;
            }
        }
        return estacioAvariada;
    }

    public void rompreEstacio() {
        if(sem.tryAcquire()) {
            notificarAvaria();
        }
    }
    
    private synchronized void notificarAvaria() {
        accesLlistaEstacions(-1);
        notifyAll();
    }

    public void repararEstacio(int estacio) {
        sortirLlistaEstacions(estacio);
        sem.release();
    }
}