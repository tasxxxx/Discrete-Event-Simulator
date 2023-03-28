package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.Pair;
import cs2030.util.ImList;
import java.util.List;
import java.util.Optional;

public class Simulate3 {

    private final Shop initialShopStatus;
    private final PQ<Event> queue;

    public Simulate3(int serverNo, List<Double> custArrTime) {
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        for (int i = 0; i < custArrTime.size(); i++) {
            queue = queue.add(new Arrive(new Customer(
                    i + 1, custArrTime.get(i)),custArrTime.get(i)));
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
        while (!copyQueue.isEmpty()) {
            Event event = copyQueue.poll().first();
            copyQueue = copyQueue.poll().second();
            output += event.toString();
            Optional<Event> nextEvent = event.execute(copyShopStatus).first();
            Shop updatedShopStatus = event.execute(copyShopStatus).second();
            if (nextEvent != Optional.<Event>empty()) {
                copyQueue = copyQueue.add(nextEvent.orElseThrow());
            }
            copyShopStatus = updatedShopStatus;
        }
        output += "-- End of Simulation --";
        return output;
    }

}
