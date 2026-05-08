# BestEffort — Sistema de despacho logístico

Trabajo práctico de **Algoritmos y Estructuras de Datos** (FCEN - UBA).

##

Se modela el sistema de despacho de una empresa logística que opera entre ciudades numeradas. Cada **traslado** representa un pedido con:

- ciudad de **origen** y **destino**
- **ganancia neta** que genera para la empresa
- **timestamp** que indica cuándo fue registrado

El sistema debe poder:

- **Registrar** nuevos traslados.
- **Despachar los más redituables** (mayor ganancia neta; desempate por menor id).
- **Despachar los más antiguos** (menor timestamp), para evitar que pedidos viejos queden indefinidamente sin atención.
- **Consultar estadísticas** en tiempo constante: ciudad con mayor superávit, ciudades con mayor ganancia acumulada/pérdida acumulada, ganancia promedio por traslado.

Cada ciudad acumula **ganancia** por los traslados que salen de ella y **pérdida** por los que llegan.

## Solución

Se basa en **dos max-heaps sincronizados** sobre el mismo conjunto de traslados:

- `trasladosRedituable` — ordenado por ganancia neta.
- `trasladosTimestamp` — ordenado por timestamp (más antiguo primero).

Al despachar un traslado desde un heap, hay que eliminarlo también del otro en O(log n). Para esto, cada `TrasladoWrapper` lleva un **Handle**: un objeto que almacena la posición actual del traslado en cada heap. Cada vez que un elemento se mueve durante un `siftUp` o `siftDown`, su handle se actualiza en el momento. Esto permite localizar y eliminar el elemento en el otro heap sin necesidad de buscarlo.

Las ciudades se mantienen en un tercer heap ordenado por superávit, lo que permite consultar la ciudad con mayor superávit en O(1).

El `Heap<T>` es una implementación propia sobre `ArrayList`.

### Clases principales

| Clase                  | Responsabilidad                                                     |
| ---------------------- | ------------------------------------------------------------------- |
| `BestEffort`           | Lógica principal del sistema                                        |
| `Heap<T>`              | Max-heap con soporte para handles                                   |
| `Handle`               | Índices de un traslado en ambos heaps                               |
| `TrasladoWrapper`      | Traslado + su handle asociado                                       |
| `Ciudad`               | Estado de una ciudad (ganancia, pérdida, superávit, índice en heap) |
| `CiudadComparator`     | Ordena ciudades por superávit (desempate por menor id)              |
| `RedituableComparator` | Ordena traslados por ganancia (desempate por menor id)              |
| `TimestampComparator`  | Ordena traslados por timestamp                                      |

## Complejidades

| Operación                             | Complejidad                       |
| ------------------------------------- | --------------------------------- |
| `BestEffort(cantCiudades, traslados)` | O(\|C\| + \|T\|) — heapify lineal |
| `registrarTraslados(traslados)`       | O(\|traslados\| · log\|T\|)       |
| `despacharMasRedituables(n)`          | O(n · (log\|T\| + log\|C\|))      |
| `despacharMasAntiguos(n)`             | O(n · (log\|T\| + log\|C\|))      |
| `ciudadConMayorSuperavit()`           | O(1)                              |
| `ciudadesConMayorGanancia()`          | O(1)                              |
| `ciudadesConMayorPerdida()`           | O(1)                              |
| `gananciaPromedioPorTraslado()`       | O(1)                              |

## Tests

Desde la terminal con Maven

```bash
mvn test
```

## Stack

- Java
- JUnit
- Maven
