package ArbolAVL;

import Clases.Producto;

//Representa un nodo del Árbol AVL específico para el inventario.
public class NodoAVL {
    private String clave;
    private Producto valor;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(String clave, Producto valor) {
        this.clave = clave;
        this.valor = valor;
        this.altura = 1;
        this.izquierdo = null;
        this.derecho = null;
    }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Producto getValor() { return valor; }
    public void setValor(Producto valor) { this.valor = valor; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }

    public NodoAVL getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoAVL izquierdo) { this.izquierdo = izquierdo; }

    public NodoAVL getDerecho() { return derecho; }
    public void setDerecho(NodoAVL derecho) { this.derecho = derecho; }
}
