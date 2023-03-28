package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class Pending extends Event {

    private final Server futureServer;

    public Pending(Customer customer, double eventTime, Server futureServer,
            double waitingTime, int servedCustomers, int leftCustomers) {
        super(customer, eventTime, waitingTime, servedCustomers, leftCustomers);
        this.futureServer = futureServer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<Server> servers = shop.getServerList();
        Server updatedServer = this.futureServer;
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (server.getId() == this.futureServer.getId()) {
                updatedServer = server;
            }
        }
        Customer topCustomer = updatedServer.getWaitingQueue().poll().first();
        if (this.customer.getCustomerId() == topCustomer.getCustomerId() && this.eventTime
                >= updatedServer.getNextAvailTime()) {
            Event serve = new Serve(this.customer, updatedServer.getNextAvailTime(),
                    updatedServer, updatedServer.getNextAvailTime() - this.customer
                    .getArrivalTime(), 1, 0);
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serve), shop);
        } else {
            Event pending = new Pending(this.customer, updatedServer.getNextAvailTime(),
                    updatedServer, 0, 0, 0);
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(pending), shop);
        }
    }

    @Override
    public String toString() {
        return "";
    }

}
