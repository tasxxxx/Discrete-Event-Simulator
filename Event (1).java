package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public abstract class Event {

    protected final Customer customer;
    protected final double eventTime;
    protected final double waitingTime;
    protected final int servedCustomers;
    protected final int leftCustomers;

    public Event(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.waitingTime = 0.0;
        this.servedCustomers = 0;
        this.leftCustomers = 0;
    }

    public Event(Customer customer, double eventTime, double waitingTime,
            int servedCustomers, int leftCustomers) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.waitingTime = waitingTime;
        this.servedCustomers = servedCustomers;
        this.leftCustomers = leftCustomers;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getEventTime() {
        return this.eventTime;
    }

    public double getWaitingTime() {
        return this.waitingTime;
    }

    public int getServedCustomers() {
        return this.servedCustomers;
    }

    public int getLeftCustomers() {
        return this.leftCustomers;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime);
    }

    abstract Pair<Optional<Event>, Shop> execute(Shop shop);

}
