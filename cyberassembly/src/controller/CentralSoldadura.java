package controller;

import java.util.concurrent.Semaphore;

public class CentralSoldadura {
    private int N;
    private int[] llistatEstacions;
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
            System.out.println("El robot " + id + " está qualificat.");
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
}