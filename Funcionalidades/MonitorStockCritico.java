package Funcionalidades;

import ArbolAVL.diccionarioAVL;
import Clases.Producto;
import Cola_Prioritaria.cola_Prioritaria;

// Funcionalidad: Control de Inventario Critico.
// Usa el AVL para recorrer el inventario y una Cola de Prioridad
// para ordenar los productos segun su stock (menor stock = mas urgente).
public class MonitorStockCritico {

    private diccionarioAVL inventario;

    public MonitorStockCritico(diccionarioAVL inventario) {
        this.inventario = inventario;
    }

    // Recorre el inventario actual y arma una cola de prioridad nueva
    // usando el stock de cada producto como prioridad.
    private cola_Prioritaria<Producto> construirColaPorStock() {
        cola_Prioritaria<Producto> colaCriticos = new cola_Prioritaria<>();
        Producto[] productos = inventario.obtenerTodos();

        for (int i = 0; i < productos.length; i++) {
            colaCriticos.encolar(productos[i], productos[i].getStock());
        }

        return colaCriticos;
    }

    // Devuelve el producto con menor stock actual, o null si no hay productos.
    public Producto obtenerProductoMasCritico() {
        cola_Prioritaria<Producto> colaCriticos = construirColaPorStock();

        if (colaCriticos.estaVacia()) {
            return null;
        }

        return colaCriticos.verPrimero();
    }

    // Muestra por consola los n productos con menor stock (los mas criticos primero).
    public void mostrarProductosCriticos(int n) {
        cola_Prioritaria<Producto> colaCriticos = construirColaPorStock();

        if (colaCriticos.estaVacia()) {
            System.out.println("No hay productos cargados en el inventario.");
            return;
        }

        Object[] criticos = colaCriticos.obtenerCriticos(n);

        System.out.println("=== PRODUCTOS CRITICOS (menor stock primero) ===");
        for (int i = 0; i < criticos.length; i++) {
            Producto producto = (Producto) criticos[i];
            System.out.println((i + 1) + ". " + producto);
        }
    }
}
