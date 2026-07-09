package Funcionalidades;

import Clases.Producto;
import Cola.cola;
import Clases.Pedido;
import Pila.pila;

// Funcionalidad: Linea de Expedicion.
// Los pedidos listos para despacho esperan en una cola FIFO.
// El operario los carga al camion en el orden en que llegaron.
// Posibilidad de poder eliminar pedidos y devolver su stock
public class lineaExpedicion {
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
        //historialDespachos.apilar(despachado);
        System.out.println("Despachado al camion: " + despachado);
        return despachado;
    }


  /*  public boolean deshacerUltimoDespacho() {
        if (historialDespachos.estaVacia()) {
            System.out.println("No hay despachos para deshacer.");
            return false;
        }

        Pedido ultimoDespachado = historialDespachos.desapilar();
        cola<Pedido> auxiliar = new cola<>();
        auxiliar.encolar(ultimoDespachado);
        while (!colaExpedicion.estaVacia()) {
            auxiliar.encolar(colaExpedicion.desencolar());
        }
        colaExpedicion = auxiliar;
        System.out.println("Se deshizo el despacho de: " + ultimoDespachado);
        return true;
    } */

    public Producto eliminarPedido(int id , gestorInventario gestor){

        cola<Pedido> colaAuxiliar = new cola<>();
        Producto productoCancelado = null;

        if (colaExpedicion.estaVacia()) {
            System.out.println("No hay pedidos para eliminar");
            return null;

        }

        while (!colaExpedicion.estaVacia()) {
            Pedido pedidoActual = colaExpedicion.desencolar();

            if (pedidoActual.getId() == id) {
                productoCancelado = pedidoActual.getProducto();

                String codigoProd = productoCancelado.getCodigo();
                int cantidadADevolver = pedidoActual.getCantidad();

                gestor.actualizarStock(codigoProd, cantidadADevolver);

                System.out.println("Pedido #" + id + " cancelado. Se devolvieron " + cantidadADevolver + " unidades del producto " + codigoProd);
            } else {
                colaAuxiliar.encolar(pedidoActual);
            }
        }

        while (!colaAuxiliar.estaVacia()){
            colaExpedicion.encolar(colaAuxiliar.desencolar());
        }

        if (productoCancelado == null){
            System.out.println("No se encontró ningún pedido con el ID #" + id);
        }
        return productoCancelado;

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
