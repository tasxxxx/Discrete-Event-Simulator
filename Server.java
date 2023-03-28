package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.PQ;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.List;

public class Server {

    private final int id;
    private final boolean isBusy;
    private final double nextAvailTime;
    private final PQ<Customer> waitingQueue;
    private final int queueMax;
    private final Supplier<Double> restTime;

    public Server(int id) {
        this.id = id;
        this.isBusy = false;
        this.nextAvailTime = 0.0;
        this.waitingQueue = new PQ<Customer>(new CustomerComparator());
        this.queueMax = 1;
        this.restTime = (() -> 0.0);
    }

    public Server(int id, int queueMax) {
        this.id = id;
        this.isBusy = false;
        this.nextAvailTime = 0.0;
        this.waitingQueue = new PQ<Customer>(new CustomerComparator());
        this.queueMax = queueMax;
        this.restTime = (() -> 0.0);
    }

    public Server(int id, boolean isBusy, double nextAvailTime,
            PQ<Customer> waitingQueue, int queueMax) {
        this.id = id;
        this.isBusy = isBusy;
        this.nextAvailTime = nextAvailTime;
        this.waitingQueue = waitingQueue;
        this.queueMax = queueMax;
        this.restTime = (() -> 0.0);
    }

    public Server(int id, int queueMax, Supplier<Double> restTime) {
        this.id = id;
        this.isBusy = false;
        this.nextAvailTime = 0.0;
        this.waitingQueue = new PQ<Customer>(new CustomerComparator());
        this.queueMax = queueMax;
        this.restTime = restTime;
    }

    public Server(int id, boolean isBusy, double nextAvailTime,
            PQ<Customer> waitingQueue, int queueMax, Supplier<Double> restTime) {
        this.id = id;
        this.isBusy = isBusy;
        this.nextAvailTime = nextAvailTime;
        this.waitingQueue = waitingQueue;
        this.queueMax = queueMax;
        this.restTime = restTime;
    }

    public boolean isBusy() {
        return this.isBusy;
    }

    public int getId() {
        return this.id;
    }

    public double getNextAvailTime() {
        return this.nextAvailTime;
    }

    public PQ<Customer> getWaitingQueue() {
        return this.waitingQueue;
    }

    public int getQueueMax() {
        return this.queueMax;
    }

    public Supplier<Double> getSupplierRestTime() {
        return this.restTime;
    }

    @Override
    public String toString() {
        return "" + this.id;
    }

}
