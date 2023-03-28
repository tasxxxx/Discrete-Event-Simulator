package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;
import cs2030.util.PQ;

public class Shop {

    private final ImList<Server> list;
    private final ImList<SelfCheckOut> scoList;
    private final PQ<Customer> scoQueue;
    private final int scoQueueMax;

    public Shop(List<Server> list) {
        this.list = ImList.<Server>of(list);
        this.scoList = ImList.<SelfCheckOut>of();
        this.scoQueue = new PQ<Customer>(new CustomerComparator());
        this.scoQueueMax = 0;
    }

    public Shop(ImList<Server> list) {
        this.list = list;
        this.scoList = ImList.<SelfCheckOut>of();
        this.scoQueue = new PQ<Customer>(new CustomerComparator());
        this.scoQueueMax = 0;
    }

    public Shop(ImList<Server> list, ImList<SelfCheckOut> scoList, int scoQueueMax) {
        this.list = list;
        this.scoList = scoList;
        this.scoQueue = new PQ<Customer>(new CustomerComparator());
        this.scoQueueMax = scoQueueMax;
    }

    public Shop(ImList<Server> list, ImList<SelfCheckOut> scoList, PQ<Customer> scoQueue,
            int scoQueueMax) {
        this.list = list;
        this.scoList = scoList;
        this.scoQueue = scoQueue;
        this.scoQueueMax = scoQueueMax;
    }

    public ImList<Server> getServerList() {
        return this.list;
    }

    public ImList<SelfCheckOut> getSelfCheckOutList() {
        return this.scoList;
    }

    public PQ<Customer> getSelfCheckOutQueue() {
        return this.scoQueue;
    }

    public int getSelfCheckOutQueueMax() {
        return this.scoQueueMax;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

}
