package Funcionalidades;

import ArbolAVL.diccionarioAVL;
import Clases.Producto;
import Cola.cola;
import Grafo.grafoAlmacen;

public class gestorInventario {

    private diccionarioAVL inventario;

    public gestorInventario(diccionarioAVL inventario) {
        this.inventario = inventario;
    }

    // Valida el codigo antes insertar un nuevo nodo
    public Producto agregarProducto(String codigo, String nombre, String ubicacion, int stock, String lote) {
        if (inventario.contiene(codigo)) {
            System.out.println("Error: Ya existe un producto registrado con el codigo [" + codigo + "].");
            return null;
        }

        Producto nuevo = new Producto(codigo, nombre, ubicacion, stock, lote);
        inventario.insertar(codigo, nuevo);
        return nuevo;
    }

    public void eliminarProducto(String codigo) {
        inventario.eliminar(codigo);
    }

    // Busca donde se encuentra el producto utilizando la clave
    public Producto buscarProducto(String codigo) {
        return inventario.buscar(codigo);
    }

    public boolean existeProducto(String codigo) {
        return inventario.contiene(codigo);
    }

    /**
     * Valida de manera segura si un articulo cuenta con disponibilidad suficiente para un pedido.
     * Actúa como filtro preventivo antes de procesar despachos.
     */
    public boolean hayStockSuficiente(String codigo, int cantidadRequerida) {
        Producto prod = inventario.buscar(codigo);
        if (prod == null) {
            System.out.println("Error: El producto [" + codigo + "] no esta registrado.");
            return false;
        }
        if (prod.getStock() < cantidadRequerida) {
            System.out.println("Alerta: Stock insuficiente para [" + codigo + "]. Disponible: " + prod.getStock());
            return false;
        }
        return true;
    }

    // Actualiza el stock de un producto.
    public boolean actualizarStock(String codigo, int cantidad) {
        Producto producto = inventario.buscar(codigo);

        if (producto == null) {
            return false;
        }

        int nuevoStock = producto.getStock() + cantidad;

        // Validación de robustez matemática para evitar inventarios inconsistentes
        if (nuevoStock < 0) {
            System.out.println("Error: no se puede retirar mas stock del disponible (" + producto.getStock() + ").");
            return false;
        }

        producto.setStock(nuevoStock);
        return true;
    }

    // Despliega los datos aprovechando el recorrido in-order (alfabético) del AVL
    public void mostrarInventario() {
        inventario.mostrarInventarioOrdenado();
    }

    public int cantidadProductos() {
        return inventario.tamanio();
    }

    // Comprueba el estado de la raiz del AVL 
    public boolean inventarioVacio() {
        return inventario.estaVacio();
    }

    // --- INTEGRACIÓN: Mapa de Stock por Pasillo y Ruta BFS ---
    public void mapaDeStockPorPasillo(String pasilloDestino, grafoAlmacen mapa) {
        
        // 1. Validar si el pasillo existe en el mapa físico
        if (!mapa.existeVertice(pasilloDestino)) {
            System.out.println("Error: El pasillo [" + pasilloDestino + "] no existe en el mapa del depósito.");
            return;
        }

        // 2. Calcular e imprimir la ruta desde la Entrada
        System.out.println("=== RUTA DE PICKING ÓPTIMA ===");
        // Si no existe el nodo "Entrada", lo creamos temporalmente para evitar que el sistema caiga
        if (!mapa.existeVertice("Entrada")) {
            System.out.println("Aviso: Nodo 'Entrada' no configurado. Mostrando solo inventario...");
        } else {
            String[] ruta = mapa.rutaMasCorta("Entrada", pasilloDestino);
            if (ruta != null) {
                System.out.print("Recorrido a seguir: ");
                for (int i = 0; i < ruta.length; i++) {
                    System.out.print("[" + ruta[i] + "]");
                    if (i < ruta.length - 1) System.out.print(" -> ");
                }
                System.out.println("\n");
            }
        }

        // 3. Buscar los productos dentro del AVL usando la Cola
        cola<Producto> inventarioLocal = inventario.buscarPorUbicacion(pasilloDestino);

        System.out.println("=== INVENTARIO EN " + pasilloDestino.toUpperCase() + " ===");
        if (inventarioLocal.estaVacia()) {
            System.out.println("El pasillo se encuentra actualmente sin productos asignados.");
        } else {
            while (!inventarioLocal.estaVacia()) {
                Producto prod = inventarioLocal.desencolar();
                System.out.println("- Cód: " + prod.getCodigo() + " | " + prod.getNombre() + " | Stock disponible: " + prod.getStock());
            }
        }
    }
}