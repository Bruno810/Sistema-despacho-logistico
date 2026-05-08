package aed;

public class Ciudad {
    
    private int ganancia;
    private int perdida;
    private int superavit;
    private int id;
    private int indiceHeap;

    public Ciudad(int id, int indiceHeap) {
        this.ganancia = 0;
        this.perdida = 0;
        this.superavit = 0;
        this.id = id;
        this.indiceHeap = indiceHeap;
    }

    public int ganancia() {
        return ganancia;
    }

    public int perdida() {
        return perdida;
    }

    public int superavit() {
        return superavit;
    }

    public int id() {
        return id;
    }

    public int indexHeap() {
        return indiceHeap;
    }

    public int agregarGanancia(int g) {
        ganancia = ganancia + g;
        superavit = superavit + g;
        return ganancia;
    }

    public int agregarPerdida(int p) {
        perdida = perdida + p;
        superavit = superavit - p;
        return perdida;
    }

    public void setIndex(int ref) {
        this.indiceHeap = ref;
    }
}
