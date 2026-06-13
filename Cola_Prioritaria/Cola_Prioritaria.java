package Cola_Prioritaria;

// Implementacion de la Cola de Prioridad usando una lista enlazada ordenada.
// El elemento mas prioritario siempre queda al frente de la lista.
public class cola_Prioritaria<T> implements iColaPrioridad<T> {

    // Nodo de la lista: guarda el dato, su prioridad y el siguiente nodo.
    private class NodoPrioridad {
        T dato;
        int prioridad;
        NodoPrioridad siguiente;

        NodoPrioridad(T dato, int prioridad) {
            this.dato = dato;
            this.prioridad = prioridad;
            this.siguiente = null;
        }
    }

    private NodoPrioridad frente;
    private int cantidad;
    private boolean menorEsPrioritario; // true = el numero mas chico es el mas urgente

    // Por defecto, menor numero = mayor prioridad (ej: menor stock = mas urgente).
    public cola_Prioritaria() {
        this.frente = null;
        this.cantidad = 0;
        this.menorEsPrioritario = true;
    }

    // Permite invertir el criterio: mayor numero = mayor prioridad.
    public cola_Prioritaria(boolean menorEsPrioritario) {
        this.frente = null;
        this.cantidad = 0;
        this.menorEsPrioritario = menorEsPrioritario;
    }

    @Override
    public int tamanio() {
        return cantidad;
    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    // Compara dos prioridades segun el criterio elegido (mayor/menor primero).
    private boolean tieneMayorPrioridad(int prioridadA, int prioridadB) {
        if (menorEsPrioritario) {
            return prioridadA < prioridadB;
        } else {
            return prioridadA > prioridadB;
        }
    }

    // Igual que la anterior, pero permite empate (sirve para insertar al final de iguales).
    private boolean tieneIgualOMayorPrioridad(int prioridadA, int prioridadB) {
        if (menorEsPrioritario) {
            return prioridadA <= prioridadB;
        } else {
            return prioridadA >= prioridadB;
        }
    }

    // Inserta el nuevo nodo en la posicion que le corresponde segun su prioridad.
    @Override
    public void encolar(T dato, int prioridad) {
        NodoPrioridad nuevo = new NodoPrioridad(dato, prioridad);

        // Caso 1: la cola esta vacia o el nuevo dato va primero
        if (frente == null || tieneMayorPrioridad(prioridad, frente.prioridad)) {
            nuevo.siguiente = frente;
            frente = nuevo;
            cantidad++;
            return;
        }

        // Caso 2: se recorre hasta encontrar el lugar correcto
        NodoPrioridad actual = frente;
        while (actual.siguiente != null
                && tieneIgualOMayorPrioridad(actual.siguiente.prioridad, prioridad)) {
            actual = actual.siguiente;
        }

        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
        cantidad++;
    }

    @Override
    public T verPrimero() {
        if (estaVacia()) {
            throw new RuntimeException("La cola de prioridad esta vacia");
        }
        return frente.dato;
    }

    @Override
    public int verPrimeraPrioridad() {
        if (estaVacia()) {
            throw new RuntimeException("La cola de prioridad esta vacia");
        }
        return frente.prioridad;
    }

    // Saca el primer nodo (el mas prioritario) y avanza el frente.
    @Override
    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("La cola de prioridad esta vacia");
        }

        T dato = frente.dato;
        frente = frente.siguiente;
        cantidad--;
        return dato;
    }

    // Recorre los primeros n nodos y los devuelve en un array, sin sacarlos.
    // Se devuelve Object[] para evitar el ClassCastException de los arrays genericos.
    @Override
    public Object[] obtenerCriticos(int n) {
        int cantidadAResolver = n;
        if (cantidadAResolver > cantidad) {
            cantidadAResolver = cantidad;
        }

        Object[] resultado = new Object[cantidadAResolver];
        NodoPrioridad actual = frente;

        for (int i = 0; i < cantidadAResolver; i++) {
            resultado[i] = actual.dato;
            actual = actual.siguiente;
        }

        return resultado;
    }

    // Busca el nodo por su dato, lo saca de la lista y lo reinserta con la nueva prioridad.
    @Override
    public void actualizarPrioridad(T dato, int nuevaPrioridad) {
        if (frente == null) {
            throw new RuntimeException("El elemento no se encuentra en la cola");
        }

        // Caso 1: el dato esta en el primer nodo
        if (frente.dato.equals(dato)) {
            frente = frente.siguiente;
            cantidad--;
            encolar(dato, nuevaPrioridad);
            return;
        }

        // Caso 2: el dato esta mas adelante en la lista
        NodoPrioridad actual = frente;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(dato)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente == null) {
            throw new RuntimeException("El elemento no se encuentra en la cola");
        }

        actual.siguiente = actual.siguiente.siguiente;
        cantidad--;
        encolar(dato, nuevaPrioridad);
    }

    // Muestra la cola completa de frente a fin, util para pruebas.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        NodoPrioridad actual = frente;

        while (actual != null) {
            sb.append("(").append(actual.dato).append(", prio=").append(actual.prioridad).append(")");
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }

        sb.append("]");
        return sb.toString();
    }
}
