package Funcionalidades;

import ArbolAVL.DiccionarioAVL;
import Clases.Movimiento;
import Clases.Producto;
import Pila.Pila;

// Funcionalidad: Trazabilidad de Lotes con Undo.
// Usa una Pila para registrar los movimientos de stock y poder
// deshacer el ultimo movimiento aplicado al inventario (AVL).
public class RegistroTrazabilidad {

    public static final String INGRESO = "INGRESO";
    public static final String EGRESO = "EGRESO";

    private DiccionarioAVL inventario;
    private Pila<Movimiento> historial;

    public RegistroTrazabilidad(DiccionarioAVL inventario) {
        this.inventario = inventario;
        this.historial = new Pila<>();
    }

    // Registra un movimiento de stock y lo apila en el historial.
    public void registrarMovimiento(String tipo, String codigoProducto, int cantidad, String usuario) {
        Movimiento movimiento = new Movimiento(tipo, codigoProducto, cantidad, usuario);
        historial.apilar(movimiento);
    }

    // Deshace el ultimo movimiento registrado, revirtiendo el stock en el inventario.
    public boolean deshacerUltimoMovimiento() {
        if (historial.estaVacia()) {
            System.out.println("No hay movimientos para deshacer.");
            return false;
        }

        Movimiento ultimo = historial.desapilar();
        Producto producto = inventario.buscar(ultimo.getCodigoProducto());

        if (producto == null) {
            System.out.println("No se puede deshacer: el producto [" + ultimo.getCodigoProducto() + "] ya no existe.");
            return false;
        }

        // Un ingreso se deshace restando esa cantidad; un egreso, sumandola de nuevo.
        if (ultimo.getTipo().equals(INGRESO)) {
            producto.setStock(producto.getStock() - ultimo.getCantidad());
        } else if (ultimo.getTipo().equals(EGRESO)) {
            producto.setStock(producto.getStock() + ultimo.getCantidad());
        }

        System.out.println("Se deshizo: " + ultimo);
        return true;
    }

    // Muestra el historial completo, del movimiento mas reciente al mas antiguo.
    // Usa una pila auxiliar para recorrer sin perder el orden original.
    public void mostrarHistorial() {
        if (historial.estaVacia()) {
            System.out.println("El historial de movimientos esta vacio.");
            return;
        }

        Pila<Movimiento> auxiliar = new Pila<>();

        System.out.println("=== HISTORIAL DE MOVIMIENTOS (mas reciente primero) ===");
        while (!historial.estaVacia()) {
            Movimiento movimiento = historial.desapilar();
            System.out.println(movimiento);
            auxiliar.apilar(movimiento);
        }

        // Se restaura el historial a su orden original
        while (!auxiliar.estaVacia()) {
            historial.apilar(auxiliar.desapilar());
        }
    }

    public int tamanioHistorial() {
        return historial.tamanio();
    }
}
