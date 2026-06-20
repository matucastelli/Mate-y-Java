package Cola;

public class nodoCola<T> {

    // Dato almacenado en este nodo.
    private T dato;

    // Referencia al siguiente nodo en la cola (null si es el último).
    private nodoCola<T> siguiente;

    //Constructor del nodo.
    public nodoCola(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public nodoCola<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodoCola<T> siguiente) {
        this.siguiente = siguiente;
    }
}
