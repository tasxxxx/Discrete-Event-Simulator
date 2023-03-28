package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() > e2.getEventTime()) {
            return 1;
        } else if (e2.getEventTime() > e1.getEventTime()) {
            return -1;
        } else {
            return e1.getCustomer().getCustomerId() - e2.getCustomer().getCustomerId();
        }    
    }


}
