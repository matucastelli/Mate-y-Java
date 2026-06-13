package repos.Mate_y_Java.ArbolAVL;

import repos.Mate_y_Java.Clases.Producto;

public interface DiccionarioAVLTDA {

    // Inserta un producto en el árbol usando su código como clave.
    void insertar(String clave, Producto valor);

    // Busca un producto por su clave.
    Producto buscar(String clave);

    // Elimina un producto del árbol a partir de su clave (código universal).
    void eliminar(String clave);

    // Verifica si una clave específica ya se encuentra registrada en el árbol.
    boolean contiene(String clave);

    // Retorna la cantidad total de productos actualmente almacenados en el árbol.
    int tamanio();

    // Muestra todo el inventario de productos ordenado alfabéticamente por código.
    void mostrarInventarioOrdenado();

    // Verifica si el árbol no tiene elementos.
    boolean estaVacio();

    // Devuelve todos los productos del árbol ordenados por código (recorrido in-order).
    // Se usa para construir la Cola de Prioridad del Monitor de Stock Critico.
    Producto[] obtenerTodos();
}
