package Grafo;

import Cola.cola;

// Implementacion del grafo del almacen mediante lista de adyacencia.
// Cada vertice es un pasillo; cada arista es una conexion directa entre pasillos.
// Se usa BFS para encontrar la ruta minima entre dos pasillos.
public class grafoAlmacen implements iGrafo {

    private nodoVertice primero;

    public grafoAlmacen() {
        this.primero = null;
    }

    // Busca y devuelve el nodoVertice del pasillo dado, o null si no existe.
    private nodoVertice buscarVertice(String pasillo) {
        nodoVertice actual = primero;
        while (actual != null) {
            if (actual.pasillo.equals(pasillo)) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public boolean existeVertice(String pasillo) {
        return buscarVertice(pasillo) != null;
    }

    @Override
    public void insertarVertice(String pasillo) {
        if (existeVertice(pasillo)) {
            System.out.println("El pasillo '" + pasillo + "' ya existe en el grafo.");
            return;
        }

        nodoVertice nuevo = new nodoVertice(pasillo);

        // Se inserta al final de la lista de vertices
        if (primero == null) {
            primero = nuevo;
        } else {
            nodoVertice actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // Agrega un adyacente a la lista de conexiones de un vertice.
    private void agregarAdyacente(String origen, String destino) {
        nodoVertice verticeOrigen = buscarVertice(origen);
        if (verticeOrigen == null) return;

        // Evitar duplicados
        if (existeArista(origen, destino)) return;

        nodoAdyacente nuevo = new nodoAdyacente(destino);

        if (verticeOrigen.adyacentes == null) {
            verticeOrigen.adyacentes = nuevo;
        } else {
            nodoAdyacente actual = verticeOrigen.adyacentes;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    @Override
    public void insertarArista(String origen, String destino) {
        // Si alguno de los pasillos no existe, se crea automaticamente
        if (!existeVertice(origen)) insertarVertice(origen);
        if (!existeVertice(destino)) insertarVertice(destino);

        // Grafo no dirigido: la conexion va en ambas direcciones
        agregarAdyacente(origen, destino);
        agregarAdyacente(destino, origen);
    }

    @Override
    public boolean existeArista(String origen, String destino) {
        nodoVertice verticeOrigen = buscarVertice(origen);
        if (verticeOrigen == null) return false;

        nodoAdyacente actual = verticeOrigen.adyacentes;
        while (actual != null) {
            if (actual.pasillo.equals(destino)) return true;
            actual = actual.siguiente;
        }
        return false;
    }

    // Quita un adyacente de la lista de conexiones de un vertice.
    private void quitarAdyacente(String origen, String destino) {
        nodoVertice verticeOrigen = buscarVertice(origen);
        if (verticeOrigen == null || verticeOrigen.adyacentes == null) return;

        nodoAdyacente actual = verticeOrigen.adyacentes;
        nodoAdyacente anterior = null;

        while (actual != null) {
            if (actual.pasillo.equals(destino)) {
                if (anterior == null) {
                    verticeOrigen.adyacentes = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    @Override
    public void eliminarArista(String origen, String destino) {
        quitarAdyacente(origen, destino);
        quitarAdyacente(destino, origen);
    }

    // Elimina todas las referencias al vertice dado en las listas de adyacencia.
    private void eliminarReferencias(String pasillo) {
        nodoVertice actual = primero;
        while (actual != null) {
            quitarAdyacente(actual.pasillo, pasillo);
            actual = actual.siguiente;
        }
    }

    @Override
    public void eliminarVertice(String pasillo) {
        if (primero == null) return;

        nodoVertice actual = primero;
        nodoVertice anterior = null;

        while (actual != null) {
            if (actual.pasillo.equals(pasillo)) {
                if (anterior == null) {
                    primero = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                // Elimina este pasillo de las listas de adyacencia de todos los demas
                eliminarReferencias(pasillo);
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        System.out.println("El pasillo '" + pasillo + "' no existe.");
    }

    // Pone todos los flags de visitado en false antes de un nuevo recorrido.
    private void limpiarVisitados() {
        nodoVertice actual = primero;
        while (actual != null) {
            actual.visitado = false;
            actual = actual.siguiente;
        }
    }

    // Cuenta cuantos vertices tiene el grafo (para dimensionar arrays internos).
    private int contarVertices() {
        int count = 0;
        nodoVertice actual = primero;
        while (actual != null) {
            count++;
            actual = actual.siguiente;
        }
        return count;
    }

    @Override
    public void mostrarGrafo() {
        if (primero == null) {
            System.out.println("El grafo del almacen esta vacio.");
            return;
        }

        System.out.println("=== MAPA DEL ALMACEN (pasillos y conexiones) ===");
        nodoVertice actual = primero;
        while (actual != null) {
            System.out.print("  " + actual.pasillo + " -> ");
            nodoAdyacente ady = actual.adyacentes;
            if (ady == null) {
                System.out.print("[sin conexiones]");
            }
            while (ady != null) {
                System.out.print(ady.pasillo);
                if (ady.siguiente != null) System.out.print(", ");
                ady = ady.siguiente;
            }
            System.out.println();
            actual = actual.siguiente;
        }
    }

    @Override
    public void recorridosDFS(String inicio) {
        limpiarVisitados();
        nodoVertice verticeInicio = buscarVertice(inicio);
        if (verticeInicio == null) {
            System.out.println("El pasillo '" + inicio + "' no existe.");
            return;
        }
        System.out.print("Recorrido DFS desde '" + inicio + "': ");
        dfsRecursivo(verticeInicio);
        System.out.println();
    }

    private void dfsRecursivo(nodoVertice vertice) {
        vertice.visitado = true;
        System.out.print(vertice.pasillo + " ");

        nodoAdyacente ady = vertice.adyacentes;
        while (ady != null) {
            nodoVertice vecino = buscarVertice(ady.pasillo);
            if (vecino != null && !vecino.visitado) {
                dfsRecursivo(vecino);
            }
            ady = ady.siguiente;
        }
    }

    @Override
    public void recorridosBFS(String inicio) {
        limpiarVisitados();
        nodoVertice verticeInicio = buscarVertice(inicio);
        if (verticeInicio == null) {
            System.out.println("El pasillo '" + inicio + "' no existe.");
            return;
        }

        cola<String> cola = new cola<>();
        verticeInicio.visitado = true;
        cola.encolar(inicio);

        System.out.print("Recorrido BFS desde '" + inicio + "': ");
        while (!cola.estaVacia()) {
            String actual = cola.desencolar();
            System.out.print(actual + " ");

            nodoVertice verticeActual = buscarVertice(actual);
            nodoAdyacente ady = verticeActual.adyacentes;
            while (ady != null) {
                nodoVertice vecino = buscarVertice(ady.pasillo);
                if (vecino != null && !vecino.visitado) {
                    vecino.visitado = true;
                    cola.encolar(ady.pasillo);
                }
                ady = ady.siguiente;
            }
        }
        System.out.println();
    }

    // Usa BFS para encontrar la ruta mas corta entre origen y destino.
    // Guarda de donde vino cada vertice visitado en un array paralelo (padre[]).
    // Al llegar al destino, reconstruye el camino recorriendo los padres hacia atras.
    @Override
    public String[] rutaMasCorta(String origen, String destino) {
        if (!existeVertice(origen) || !existeVertice(destino)) {
            System.out.println("Uno de los pasillos no existe en el grafo.");
            return null;
        }

        if (origen.equals(destino)) {
            return new String[]{origen};
        }

        limpiarVisitados();

        int n = contarVertices();

        // Arrays paralelos para BFS con reconstruccion de camino
        String[] visitados = new String[n];
        String[] padre = new String[n];
        int count = 0;

        cola<String> cola = new cola<>();
        nodoVertice verticeInicio = buscarVertice(origen);
        verticeInicio.visitado = true;
        cola.encolar(origen);

        visitados[count] = origen;
        padre[count] = null;
        count++;

        boolean encontrado = false;

        while (!cola.estaVacia() && !encontrado) {
            String actual = cola.desencolar();

            nodoVertice verticeActual = buscarVertice(actual);
            nodoAdyacente ady = verticeActual.adyacentes;

            while (ady != null) {
                nodoVertice vecino = buscarVertice(ady.pasillo);

                if (vecino != null && !vecino.visitado) {
                    vecino.visitado = true;
                    cola.encolar(ady.pasillo);

                    visitados[count] = ady.pasillo;
                    // El padre de este vecino es el nodo actual
                    padre[count] = actual;
                    count++;

                    if (ady.pasillo.equals(destino)) {
                        encontrado = true;
                        break;
                    }
                }
                ady = ady.siguiente;
            }
        }

        if (!encontrado) {
            System.out.println("No existe ruta entre '" + origen + "' y '" + destino + "'.");
            return null;
        }

        // Reconstruir el camino desde destino hasta origen usando el array padre
        // Se guarda en un array temporal y luego se invierte
        String[] temporal = new String[n];
        int largo = 0;
        String paso = destino;

        while (paso != null) {
            temporal[largo] = paso;
            largo++;

            // Buscar el padre de este paso
            String padreDelPaso = null;
            for (int i = 0; i < count; i++) {
                if (visitados[i].equals(paso)) {
                    padreDelPaso = padre[i];
                    break;
                }
            }
            paso = padreDelPaso;
        }

        // Invertir: el camino esta de destino a origen, lo queremos de origen a destino
        String[] ruta = new String[largo];
        for (int i = 0; i < largo; i++) {
            ruta[i] = temporal[largo - 1 - i];
        }

        return ruta;
    }
}
