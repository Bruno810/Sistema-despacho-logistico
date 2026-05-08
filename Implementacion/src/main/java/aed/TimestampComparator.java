package aed;

import java.util.Comparator;

public class TimestampComparator implements Comparator<TrasladoWrapper> {
    
    @Override
    public int compare(TrasladoWrapper t1, TrasladoWrapper t2) {
        return Integer.compare(t2.getTraslado().getTimestamp(), t1.getTraslado().getTimestamp());
    }
}
