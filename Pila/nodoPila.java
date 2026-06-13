package Pila;

public class NodoPila<T> {

    //Dato almacenado en este nodo
    private T dato;

    // Referencia al nodo que está debajo en la pila (null si es el fondo).
    private NodoPila<T> siguiente;

    // Constructor para crear un nuevo nodo con el dato dado y sin siguiente.
    public NodoPila(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoPila<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila<T> siguiente) {
        this.siguiente = siguiente;
    }
}
