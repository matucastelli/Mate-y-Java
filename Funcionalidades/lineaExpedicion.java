package Funcionalidades;

import Clases.Producto;
import Cola.cola;

// Funcionalidad: Linea de Expedicion.
// Los pedidos listos para despacho esperan en una cola FIFO.
// El operario los carga al camion en el orden en que llegaron.
public class lineaExpedicion {

    // Cada pedido tiene un id, el producto a despachar y la cantidad.
    public static class Pedido {
        private static int contadorId = 1;
        private int id;
        private Producto producto;
        private int cantidad;

        public Pedido(Producto producto, int cantidad) {
            this.id = contadorId++;
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public int getId() { return id; }
        public Producto getProducto() { return producto; }
        public int getCantidad() { return cantidad; }

        @Override
        public String toString() {
            return "Pedido#" + id + " [" + producto.getCodigo() + " - " + producto.getNombre() + " x" + cantidad + "]";
        }
    }

    private cola<Pedido> colaExpedicion;

    public lineaExpedicion() {
        this.colaExpedicion = new cola<>();
    }

    // Agrega un pedido al final de la linea de expedicion.
    public Pedido agregarPedido(Producto producto, int cantidad) {
        if (producto == null) {
            System.out.println("Error: el producto no puede ser nulo.");
            return null;
        }
        if (cantidad <= 0) {
            System.out.println("Error: la cantidad debe ser mayor a cero.");
            return null;
        }
        Pedido pedido = new Pedido(producto, cantidad);
        colaExpedicion.encolar(pedido);
        System.out.println("Pedido encolado: " + pedido);
        return pedido;
    }

    // Despacha el proximo pedido (el que llego primero) y lo retorna.
    public Pedido despacharProximo() {
        if (colaExpedicion.estaVacia()) {
            System.out.println("No hay pedidos en la linea de expedicion.");
            return null;
        }
        Pedido despachado = colaExpedicion.desencolar();
        System.out.println("Despachado al camion: " + despachado);
        return despachado;
    }

    // Muestra el pedido que esta primero sin despacharlo.
    public Pedido verProximo() {
        if (colaExpedicion.estaVacia()) {
            System.out.println("No hay pedidos en la linea de expedicion.");
            return null;
        }
        return colaExpedicion.frente();
    }

    // Muestra todos los pedidos en espera, del primero al ultimo.
    public void mostrarPedidosEnEspera() {
        if (colaExpedicion.estaVacia()) {
            System.out.println("La linea de expedicion esta vacia.");
            return;
        }
        // Para mostrar sin sacar, usamos una cola auxiliar
        cola<Pedido> auxiliar = new cola<>();
        System.out.println("=== LINEA DE EXPEDICION (orden de despacho) ===");
        int posicion = 1;
        while (!colaExpedicion.estaVacia()) {
            Pedido p = colaExpedicion.desencolar();
            System.out.println(posicion++ + ". " + p);
            auxiliar.encolar(p);
        }
        // Restauramos la cola original
        while (!auxiliar.estaVacia()) {
            colaExpedicion.encolar(auxiliar.desencolar());
        }
    }

    public boolean estaVacia() { return colaExpedicion.estaVacia(); }
    public int cantidadPedidos() { return colaExpedicion.tamanio(); }
}
