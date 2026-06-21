package Grafo;

public class nodoVertice { 


    String pasillo;
    nodoAdyacente adyacentes;
    nodoVertice siguiente;
    boolean visitado;

    public nodoVertice(String pasillo) {
        this.pasillo = pasillo;
        this.adyacentes = null;
        this.visitado = false;
        this.siguiente = null;
    }
}