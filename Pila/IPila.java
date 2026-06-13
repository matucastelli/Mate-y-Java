package Pila;

public interface IPila<T> {
    // Apila un elemento en el tope de la pila
    void apilar(T dato);

    // Elimina y retorna el elemento del tope de la pila.
    T desapilar();

    // Retorna el elemento del tope sin eliminarlo.
    T tope();

    // Indica si la pila no contiene elementos.
    boolean estaVacia();

    // Retorna la cantidad de elementos en la pila
    int tamanio();

    // Elimina todos los elementos de la pila.
    void vaciar();
}
