package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;
import cs2030.util.ImList;

public class Leave extends Event {

    public Leave(Customer customer, double eventTime, double waitingTime,
            int servedCustomers, int leftCustomers) {
        super(customer, eventTime, waitingTime, servedCustomers, leftCustomers);
    }

    @Override
    Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " +  customer.toString() +
                " leaves" + "\n";
    }

}
