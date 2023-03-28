package cs2030.simulator;

import java.util.function.Supplier;
import java.util.function.Supplier;
import cs2030.util.Lazy;

public class Customer {

    private final int id;
    private final double arrival;
    private final Lazy<Double> service;

    public Customer(int id, double arrival) {
        this.id = id;
        this.arrival = arrival;
        this.service = new Lazy<Double>(() -> 1.0);
    }

    public Customer(int id, double arrival, Supplier<Double> service) {
        this.id = id;
        this.arrival = arrival;
        this.service = new Lazy<Double>(service);
    }

    public int getCustomerId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrival;
    }

    public double getServiceTime() {
        return this.service.get();
    }

    @Override
    public String toString() {
        return "" + this.id;
    }

}
