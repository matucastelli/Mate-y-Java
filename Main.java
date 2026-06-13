
import java.util.Scanner;

import ArbolAVL.diccionarioAVL;
import Clases.Producto;
import Funcionalidades.MonitorStockCritico;
import Funcionalidades.RegistroTrazabilidad;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        diccionarioAVL inventario = new diccionarioAVL();
        MonitorStockCritico monitor = new MonitorStockCritico(inventario);
        RegistroTrazabilidad trazabilidad = new RegistroTrazabilidad(inventario);

        cargarDatosDePrueba(inventario, trazabilidad);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(teclado, "Elija una opcion: ");

            switch (opcion) {
                case 1:
                    altaProducto(teclado, inventario, trazabilidad);
                    break;
                case 2:
                    registrarIngreso(teclado, inventario, trazabilidad);
                    break;
                case 3:
                    registrarEgreso(teclado, inventario, trazabilidad);
                    break;
                case 4:
                    inventario.mostrarInventarioOrdenado();
                    break;
                case 5:
                    mostrarCriticos(teclado, monitor);
                    break;
                case 6:
                    trazabilidad.mostrarHistorial();
                    break;
                case 7:
                    trazabilidad.deshacerUltimoMovimiento();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

            System.out.println();

        } while (opcion != 0);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("===== CENTRO LOGISTICO - MENU PRINCIPAL =====");
        System.out.println("1. Alta de producto");
        System.out.println("2. Registrar ingreso de stock");
        System.out.println("3. Registrar egreso de stock");
        System.out.println("4. Mostrar inventario ordenado");
        System.out.println("5. Mostrar productos criticos");
        System.out.println("6. Mostrar historial de movimientos");
        System.out.println("7. Deshacer ultimo movimiento");
        System.out.println("0. Salir");
    }

    // Lee un entero validando que el usuario no ingrese texto.
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

    private static void altaProducto(Scanner teclado, diccionarioAVL inventario, RegistroTrazabilidad trazabilidad) {
        System.out.print("Codigo: ");
        String codigo = teclado.nextLine();
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Pasillo: ");
        String pasillo = teclado.nextLine();
        int stock = leerEntero(teclado, "Stock inicial: ");
        System.out.print("Lote: ");
        String lote = teclado.nextLine();

        Producto nuevo = new Producto(codigo, nombre, pasillo, stock, lote);
        inventario.insertar(codigo, nuevo);

        if (stock > 0) {
            trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, codigo, stock, "sistema");
        }

        System.out.println("Producto agregado: " + nuevo);
    }

    private static void registrarIngreso(Scanner teclado, diccionarioAVL inventario, RegistroTrazabilidad trazabilidad) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();
        Producto producto = inventario.buscar(codigo);

        if (producto == null) {
            return;
        }

        int cantidad = leerEntero(teclado, "Cantidad a ingresar: ");
        producto.setStock(producto.getStock() + cantidad);
        trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, codigo, cantidad, "operador");

        System.out.println("Nuevo stock de " + codigo + ": " + producto.getStock());
    }

    private static void registrarEgreso(Scanner teclado, diccionarioAVL inventario, RegistroTrazabilidad trazabilidad) {
        System.out.print("Codigo del producto: ");
        String codigo = teclado.nextLine();
        Producto producto = inventario.buscar(codigo);

        if (producto == null) {
            return;
        }

        int cantidad = leerEntero(teclado, "Cantidad a retirar: ");

        if (cantidad > producto.getStock()) {
            System.out.println("Error: no se puede retirar mas stock del disponible (" + producto.getStock() + ").");
            return;
        }

        producto.setStock(producto.getStock() - cantidad);
        trazabilidad.registrarMovimiento(RegistroTrazabilidad.EGRESO, codigo, cantidad, "operador");

        System.out.println("Nuevo stock de " + codigo + ": " + producto.getStock());
    }

    private static void mostrarCriticos(Scanner teclado, MonitorStockCritico monitor) {
        int n = leerEntero(teclado, "Cuantos productos criticos mostrar?: ");
        monitor.mostrarProductosCriticos(n);
    }

    // Carga algunos productos de ejemplo para poder probar el sistema rapidamente.
    private static void cargarDatosDePrueba(diccionarioAVL inventario, RegistroTrazabilidad trazabilidad) {
        Producto p1 = new Producto("P001", "Tornillos 5mm", "Pasillo A - Estante 1", 50, "L001");
        Producto p2 = new Producto("P002", "Cables UTP", "Pasillo B - Estante 3", 5, "L002");
        Producto p3 = new Producto("P003", "Cajas de carton", "Pasillo C - Estante 2", 200, "L003");
        Producto p4 = new Producto("P004", "Guantes de seguridad", "Pasillo A - Estante 4", 2, "L004");

        inventario.insertar(p1.getCodigo(), p1);
        inventario.insertar(p2.getCodigo(), p2);
        inventario.insertar(p3.getCodigo(), p3);
        inventario.insertar(p4.getCodigo(), p4);

        trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, "P001", 50, "carga_inicial");
        trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, "P002", 5, "carga_inicial");
        trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, "P003", 200, "carga_inicial");
        trazabilidad.registrarMovimiento(RegistroTrazabilidad.INGRESO, "P004", 2, "carga_inicial");
    }
}
