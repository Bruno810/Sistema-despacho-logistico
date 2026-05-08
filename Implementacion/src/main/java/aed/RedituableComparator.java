package aed;

import java.util.Comparator;

public class RedituableComparator implements Comparator<TrasladoWrapper> {
    
    @Override
    public int compare(TrasladoWrapper t1, TrasladoWrapper t2) {
        int resCompareGanancia = Integer.compare(
            t1.getTraslado().getGananciaNeta(),
            t2.getTraslado().getGananciaNeta()
        );
        if (resCompareGanancia == 0) {
            return Integer.compare(t2.getTraslado().getId(), t1.getTraslado().getId());
        } else {
            return resCompareGanancia;
        }
    }
}
