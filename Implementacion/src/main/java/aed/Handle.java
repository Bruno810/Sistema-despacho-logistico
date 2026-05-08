package aed;

public class Handle {
    
    private int indexRedituable;
    private int indexTimestamp;

    public Handle(int indexRedituable, int indexTimestamp) {
        this.indexRedituable = indexRedituable;
        this.indexTimestamp = indexTimestamp;
    }

    public int getIndexRedituable() {
        return indexRedituable;
    }

    public int getIndexTimestamp() {
        return indexTimestamp;
    }

    public void setIndexRedituable(int ref) {
        indexRedituable = ref;
    }

    public void setIndexTimestamp(int ref) {
        indexTimestamp = ref;
    }
}
