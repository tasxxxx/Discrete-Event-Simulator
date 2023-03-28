package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class Arrive extends Event {

    public Arrive(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<Server> servers = shop.getServerList();
        ImList<SelfCheckOut> selfcheckouts = shop.getSelfCheckOutList();
        for (int i = 0; i < servers.size(); i++) {
            Server currentServer = servers.get(i);
            if (!currentServer.isBusy() && this.eventTime >= 
                    currentServer.getNextAvailTime()) {
                Event serve = new Serve(this.customer, this.eventTime,
                        currentServer, 0, 1, 0);
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serve), shop);
            }
        }
        for (int i = 0; i < selfcheckouts.size(); i++) {
            SelfCheckOut currentSco = selfcheckouts.get(i);
            if (!currentSco.isBusy()) {
                Event serve = new ServeSCO(this.customer, this.eventTime,
                        currentSco, 0, 1, 0);
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serve), shop);
            }
        }
        for (int i = 0; i < servers.size(); i++) {
            Server futureServer = servers.get(i);
            if ((futureServer.isBusy() || this.eventTime < futureServer.getNextAvailTime())
                    && futureServer.getWaitingQueue().size() < futureServer.getQueueMax()) {
                PQ<Customer> waitingQueue = futureServer.getWaitingQueue().add(this.customer);
                futureServer = new Server(futureServer.getId(), true,
                        futureServer.getNextAvailTime(), waitingQueue,
                        futureServer.getQueueMax(), futureServer.getSupplierRestTime());
                servers = servers.set(i, futureServer);
                Shop updatedShop = new Shop(servers, selfcheckouts, shop.getSelfCheckOutQueue(),
                        shop.getSelfCheckOutQueueMax());
                Event wait = new Wait(this.customer, this.eventTime, futureServer, 0, 0, 0);
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(wait), updatedShop);
            } 
        }
        if (shop.getSelfCheckOutList().size() > 0 && 
            shop.getSelfCheckOutQueue().size() < shop.getSelfCheckOutQueueMax()) {
            PQ<Customer> waitingQueue = shop.getSelfCheckOutQueue().add(this.customer);
            Shop updatedShop = new Shop(servers, selfcheckouts, waitingQueue,
                 shop.getSelfCheckOutQueueMax());
            Event wait = new WaitSCO(this.customer, this.eventTime, updatedShop, 0, 0, 0);
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(wait), updatedShop);
        }
        Event leave = new Leave(this.customer, this.eventTime, 0, 0, 1);
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(leave), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " +  customer.toString() +
                " arrives" + "\n";
    }

}
