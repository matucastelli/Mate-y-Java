import java.util.Scanner;

import ArbolAVL.diccionarioAVL;
import Clases.Producto;
import Funcionalidades.gestorInventario;
import Funcionalidades.monitorStockCritico;
import Funcionalidades.registroTrazabilidad;
import Funcionalidades.lineaExpedicion;
import Grafo.grafoAlmacen;

public class Main {

    private static int CANTIDAD_CRITICOS = 5;
    private static final String PASILLO_ENTRADA = "Pasillo-A";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        diccionarioAVL arbolBase = new diccionarioAVL();
        gestorInventario gestor = new gestorInventario(arbolBase);
        registroTrazabilidad trazabilidad = new registroTrazabilidad(arbolBase);
        monitorStockCritico monitor = new monitorStockCritico(arbolBase);
        lineaExpedicion expedicion = new lineaExpedicion();
        grafoAlmacen grafo = new grafoAlmacen();

        cargarDatosDePrueba(gestor, trazabilidad, grafo);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(teclado, "Elija una opcion: ");

            switch (opcion) {
                case 1:  altaProducto(teclado, gestor, trazabilidad); break;
                case 2:  registrarIngreso(teclado, gestor, trazabilidad, monitor); break;
                case 3:  registrarEgreso(teclado, gestor, trazabilidad, monitor); break;
                case 4:  gestor.mostrarInventario(); break;
                case 5:  mostrarCriticos(teclado, monitor); break;
                case 6:  trazabilidad.mostrarHistorial(); break;
                case 7:  trazabilidad.deshacerUltimoMovimiento(); break;
                case 8:  buscarProducto(teclado, gestor); break;
                case 9:  eliminarProducto(teclado, gestor); break;
                case 10: System.out.println("Cantidad de productos: " + gestor.cantidadProductos()); break;
                case 11: procesarPedido(teclado, gestor, trazabilidad, monitor, grafo, expedicion); break;
                case 12: expedicion.despacharProximo(); break;
                case 13: expedicion.mostrarPedidosEnEspera(); break;
                case 14: grafo.mostrarGrafo(); break;
                case 15: calcularRuta(teclado, grafo); break;
                case 16: agregarPasillo(teclado, grafo); break;
                case 17: conectarPasillos(teclado, grafo); break;
                case 0:  System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opcion invalida.");
            }
            System.out.println();

        } while (opcion != 0);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("===== CENTRO LOGISTICO - MENU PRINCIPAL =====");
        System.out.println("--- Inventario ---");
        System.out.println("1.  Alta de producto");
        System.out.println("2.  Registrar ingreso de stock");
        System.out.println("3.  Registrar egreso de stock");
        System.out.println("4.  Mostrar inventario ordenado");
        System.out.println("5.  Mostrar productos criticos");
        System.out.println("6.  Mostrar historial de movimientos");
        System.out.println("7.  Deshacer ultimo movimiento");
        System.out.println("8.  Buscar producto por codigo");
        System.out.println("9.  Eliminar producto");
        System.out.println("10. Ver cantidad de productos");
        System.out.println("--- Expedicion ---");
        System.out.println("11. Procesar pedido (localizar, verificar, ruta, despachar)");
        System.out.println("12. Despachar proximo pedido de la cola");
        System.out.println("13. Ver pedidos en espera");
        System.out.println("--- Rutas del almacen ---");
        System.out.println("14. Ver mapa de pasillos");
        System.out.println("15. Calcular ruta minima entre pasillos");
        System.out.println("16. Agregar pasillo nuevo");
        System.out.println("17. Conectar dos pasillos");
        System.out.println("0.  Salir");
    }

    private static int leerEntero(Scanner teclado, String mensaje) {
        System.out.print(mensaje);
        while (!teclado.hasNextInt()) {
            System.out.println("Por favor ingrese un numero valido.");
            teclado.next();
            System.out.print(mensaje);
        }
        int valor = teclado.nextInt();
        teclado.nextLine();
        return valor;
    }

    private static void altaProducto(Scanner teclado, gestorInventario gestor, registroTrazabilidad trazabilidad) {
        System.out.print("Codigo: ");
        String codigo = teclado.nextLine();
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Pasillo: ");
        String pasillo = teclado.nextLine();
        int stock = leerEntero(teclado, "Stock inicial: ");
        System.out.print("Lote: ");
        String lote = teclado.nextLine();

        Producto nuevo = gestor.agregarProducto(codigo, nombre, pasillo, stock, lote);
        if (nuevo == null) return;

        if (stock > 0) {
            trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, codigo, stock, "sistema");
        }
        System.out.println("Producto agregado: " + nuevo);
    }

    private static void registrarIngreso(Scanner teclado, gestorInventario gestor, registroTrazabilidad trazabilidad, monitorStockCritico monitor) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();
        int cantidad = leerEntero(teclado, "Cantidad a ingresar: ");

        if (cantidad <= 0) {
            System.out.println("Error: la cantidad a ingresar debe ser mayor a cero.");
            return;
        }

        boolean exito = gestor.actualizarStock(codigo, cantidad);
        if (exito) {
            trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, codigo, cantidad, "operador");
            Producto producto = gestor.buscarProducto(codigo);
            System.out.println("Nuevo stock de " + codigo + ": " + producto.getStock());
            if (monitor.esProductoCritico(codigo, CANTIDAD_CRITICOS)) {
                System.out.println(">>> ALERTA: El producto [" + codigo + "] sigue en nivel de Stock Critico. <<<");
            }
        }
    }

    private static void registrarEgreso(Scanner teclado, gestorInventario gestor, registroTrazabilidad trazabilidad, monitorStockCritico monitor) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();
        int cantidad = leerEntero(teclado, "Cantidad a retirar: ");

        if (cantidad <= 0) {
            System.out.println("Error: la cantidad a retirar debe ser mayor a cero.");
            return;
        }

        boolean exito = gestor.actualizarStock(codigo, -cantidad);
        if (exito) {
            trazabilidad.registrarMovimiento(registroTrazabilidad.EGRESO, codigo, cantidad, "operador");
            Producto producto = gestor.buscarProducto(codigo);
            System.out.println("Nuevo stock de " + codigo + ": " + producto.getStock());
            if (monitor.esProductoCritico(codigo, CANTIDAD_CRITICOS)) {
                System.out.println(">>> ALERTA: El producto [" + codigo + "] entro en nivel de Stock Critico. <<<");
            }
        }
    }

    private static void mostrarCriticos(Scanner teclado, monitorStockCritico monitor) {
        int n = leerEntero(teclado, "Cuantos productos criticos mostrar?: ");
        monitor.mostrarProductosCriticos(n);
    }

    private static void buscarProducto(Scanner teclado, gestorInventario gestor) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();
        Producto producto = gestor.buscarProducto(codigo);
        if (producto != null) System.out.println(producto);
    }

    private static void eliminarProducto(Scanner teclado, gestorInventario gestor) {
        System.out.print("Codigo del producto a eliminar: ");
        String codigo = teclado.nextLine();

        if (!gestor.existeProducto(codigo)) {
            System.out.println("Error: no existe ningun producto con el codigo [" + codigo + "].");
            return;
        }
        System.out.print("Confirma la eliminacion de [" + codigo + "]? (S/N): ");
        String confirmacion = teclado.nextLine();
        if (confirmacion.equalsIgnoreCase("S")) {
            gestor.eliminarProducto(codigo);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static void procesarPedido(Scanner teclado, gestorInventario gestor, registroTrazabilidad trazabilidad, monitorStockCritico monitor, grafoAlmacen grafo, lineaExpedicion expedicion) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();

        Producto producto = gestor.buscarProducto(codigo);
        if (producto == null) return;

        int cantidad = leerEntero(teclado, "Cantidad a despachar: ");

        if (cantidad <= 0) {
            System.out.println("Error: la cantidad a despachar debe ser mayor a cero.");
            return;
        }

        if (!gestor.hayStockSuficiente(codigo, cantidad)) return;

        if (monitor.esProductoCritico(codigo, CANTIDAD_CRITICOS)) {
            System.out.println(">>> ALERTA: El producto [" + codigo + "] esta en nivel de Stock Critico antes de despachar. <<<");
        }

        String[] ruta = grafo.rutaMasCorta(PASILLO_ENTRADA, producto.getUbicacion());
        if (ruta != null) {
            System.out.print("Ruta de recoleccion (" + ruta.length + " pasillos): ");
            for (int i = 0; i < ruta.length; i++) {
                System.out.print(ruta[i]);
                if (i < ruta.length - 1) System.out.print(" -> ");
            }
            System.out.println();
        }

        boolean exito = gestor.actualizarStock(codigo, -cantidad);
        if (!exito) return;

        trazabilidad.registrarMovimiento(registroTrazabilidad.EGRESO, codigo, cantidad, "operador");

        expedicion.agregarPedido(producto, cantidad);
    }

    private static void calcularRuta(Scanner teclado, grafoAlmacen grafo) {
        System.out.print("Pasillo de origen: ");
        String origen = teclado.nextLine();
        System.out.print("Pasillo de destino: ");
        String destino = teclado.nextLine();

        String[] ruta = grafo.rutaMasCorta(origen, destino);
        if (ruta != null) {
            System.out.print("Ruta minima (" + ruta.length + " pasillos): ");
            for (int i = 0; i < ruta.length; i++) {
                System.out.print(ruta[i]);
                if (i < ruta.length - 1) System.out.print(" -> ");
            }
            System.out.println();
        }
    }

    private static void agregarPasillo(Scanner teclado, grafoAlmacen grafo) {
        System.out.print("Nombre del pasillo nuevo: ");
        String pasillo = teclado.nextLine();
        grafo.insertarVertice(pasillo);
}

    private static void conectarPasillos(Scanner teclado, grafoAlmacen grafo) {
        System.out.print("Pasillo origen: ");
        String origen = teclado.nextLine();
        System.out.print("Pasillo destino: ");
        String destino = teclado.nextLine();
        grafo.insertarArista(origen, destino);
}

    private static void cargarDatosDePrueba(gestorInventario gestor, registroTrazabilidad trazabilidad, grafoAlmacen grafo) {
        gestor.agregarProducto("P001", "Tornillos 5mm",        "Pasillo-A", 50,  "L001");
        gestor.agregarProducto("P002", "Cables UTP",            "Pasillo-B", 5,   "L002");
        gestor.agregarProducto("P003", "Cajas de carton",       "Pasillo-C", 200, "L003");
        gestor.agregarProducto("P004", "Guantes de seguridad",  "Pasillo-A", 2,   "L004");

        trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, "P001", 50,  "carga_inicial");
        trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, "P002", 5,   "carga_inicial");
        trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, "P003", 200, "carga_inicial");
        trazabilidad.registrarMovimiento(registroTrazabilidad.INGRESO, "P004", 2,   "carga_inicial");

        grafo.insertarArista("Pasillo-A", "Pasillo-B");
        grafo.insertarArista("Pasillo-B", "Pasillo-C");
        grafo.insertarArista("Pasillo-C", "Pasillo-D");
        grafo.insertarArista("Pasillo-A", "Pasillo-D");
        grafo.insertarArista("Pasillo-B", "Pasillo-E");
        grafo.insertarArista("Pasillo-D", "Pasillo-E");
    }
}