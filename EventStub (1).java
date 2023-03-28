package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class EventStub extends Event {

    public EventStub(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), shop);
    }

}
