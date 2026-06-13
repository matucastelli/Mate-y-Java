package repos.Mate_y_Java.Cola_Prioritaria;

// Interfaz del TDA Cola de Prioridad.
// Define las operaciones que debe tener cualquier implementacion.
public interface IColaPrioridad<T> {

    // Inserta un dato segun su prioridad (a menor numero, mas urgente por defecto).
    void encolar(T dato, int prioridad);

    // Devuelve el dato mas prioritario sin sacarlo de la cola.
    T verPrimero();

    // Devuelve la prioridad del dato mas prioritario.
    int verPrimeraPrioridad();

    // Saca y devuelve el dato mas prioritario.
    T desencolar();

    // Devuelve los n elementos mas prioritarios sin sacarlos.
    // Se devuelve Object[] (no T[]) para evitar el ClassCastException
    // que ocurre al castear un array generico creado con new Object[].
    Object[] obtenerCriticos(int n);

    // Cambia la prioridad de un dato ya existente y lo reordena.
    void actualizarPrioridad(T dato, int nuevaPrioridad);

    // Cantidad de elementos en la cola.
    int tamanio();

    // Indica si la cola esta vacia.
    boolean estaVacia();
}
