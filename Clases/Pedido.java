package Clases;

public class Pedido {
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
