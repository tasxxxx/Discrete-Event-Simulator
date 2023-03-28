package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;


class Statistics {

    private final double averageWaitingTime;
    private final int servedCustomers;
    private final int leftCustomers;

    Statistics(double averageWaitingTime, int servedCustomers, int leftCustomers) {
        this.averageWaitingTime = averageWaitingTime;
        this.servedCustomers = servedCustomers;
        this.leftCustomers = leftCustomers;
    }

    public double getAverageWaitingTime() {
        return this.averageWaitingTime;
    }

    public int getServedCustomers() {
        return this.servedCustomers;
    }

    public int getLeftCustomers() {
        return this.leftCustomers;
    }

    @Override
    public String toString() {
        return "[" + String.format("%.3f", this.averageWaitingTime) + " " +
                this.servedCustomers + " " + this.leftCustomers + "]";
    }

}
