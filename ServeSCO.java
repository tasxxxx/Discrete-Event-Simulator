package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import cs2030.util.Pair;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class ServeSCO extends Event {

    private final SelfCheckOut currentSCO;

    public ServeSCO(Customer customer, double eventTime, SelfCheckOut currentSCO,
            double waitingTime, int servedCustomers, int leftCustomers) {
        super(customer, eventTime, waitingTime, servedCustomers, leftCustomers);
        this.currentSCO = currentSCO;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<SelfCheckOut> selfcheckouts = shop.getSelfCheckOutList();
        ImList<Server> servers = shop.getServerList();
        PQ<Customer> scoQueue = shop.getSelfCheckOutQueue();
        SelfCheckOut updatedSCO = this.currentSCO;
        for (int i = 0; i < selfcheckouts.size(); i++) {
            SelfCheckOut sco = selfcheckouts.get(i);
            if (sco.getId() == this.currentSCO.getId()) {
                updatedSCO = sco;
            }
        }
        updatedSCO = new SelfCheckOut(updatedSCO.getId(), true,
                this.eventTime + this.customer.getServiceTime());
        Event done = new DoneSCO(this.customer, this.eventTime +
                this.customer.getServiceTime(), updatedSCO);
        selfcheckouts = selfcheckouts.set(currentSCO.getId()
                - 1 - servers.size(), updatedSCO);
        PQ<Customer> updatedQueue = scoQueue;
        if (!scoQueue.isEmpty()) {
            if (this.customer.getCustomerId() == scoQueue.poll()
                    .first().getCustomerId()) {
                updatedQueue = scoQueue.poll().second();
            }
        } 
        Shop updatedShop = new Shop(servers, selfcheckouts, updatedQueue,
                shop.getSelfCheckOutQueueMax());
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(done), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + customer.toString() +
                " serves by self-check " + this.currentSCO.getId() + "\n";
    }

}
