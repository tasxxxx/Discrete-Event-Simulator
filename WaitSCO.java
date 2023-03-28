package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class WaitSCO extends Event {

    private final Shop shop;

    public WaitSCO(Customer customer, double eventTime, Shop shop,
            double waitingTime, int servedCustomers, int leftCustomers) {
        super(customer, eventTime, waitingTime, servedCustomers, leftCustomers);
        this.shop = shop;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<SelfCheckOut> selfcheckouts = shop.getSelfCheckOutList();
        Customer topCustomer = shop.getSelfCheckOutQueue().poll().first();
        if (this.customer.getCustomerId() == topCustomer.getCustomerId()) {
            for (int i = 0; i < selfcheckouts.size(); i++) {
                SelfCheckOut currentSCO = selfcheckouts.get(i);
                if (!currentSCO.isBusy()) {
                    Event serve = new ServeSCO(this.customer, currentSCO
                            .getNextAvailTime(),currentSCO,
                            currentSCO.getNextAvailTime() -
                            this.customer.getArrivalTime(), 1, 0);
                    return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serve), shop);
                }
            }
        }
        double earliestTime = selfcheckouts.get(0).getNextAvailTime();
        for (int i = 0; i < selfcheckouts.size(); i++) {
            SelfCheckOut currentSCO = selfcheckouts.get(i);
            if (currentSCO.getNextAvailTime() < earliestTime) {
                earliestTime = currentSCO.getNextAvailTime();
            }
        }
        Event pending = new PendingSCO(this.customer, earliestTime, 0, 0, 0);
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(pending), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + customer.toString() +
                " waits at self-check " + this.shop.getSelfCheckOutList()
                .get(0).getId() + "\n";
    }

}
