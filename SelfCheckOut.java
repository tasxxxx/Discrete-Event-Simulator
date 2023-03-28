package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.PQ;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.List;

public class SelfCheckOut {

    private final int id;
    private final boolean isBusy;
    private final double nextAvailTime;

    public SelfCheckOut(int id) {
        this.id = id;
        this.isBusy = false;
        this.nextAvailTime = 0.0;
    }

    public SelfCheckOut(int id, boolean isBusy, double nextAvailTime) {
        this.id = id;
        this.isBusy = isBusy;
        this.nextAvailTime = nextAvailTime;
    }

    public int getId() {
        return this.id;
    }

    public boolean isBusy() {
        return this.isBusy;
    }

    public double getNextAvailTime() {
        return this.nextAvailTime;
    }

    @Override
    public String toString() {
        return "" + this.id;
    }

}
