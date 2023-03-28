package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import java.util.function.Supplier;
import cs2030.util.Pair;
import cs2030.util.ImList;

public class DoneSCO extends Event {

    private final SelfCheckOut sco;

    public DoneSCO(Customer customer, double eventTime, SelfCheckOut sco) {
        super(customer, eventTime);
        this.sco = sco;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<Server> servers = shop.getServerList();
        ImList<SelfCheckOut> selfcheckouts = shop.getSelfCheckOutList();
        SelfCheckOut updatedSCO = this.sco;
        for (int i = 0; i < selfcheckouts.size(); i++) {
            SelfCheckOut sco = selfcheckouts.get(i);
            if (sco.getId() == updatedSCO.getId()) {
                updatedSCO = sco;
            }
        }
        updatedSCO = new SelfCheckOut(updatedSCO.getId(), false, this.eventTime);
        selfcheckouts = selfcheckouts.set(sco.getId() - 1 - servers.size(), 
                updatedSCO);
        Shop updatedShop = new Shop(servers, selfcheckouts, shop.getSelfCheckOutQueue(),
                shop.getSelfCheckOutQueueMax());
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + customer.toString() +
                " done serving by self-check " + this.sco.getId() + "\n";
    }

}

