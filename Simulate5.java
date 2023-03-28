package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.Pair;
import cs2030.util.ImList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate5 {

    private final Shop initialShopStatus;
    private final PQ<Event> queue;

    public Simulate5(int serverNo, List<Pair<Double, Supplier<Double>>> arrServiceTimes) {
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        for (int i = 0; i < arrServiceTimes.size(); i++) {
            queue = queue.add(new Arrive(new Customer(
                    i + 1, arrServiceTimes.get(i).first(),
                    arrServiceTimes.get(i).second()),
                    arrServiceTimes.get(i).first()));
        }
        ImList<Server> servers = ImList.<Server>of();
        for (int i = 0; i < serverNo; i++) {
            servers = servers.add(new Server(i + 1));
        }
        this.initialShopStatus = new Shop(servers);
        this.queue = queue;
    }

    public String run() {
        String output = "";
        PQ<Event> copyQueue = this.queue;
        Shop copyShopStatus = this.initialShopStatus;
        Statistics statistics = new Statistics(0, 0, 0);
        while (!copyQueue.isEmpty()) {
            Event event = copyQueue.poll().first();
            statistics = new Statistics(
                    statistics.getAverageWaitingTime() + event.getWaitingTime(),
                    statistics.getServedCustomers() + event.getServedCustomers(),
                    statistics.getLeftCustomers() + event.getLeftCustomers());
            copyQueue = copyQueue.poll().second();
            output += event.toString();
            Optional<Event> nextEvent = event.execute(copyShopStatus).first();
            Shop updatedShopStatus = event.execute(copyShopStatus).second();
            if (nextEvent != Optional.<Event>empty()) {
                copyQueue = copyQueue.add(nextEvent.orElseThrow());
            }
            copyShopStatus = updatedShopStatus;
        }
        statistics = new Statistics(
                   statistics.getAverageWaitingTime() / statistics.getServedCustomers(),
                   statistics.getServedCustomers(),
                   statistics.getLeftCustomers());
        output += statistics.toString();
        return output;
    }

}
