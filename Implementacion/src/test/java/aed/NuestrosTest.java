package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class NuestrosTest {
    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 6, 3000, 10), 
                                            new Traslado(2, 1, 6, 3000, 20),
                                            new Traslado(3, 6, 5, 500, 50),
                                            new Traslado(4, 3, 6, 2000, 11),
                                            new Traslado(5, 4, 6, 2000, 40),
                                            new Traslado(6, 5, 6, 1000, 41),
                                            new Traslado(7, 6, 3, 400, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    
    @Test 
    void despachar_con_heap_vacio(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasRedituables(this.listaTraslados.length);

        int[] despacharConHeapVacio = sis.despacharMasRedituables(1);

        assertEquals(despacharConHeapVacio.length, 0);
    }


    @Test 
    void se_elimina_del_otro_heap_al_despachar(){

        //Caso 1: Despachamos todos los traslados por un criterio, luego si queremos despachar por el otro deberia responder []
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(this.listaTraslados.length);
        int[] resAntiguos = sis.despacharMasAntiguos(this.listaTraslados.length);

        sis.registrarTraslados(listaTraslados);
        sis.despacharMasAntiguos(this.listaTraslados.length);

        int[] resRedituables = sis.despacharMasRedituables(this.listaTraslados.length);

        // Caso 2: Despachamos solo algunos

        sis.registrarTraslados(listaTraslados);
        sis.despacharMasAntiguos(3);
        int[] res = sis.despacharMasRedituables(this.listaTraslados.length);

        assertEquals(resAntiguos.length, 0);
        assertEquals(resRedituables.length, 0);
        assertEquals(res.length, (this.listaTraslados.length - 3));
    }

    @Test 
    void order_correcto_al_despachar_antiguos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        int[] res = sis.despacharMasAntiguos(this.listaTraslados.length);

        //Inicia el array con esos valores
        int[] esperado = {1,4,2,5,6,7,3}; 
        assertArrayEquals(res,esperado);
    }

    @Test 
    void order_correcto_al_despachar_redituables(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        int[] res = sis.despacharMasRedituables(this.listaTraslados.length);

        //Inicia el array con esos valores
        int[] esperado = {1,2,4,5,6,3,7};
        assertArrayEquals(res,esperado);
    }

    @Test
    void despachar_con_misma_ganancia_pero_desempata_por_id(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        //Traslado 1 y 2 con misma ganancia pero es mas por desempate es mas redituable el 1 por tener id menor
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(sis.despacharMasRedituables(1)[0])));

        //Pasa lo mismo pero entre el traslado 4 y 5
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), new ArrayList<>(Arrays.asList(sis.despacharMasRedituables(2)[1])));        
    }

    @Test
    void ciudad_con_mayor_superavit(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);

        //Empate con ciudad 0 y 1 pero gana el 0 por tener menor id
        assertEquals(0, sis.ciudadConMayorSuperavit());
    }

    @Test 
    void ciudades_mayores_ganancias_y_perdidas(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(3);

        ArrayList<Integer> resGanancias = new ArrayList<Integer>();
        resGanancias.add(0);
        resGanancias.add(1); 
        ArrayList<Integer> resPerdidas = new ArrayList<Integer>();
        resPerdidas.add(6);

        assertEquals(sis.ciudadesConMayorGanancia(), resGanancias);
        assertEquals(sis.ciudadesConMayorPerdida(), resPerdidas);
    }

    @Test
    void despachar_mayor_a_los_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos un numero mas grande que la cantidad de traslados actuales
        int[] respuesta = sis.despacharMasRedituables(20);
        ArrayList<Integer> lista = new ArrayList<>();
        
        // Agregamos los elementos
        for(int i = 0; i < respuesta.length; i++){
            lista.add(respuesta[i]);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7)), lista);        
    }
}
