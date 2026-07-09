package Grafo;

public interface iGrafo {

    void insertarVertice(String pasillo);

    void eliminarVertice(String pasillo);

    void insertarArista(String origen , String destino);

    void eliminarArista(String origen , String destino);

    boolean existeVertice(String pasillo);


    boolean existeArista(String origen , String destino);

    void mostrarGrafo();

    void recorridosDFS(String inicio);

    void recorridosBFS(String inicio);

    String[] rutaMasCorta(String origen , String destino);
}