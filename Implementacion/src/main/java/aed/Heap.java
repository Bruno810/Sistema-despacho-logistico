package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    
    private ArrayList<T> data;
    private Comparator<T> comparador;


    public Heap(T[] elems, Comparator<T> c) {
        data = new ArrayList<T>();
        comparador = c;
        
        int i = 0;
        while (i < elems.length) {
            data.add(elems[i]);
            i++;
        }

        heapify();
    }
    
    public void heapify() {
        
        if (data.size() < 2) {
            return;
        }

        int i = (data.size() - 1) / 2;
        while (i >= 0) {
            siftDown(i);
            i--;
        }
    }
    
    public void agregar(T elem) {
        data.add(elem);
        actualizarHandle(data.size() - 1, elem);
        siftUp(data.size() - 1);
    }

    // Sube el elemento en la posición i hasta que esté en la posición correcta. -> Se usa al agregar un elemento al heap.
    public void siftUp(int i) {
        
        int posHijo = i;
        int posPadre = (posHijo - 1) / 2;
        T hijo = data.get(posHijo);
        T padre = data.get(posPadre);
        
        while (posHijo != 0 && (comparador.compare(padre, hijo) < 0)) {
            
            data.set(posHijo, padre);
            data.set(posPadre, hijo);

            actualizarHandle(posPadre, hijo);
            actualizarHandle(posHijo, padre);
            
            posHijo = posPadre;
            posPadre = (posHijo - 1) / 2;
            hijo = data.get(posHijo);
            padre = data.get(posPadre);
        }    
    }

    public void siftDown(int i) {
        
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;
        int mayor = i;

        if (hijoIzq < data.size() && comparador.compare(data.get(hijoIzq), data.get(mayor)) > 0) {
            mayor = hijoIzq;
        }

        if (hijoDer < data.size() && comparador.compare(data.get(hijoDer), data.get(mayor)) > 0) {
            mayor = hijoDer;
        }

        if (mayor != i) {

            T temp = data.get(i);
            T mayorT = data.get(mayor);
            data.set(i, mayorT);
            data.set(mayor, temp);

            actualizarHandle(i, mayorT);
            actualizarHandle(mayor, temp);
            
            siftDown(mayor);
        }
    }

    public T sacarMaximo() {
        T raiz = data.get(0);

        T ultimoElemento = data.get(data.size() - 1);
        data.set(0, ultimoElemento);
        data.remove(data.size() - 1);

        if (!data.isEmpty()) {
            actualizarHandle(0, ultimoElemento);
            siftDown(0);
        }

        return raiz;
    }

    public void eliminar(int i) {
        
        if (i == 0) {
            sacarMaximo();
            return;
        }

        // Si es el último elemento lo sacamos
        if (i == data.size() - 1) {
            data.remove(data.size() - 1);
            return;
        }
        
        T ultimoElemento = data.get(data.size() - 1);
        data.set(i, ultimoElemento);
        data.remove(data.size() - 1);

        actualizarHandle(i, ultimoElemento);

        T padre = data.get((i - 1) / 2);
        if (comparador.compare(ultimoElemento, padre) > 0) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    private void actualizarHandle(int i, T elem) {
        if (elem instanceof TrasladoWrapper) {
            TrasladoWrapper traslado = (TrasladoWrapper) elem;
            if (comparador instanceof RedituableComparator) {
                traslado.handle.setIndexRedituable(i);      // Actualizar índice para heap de redituables
            } else {
                traslado.handle.setIndexTimestamp(i);       // Actualizar índice para heap de timestamps
            }
        } else if (elem instanceof Ciudad) {
            Ciudad ciudad = (Ciudad) elem;
            ciudad.setIndex(i);             // Actualizar el índice de la ciudad
        }
    }

    public int size() {
        return data.size();
    }

    public T obtener(int index) {
        return data.get(index);
    }
}
