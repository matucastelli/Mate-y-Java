package repos.Mate_y_Java.Pila;

public class Pila<T> implements IPila<T> {

    //* Referencia al nodo en el tope de la pila. null si la pila está vacía.
    private NodoPila<T> tope;

    //* Cantidad de elementos actualmente apilados.
    private int tamanio;

    // Constructor. Crea una pila vacía.
    public Pila() {
        this.tope = null;
        this.tamanio = 0;
    }

    /**
     * Apila un elemento en el tope. O(1).
     * El nuevo nodo pasa a ser el tope y apunta al nodo que era tope antes.
     */
    @Override
    public void apilar(T dato) {
        if (dato == null) {
            System.out.println("No se puede apilar un dato nulo.");
            return;
        }
        NodoPila<T> nuevoNodo = new NodoPila<>(dato);
        // El nuevo nodo apunta al tope actual
        nuevoNodo.setSiguiente(tope);
        // El nuevo nodo pasa a ser el tope
        tope = nuevoNodo;
        tamanio++;
    }

    /**
     * Elimina y retorna el elemento del tope. O(1).
     * El tope pasa a ser el nodo que estaba debajo.
     */
    @Override
    public T desapilar() {
        if (estaVacia()) {
            System.out.println("No se puede desapilar: la pila está vacía.");
            return null;
        }
        // Guardamos el dato del tope para retornarlo
        T datoDesapilado = tope.getDato();
        // El tope pasa a ser el nodo siguiente
        tope = tope.getSiguiente();
        tamanio--;
        return datoDesapilado;
    }

    /**
     * Retorna el dato del tope sin eliminarlo. O(1).
     */
    @Override
    public T tope() {
        if (estaVacia()) {
            System.out.println("No se puede consultar el tope: la pila está vacía.");
            return null;
        }
        return tope.getDato();
    }

    // Indica si la pila no tiene elementos. O(1).
    @Override
    public boolean estaVacia() {
        return tope == null;
    }

    // Retorna la cantidad de elementos en la pila. O(1).
    @Override
    public int tamanio() {
        return tamanio;
    }

    // Elimina todos los elementos de la pila. O(1).
    @Override
    public void vaciar() {
        tope = null;
        tamanio = 0;
    }
}
