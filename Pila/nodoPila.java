package Pila;

public class nodoPila<T> {

    //Dato almacenado en este nodo
    private T dato;

    // Referencia al nodo que está debajo en la pila (null si es el fondo).
    private nodoPila<T> siguiente;

    // Constructor para crear un nuevo nodo con el dato dado y sin siguiente.
    public nodoPila(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public nodoPila<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodoPila<T> siguiente) {
        this.siguiente = siguiente;
    }
}
