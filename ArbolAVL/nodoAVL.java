package ArbolAVL;

import Clases.Producto;

//Representa un nodo del Árbol AVL específico para el inventario.
public class nodoAVL {
    private String clave;
    private Producto valor;
    private int altura;
    private nodoAVL izquierdo;
    private nodoAVL derecho;

    public nodoAVL(String clave, Producto valor) {
        this.clave = clave;
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y setters para los atributos del nodo.
    
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Producto getValor() { return valor; }
    public void setValor(Producto valor) { this.valor = valor; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }

    public nodoAVL getIzquierdo() { return izquierdo; }
    public void setIzquierdo(nodoAVL izquierdo) { this.izquierdo = izquierdo; }

    public nodoAVL getDerecho() { return derecho; }
    public void setDerecho(nodoAVL derecho) { this.derecho = derecho; }
}
