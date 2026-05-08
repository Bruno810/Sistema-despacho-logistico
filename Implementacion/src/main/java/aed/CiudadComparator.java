package aed;

import java.util.Comparator;

public class CiudadComparator implements Comparator<Ciudad>{
    
    @Override
    public int compare(Ciudad c1, Ciudad c2) {
        int resCompareSuperavit = Integer.compare(c1.superavit(), c2.superavit());
        if(resCompareSuperavit == 0){
            return Integer.compare(c2.id(), c1.id());
        } else {
            return resCompareSuperavit;
        }
         
    }
}
