package aed;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int getId() { 
        return id; 
    }

    public int getOrigen() { 
        return origen; 
    }

    public int getDestino() { 
        return destino; 
    }

    public int getGananciaNeta() { 
        return gananciaNeta; 
    }

    public int getTimestamp() { 
        return timestamp; 
    }
}
