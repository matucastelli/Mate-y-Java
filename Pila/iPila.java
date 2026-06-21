package Pila;

public interface iPila<T> {

    //Apila un elemento en el tope de la pila.

    void apilar(T dato);

    //elimina todos los elementos de la pila, dejándola vacía.

    void vaciar();

    //elimina y retorna el elemento del tope de la pila. Si la pila está vacía, retorna null.

    T desapilar();

    //retorna el elemento del tope de la pila sin eliminarlo. Si la pila está vacía, retorna null.

    T tope();

    //indica si la pila no tiene elementos. Retorna true si la pila está vacía, false en caso contrario.

    boolean estaVacia();

    //retorna la cantidad de elementos en la pila.

    int tamanio();
}