package repos.Mate_y_Java.Cola;

public interface ICola<T> {

    //Agrega un elemento al final de la cola.
    void encolar(T dato);

    // Elimina y retorna el elemento del frente de la cola.

    T desencolar();

    // Retorna el elemento del frente sin eliminarlo.
    T frente();

    // Indica si la cola no contiene elementos.
    boolean estaVacia();

    // Retorna la cantidad de elementos en la cola.
    int tamanio();

    // Elimina todos los elementos de la cola.
    void vaciar();
}
