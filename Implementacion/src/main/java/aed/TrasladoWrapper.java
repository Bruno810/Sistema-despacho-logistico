package aed;

public class TrasladoWrapper {
    
    private Traslado traslado;
    public Handle handle;

    public TrasladoWrapper(Traslado traslado, Handle handle) {
        this.traslado = traslado;
        this.handle = handle;
    }

    public Traslado getTraslado() {
        return traslado;
    }
}
