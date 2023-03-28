package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.Pair;
import cs2030.util.ImList;
import java.util.List;

public class Simulate2 {

    private final Shop shopStatus;
    private final PQ<Event> queue;

    public Simulate2(int serverNo, List<Double> custArrTime) {
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ImList<Server> servers = ImList.<Server>of();
        for (int i = 0; i < custArrTime.size(); i++) {
            queue = queue.add(new EventStub(new Customer(i + 1, custArrTime.get(i)), 
                                                                custArrTime.get(i)));
        }
        for (int i = 0; i < serverNo; i++) {
            servers = servers.add(new Server(i + 1));
        }
        this.shopStatus = new Shop(servers);
        this.queue = queue;
    }

    public String run() {
        String output = "";
        PQ<Event> copyQueue = this.queue;
        while (!copyQueue.isEmpty()) {
            output += copyQueue.poll().first().toString() + "\n";
            copyQueue = copyQueue.poll().second();
        }
        output += "-- End of Simulation --";
        return output;
    }

    @Override
    public String toString() {
        return "Queue: " + this.queue.toString() +
                "; Shop: " + this.shopStatus.getServerList().toString();
    }


}
