package repos.Mate_y_Java.Cola;

public class NodoCola<T> {

    // Dato almacenado en este nodo.
    private T dato;

    // Referencia al siguiente nodo en la cola (null si es el último).
    private NodoCola<T> siguiente;

    //Constructor del nodo.
    public NodoCola(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoCola<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola<T> siguiente) {
        this.siguiente = siguiente;
    }
}
