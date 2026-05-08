package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {

    private Heap<TrasladoWrapper> trasladosRedituable;
    private Heap<TrasladoWrapper> trasladosTimestamp;

    private Ciudad[] ciudades;
    private Heap<Ciudad> heapCiudades;

    private ArrayList<Integer> mayoresGanancias;
    private ArrayList<Integer> mayoresPerdidas;

    private int gananciaTotal;
    private int cantidadTrasladosDespachados;
    private int trasladosPendientes;
    private int mayorPerdida;
    private int mayorGanancia;


    public BestEffort(int cantCiudades, Traslado[] traslados) {     //O(|C| + |T|), pues hay un ciclo para las ciudades, otro para crear los traslados, y luego dos heapifies, todo en a lo sumo O(n)
        
        ciudades = new Ciudad[cantCiudades];
        int i = 0;
        while (i < cantCiudades) {          //O(|C|)
            Ciudad ciudad = new Ciudad(i, i);
            ciudades[i] = ciudad;
            i++;
        }

        TrasladoWrapper[] primerosTraslados = new TrasladoWrapper[traslados.length];
        int j = 0;
        while (j < traslados.length) {          //O(|T|)
            Handle handle = new Handle(j, j);
            TrasladoWrapper nuevoTraslado = new TrasladoWrapper(traslados[j], handle);
            primerosTraslados[j] = nuevoTraslado;
            j++;
        }

        Comparator<TrasladoWrapper> redituableComparator = new RedituableComparator();
        Comparator<TrasladoWrapper> timestampComparator = new TimestampComparator();
        Comparator<Ciudad> ciudadComparator = new CiudadComparator();
        trasladosRedituable = new Heap<TrasladoWrapper>(primerosTraslados, redituableComparator);   //O(|T|) (heapify)
        trasladosTimestamp = new Heap<TrasladoWrapper>(primerosTraslados, timestampComparator);     //O(|T|) (heapify)
        heapCiudades = new Heap<Ciudad>(ciudades, ciudadComparator);                                //O(|C|) (heapify)

        //Por default decimos que la ciudad con menor id es la de mayor ganancia al comienzo.
        this.gananciaTotal = 0;
        this.cantidadTrasladosDespachados = 0;
        this.mayoresGanancias = new ArrayList<>();
        this.mayoresPerdidas = new ArrayList<>();
        this.trasladosPendientes = traslados.length;
        this.mayorGanancia = 0;
        this.mayorPerdida = 0;
        mayoresGanancias.add(0);
        mayoresPerdidas.add(0);
    }

    
    public void registrarTraslados(Traslado[] traslados) {      //O(|traslados| * log(|T|)), pues por cada traslado se realiza el agregado en log|T|
        int i = 0;
        while (i < traslados.length) {               //O(|traslados| * log(|T|))
            Handle handle = new Handle(-1, -1);
            TrasladoWrapper nuevo = new TrasladoWrapper(traslados[i], handle);
            trasladosRedituable.agregar(nuevo);     //O(log(|T|)), siftUp actualiza handle.indexRedituable
            trasladosTimestamp.agregar(nuevo);      //O(log(|T|)), siftUp actualiza handle.indexTimestamp
            this.trasladosPendientes++;
            i++;
        }
    }

    public int[] despacharMasRedituables(int n) {   //O(n * (log(|T|) + log(|C|))
        if (n > trasladosPendientes) {
            n = trasladosPendientes;
        }

        int[] res = new int[n];
        int i = 0;

        while (i < n) {                           //O(n * (log(|T|) + log(|C|))
            TrasladoWrapper twAux = trasladosRedituable.sacarMaximo();      // O(log(|T|))
            trasladosTimestamp.eliminar(twAux.handle.getIndexTimestamp());  // O(log(|T|))
            res[i] = twAux.getTraslado().getId();
            estadisticaDespacho(twAux);                                     // O(log(|C|))

            this.cantidadTrasladosDespachados++;
            this.trasladosPendientes--;
            i++;
        }
        return res;
    }
    

    public int[] despacharMasAntiguos(int n) {      //O(n * (log(|T|) + log(|C|)), idem funcion anterior
        if (n > trasladosPendientes) {
            n = trasladosPendientes;
        }
        
        int[] res = new int[n];
        int i = 0;

        while (i < n) {                              //O(n * (log(|T|) + log(|C|))
            TrasladoWrapper twAux = trasladosTimestamp.sacarMaximo();         // O(log(|T|))
            trasladosRedituable.eliminar(twAux.handle.getIndexRedituable());  // O(log(|T|))
            res[i] = twAux.getTraslado().getId();
            estadisticaDespacho(twAux);                                       // O(log(|C|))

            this.cantidadTrasladosDespachados++;
            this.trasladosPendientes--;
            i++;
        }
        return res;
    }
    
    public int ciudadConMayorSuperavit() { //O(1)
        Ciudad res = heapCiudades.obtener(0);
        return res.id();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {  //O(1)
        return mayoresGanancias;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {   //O(1)
        return mayoresPerdidas;
    }

    public int gananciaPromedioPorTraslado() {              //O(1)
        return gananciaTotal / cantidadTrasladosDespachados;
    }


    private void estadisticaDespacho(TrasladoWrapper tw) { //O(log(|C|))
        Traslado t = tw.getTraslado();

        Ciudad ciudadOrigen = ciudades[t.getOrigen()];
        ciudadOrigen.agregarGanancia(t.getGananciaNeta());
        heapCiudades.siftUp(ciudadOrigen.indexHeap());          //O(log(|C|))

        Ciudad ciudadDestino = ciudades[t.getDestino()];
        ciudadDestino.agregarPerdida(t.getGananciaNeta());
        heapCiudades.siftDown(ciudadDestino.indexHeap());       //O(log(|C|))
        
        gananciaTotal = gananciaTotal + t.getGananciaNeta();
        modificaMayoresGanancias(t.getOrigen());
        modificaMayoresPerdidas(t.getDestino());
    }

    private void modificaMayoresGanancias(int id) { //O(1)
        if (ciudades[id].ganancia() > mayorGanancia) {
            mayoresGanancias.clear();
            mayoresGanancias.add(id);
            mayorGanancia = ciudades[id].ganancia();
        } else if (ciudades[id].ganancia() == mayorGanancia) {
            mayoresGanancias.add(id);
        }        
    }

    private void modificaMayoresPerdidas(int id) { //O(1)
        if (ciudades[id].perdida() > mayorPerdida) {
            mayoresPerdidas.clear();
            mayoresPerdidas.add(id);
            mayorPerdida = ciudades[id].perdida();
        } else if (ciudades[id].perdida() == mayorPerdida) {
            mayoresPerdidas.add(id);
        }        
    }
}
