package cs2030.util;

import java.util.PriorityQueue;
import java.util.Comparator;

public class PQ<T> {

    private final PriorityQueue<T> queue;
    private final Comparator<? super T> cmp;

    public PQ(Comparator<? super T> cmp) {
        this.cmp = cmp;
        this.queue = new PriorityQueue<T>(cmp);
    }

    public PQ(Comparator<? super T> cmp, PriorityQueue<T> queue) {
        this.cmp = cmp;
        this.queue = queue;
    }

    public PQ<T> add(T t) {
        PriorityQueue<T> newQueue = new PriorityQueue<T>(this.cmp);
        for (T s : this.queue) {
            newQueue.add(s);
        }
        newQueue.add(t);
        return new PQ<T>(this.cmp, newQueue);
    }

    public PQ<T> add(PQ<T> pq) {
        PriorityQueue<T> newQueue = new PriorityQueue<T>(this.cmp);
        for (T s: this.queue) {
            newQueue.add(s);
        }
        for (T u: pq.queue) {
            newQueue.add(u);
        }
        return new PQ<T>(this.cmp, newQueue);
    }

    public Pair<T, PQ<T>> poll() {
        PriorityQueue<T> copyQueue = new PriorityQueue<T>(this.cmp);
        for (T s : this.queue) {
            copyQueue.add(s);
        }
        T removed = copyQueue.poll();
        PQ<T> newQueue = new PQ<T>(this.cmp, copyQueue);
        return Pair.<T, PQ<T>>of(removed, newQueue);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int size() {
        return this.queue.size();
    }

    @Override
    public String toString() {
        return this.queue.toString();
    }



}
