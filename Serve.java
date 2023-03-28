package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import cs2030.util.Pair;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class Serve extends Event {

    private final Server currentServer;

    public Serve(Customer customer, double eventTime, Server currentServer,
            double waitingTime, int servedCustomers, int leftCustomers) {
        super(customer, eventTime, waitingTime, servedCustomers, leftCustomers);
        this.currentServer = currentServer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<Server> servers = shop.getServerList();
        Server updatedServer = this.currentServer;
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (server.getId() == this.currentServer.getId()) {
                updatedServer = server;
            }
        }
        updatedServer = new Server(updatedServer.getId(), true,
                this.eventTime + this.customer.getServiceTime(), 
                updatedServer.getWaitingQueue(),
                updatedServer.getQueueMax(),
                updatedServer.getSupplierRestTime());
        if (!updatedServer.getWaitingQueue().isEmpty()) {
            if (this.customer.getCustomerId() == updatedServer.getWaitingQueue()
                    .poll().first().getCustomerId()) {
                PQ<Customer> updatedQueue = updatedServer.getWaitingQueue().poll().second();
                updatedServer = new Server(updatedServer.getId(), true,
                        this.eventTime + this.customer.getServiceTime(), 
                        updatedQueue, updatedServer.getQueueMax(),
                        updatedServer.getSupplierRestTime());
            }
        }
        Event done = new Done(this.customer, this.eventTime + this.customer.getServiceTime(),
                updatedServer);
        servers = servers.set(currentServer.getId() - 1, updatedServer);
        Shop updatedShop = new Shop(servers, shop.getSelfCheckOutList(),
                shop.getSelfCheckOutQueue(), shop.getSelfCheckOutQueueMax());
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(done), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + customer.toString() + 
                " serves by " + this.currentServer + "\n";
    }

}
