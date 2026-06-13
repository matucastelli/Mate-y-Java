package Cola;

public class Cola<T> implements iCola<T> {

    // Referencia al primer nodo (el que saldrá primero).
    private nodoCola<T> frente;
    // Referencia al último nodo (donde se encola el siguiente).
    private nodoCola<T> fin;
    // Cantidad de elementos actualmente en la cola.
    private int tamanio;

    // Constructor. Crea una cola vacía.
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // Agrega un elemento al final de la cola. O(1).
    @Override
    public void encolar(T dato) {
        if (dato == null) {
            System.out.println("No se puede encolar un dato nulo.");
            return;
        }

        nodoCola<T> nuevoNodo = new nodoCola<>(dato);

        if (estaVacia()) {
            // Si la cola estaba vacía, frente y fin apuntan al mismo nodo
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            // El nodo que era el último ahora apunta al nuevo
            fin.setSiguiente(nuevoNodo);
            // El nuevo nodo pasa a ser el último
            fin = nuevoNodo;
        }
        tamanio++;
    }

    /**
     * Elimina y retorna el elemento del frente. O(1).
     * El frente pasa a ser el nodo siguiente
     */
    @Override
    public T desencolar() {
        if (estaVacia()) {
            System.out.println("No se puede desencolar: la cola está vacía.");
            return null;
        }

        // Guardamos el dato del frente para retornarlo
        T datoDesencolado = frente.getDato();
        // El frente pasa a ser el nodo siguiente
        frente = frente.getSiguiente();

        // Si la cola quedó vacía, fin también debe ser null
        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return datoDesencolado;
    }

    // Retorna el dato del frente sin eliminarlo. O(1).
    @Override
    public T frente() {
        if (estaVacia()) {
            System.out.println("No se puede consultar el frente: la cola está vacía.");
            return null;
        }
        return frente.getDato();
    }

    // Indica si la cola no tiene elementos.
    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    // Retorna la cantidad de elementos en la cola.
    @Override
    public int tamanio() {
        return tamanio;
    }

    // Elimina todos los elementos de la cola. O(1).
    @Override
    public void vaciar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }
}
