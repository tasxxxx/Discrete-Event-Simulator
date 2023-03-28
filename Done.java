package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import java.util.function.Supplier;
import cs2030.util.Pair;
import cs2030.util.ImList;

public class Done extends Event {

    private final Server finishedServer;

    public Done(Customer customer, double eventTime, Server finishedServer) {
        super(customer, eventTime);
        this.finishedServer = finishedServer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        ImList<Server> servers = shop.getServerList();
        Server updatedServer = this.finishedServer;
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (server.getId() == updatedServer.getId()) {
                updatedServer = server;
            }
        }
        double restTime = updatedServer.getSupplierRestTime().get();
        updatedServer = new Server(updatedServer.getId(),
                false, this.eventTime + restTime,
                updatedServer.getWaitingQueue(),
                updatedServer.getQueueMax(), updatedServer.getSupplierRestTime());
        servers = servers.set(finishedServer.getId() - 1, updatedServer);
        Shop updatedShop = new Shop(servers, shop.getSelfCheckOutList(),
                shop.getSelfCheckOutQueue(), shop.getSelfCheckOutQueueMax());
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime) + " " + customer.toString() +
                " done serving by " + this.finishedServer + "\n";
    }

}
