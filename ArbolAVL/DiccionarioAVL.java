package repos.Mate_y_Java.ArbolAVL;

import repos.Mate_y_Java.Clases.Producto;

public class DiccionarioAVL implements DiccionarioAVLTDA {
    private NodoAVL raiz;
    private int cantidadElementos;

    // Indice auxiliar usado al recorrer el arbol para llenar arrays (obtenerTodos).
    private int indiceTemp;

    //Inicializa el árbol vacío y el contador de elementos en cero.
    public DiccionarioAVL() {
        this.raiz = null;
        this.cantidadElementos = 0;
    }

    // Métodos auxiliares para rotaciones y balanceo

    private int obtenerAltura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private int obtenerFactorBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : obtenerAltura(nodo.getIzquierdo()) - obtenerAltura(nodo.getDerecho());
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierdo();
        NodoAVL T2 = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(T2);
        y.setAltura(Math.max(obtenerAltura(y.getIzquierdo()), obtenerAltura(y.getDerecho())) + 1);
        x.setAltura(Math.max(obtenerAltura(x.getIzquierdo()), obtenerAltura(x.getDerecho())) + 1);
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecho();
        NodoAVL T2 = y.getIzquierdo();
        y.setIzquierdo(x);
        x.setDerecho(T2);
        x.setAltura(Math.max(obtenerAltura(x.getIzquierdo()), obtenerAltura(x.getDerecho())) + 1);
        y.setAltura(Math.max(obtenerAltura(y.getIzquierdo()), obtenerAltura(y.getDerecho())) + 1);
        return y;
    }

    // Implementecion de Insercion

    @Override
    public void insertar(String clave, Producto valor) {
        if (!contiene(clave)) {
            this.raiz = insertarRecursivo(this.raiz, clave, valor);
            cantidadElementos++; // Sumamos al contador
        } else {
            System.out.println("Error: Ya existe un producto registrado con el código [" + clave + "].");
        }
    }

    private NodoAVL insertarRecursivo(NodoAVL nodo, String clave, Producto valor) {
        if (nodo == null) return new NodoAVL(clave, valor);

        int comparacion = clave.compareTo(nodo.getClave());
        if (comparacion < 0) nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), clave, valor));
        else if (comparacion > 0) nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), clave, valor));
        else return nodo;

        nodo.setAltura(1 + Math.max(obtenerAltura(nodo.getIzquierdo()), obtenerAltura(nodo.getDerecho())));
        int balance = obtenerFactorBalance(nodo);

        if (balance > 1 && clave.compareTo(nodo.getIzquierdo().getClave()) < 0) return rotarDerecha(nodo);
        if (balance < -1 && clave.compareTo(nodo.getDerecho().getClave()) > 0) return rotarIzquierda(nodo);
        if (balance > 1 && clave.compareTo(nodo.getIzquierdo().getClave()) > 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && clave.compareTo(nodo.getDerecho().getClave()) < 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    // Implementación de Eliminación
    @Override
    public void eliminar(String clave) {
        if (!contiene(clave)) {
            System.out.println("Aviso: No se puede eliminar. El producto [" + clave + "] no existe en el sistema.");
            return;
        }
        this.raiz = eliminarRecursivo(this.raiz, clave);
        cantidadElementos--; // Restamos del contador
        System.out.println("Producto [" + clave + "] eliminado exitosamente del inventario.");
    }

    private NodoAVL eliminarRecursivo(NodoAVL nodo, String clave) {
        if (nodo == null) return null;

        int comparacion = clave.compareTo(nodo.getClave());

        // 1. Buscar el nodo a eliminar
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), clave));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), clave));
        } else {
            // Resolvemos según la cantidad de hijos
            // Caso 1 o 2: Sin hijos o con un solo hijo
            if ((nodo.getIzquierdo() == null) || (nodo.getDerecho() == null)) {
                NodoAVL temp = (nodo.getIzquierdo() != null) ? nodo.getIzquierdo() : nodo.getDerecho();

                if (temp == null) {
                    nodo = null; // Sin hijos
                } else {
                    nodo = temp; // Un hijo (toma su lugar)
                }
            } else {
                // Caso 3: Dos hijos. Buscamos el sucesor (el menor de la derecha)
                NodoAVL temp = nodoConValorMinimo(nodo.getDerecho());

                // Copiamos los datos del sucesor al nodo actual
                nodo.setClave(temp.getClave());
                nodo.setValor(temp.getValor());

                // Eliminamos al sucesor de su posición original
                nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), temp.getClave()));
            }
        }

        // Si el árbol tenía un solo nodo y se eliminó
        if (nodo == null) return null;

        // 2. Actualizar altura y 3. Verificar balance
        nodo.setAltura(Math.max(obtenerAltura(nodo.getIzquierdo()), obtenerAltura(nodo.getDerecho())) + 1);
        int balance = obtenerFactorBalance(nodo);

        // CASOS DE DESBALANCEO AL ELIMINAR
        if (balance > 1 && obtenerFactorBalance(nodo.getIzquierdo()) >= 0) return rotarDerecha(nodo);
        if (balance > 1 && obtenerFactorBalance(nodo.getIzquierdo()) < 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && obtenerFactorBalance(nodo.getDerecho()) <= 0) return rotarIzquierda(nodo);
        if (balance < -1 && obtenerFactorBalance(nodo.getDerecho()) > 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL nodoConValorMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;
    }

    // Implementación de Búsqueda y Contención

    @Override
    public Producto buscar(String clave) {
        NodoAVL resultado = buscarRecursivo(this.raiz, clave);
        if (resultado == null) {
            System.out.println("Aviso: El código [" + clave + "] no corresponde a ningún producto.");
            return null;
        }
        return resultado.getValor();
    }

    private NodoAVL buscarRecursivo(NodoAVL nodo, String clave) {
        if (nodo == null || clave.compareTo(nodo.getClave()) == 0) return nodo;
        if (clave.compareTo(nodo.getClave()) < 0) return buscarRecursivo(nodo.getIzquierdo(), clave);
        return buscarRecursivo(nodo.getDerecho(), clave);
    }

    @Override
    public boolean contiene(String clave) {
        return buscarRecursivo(this.raiz, clave) != null;
    }

    @Override
    public int tamanio() {
        return this.cantidadElementos; // O(1) gracias al contador
    }

    @Override
    public void mostrarInventarioOrdenado() {
        if (estaVacio()) {
            System.out.println("Inventario actual: [Vacío]");
            return;
        }
        System.out.println("=== INVENTARIO (Orden Alfabético) ===");
        recorridoInOrder(this.raiz);
        System.out.println("Total de productos distintos: " + tamanio());
    }

    private void recorridoInOrder(NodoAVL nodo) {
        if (nodo != null) {
            recorridoInOrder(nodo.getIzquierdo());
            System.out.println(nodo.getValor().toString());
            recorridoInOrder(nodo.getDerecho());
        }
    }

    @Override
    public boolean estaVacio() {
        return this.raiz == null;
    }

    // Recorre el arbol in-order y devuelve todos los productos en un array.
    // Al ser Producto[] (tipo concreto, no generico), no hay problema de cast.
    @Override
    public Producto[] obtenerTodos() {
        Producto[] resultado = new Producto[cantidadElementos];
        indiceTemp = 0;
        llenarArrayInOrder(this.raiz, resultado);
        return resultado;
    }

    private void llenarArrayInOrder(NodoAVL nodo, Producto[] array) {
        if (nodo != null) {
            llenarArrayInOrder(nodo.getIzquierdo(), array);
            array[indiceTemp] = nodo.getValor();
            indiceTemp++;
            llenarArrayInOrder(nodo.getDerecho(), array);
        }
    }
}
