package Clases;

public class Producto {

    private String codigo;
    private String nombre;
    private String ubicacion;
    private int stock;
    private String lote;

    // Crea un nuevo producto validando que el código exista y el stock sea válido mediante mensajes en consola.
    public Producto(String codigo, String nombre, String ubicacion, int stock, String lote) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.out.println("Error: El código no puede ser nulo o vacío. Se asignará un valor por defecto.");
            this.codigo = "SIN_CODIGO";
        } else {
            this.codigo = codigo;
        }
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        if (stock < 0) {
            System.out.println("Error: El stock no puede ser negativo. Se inicializará en 0.");
            this.stock = 0;
        } else {
            this.stock = stock;
        }
        this.lote = lote;
    }

    // Getters y Setters con validaciones básicas para mantener la integridad de los datos.
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getStock() { return stock; }
    //  Actualiza el stock asegurando que no se asignen valores negativos.
    public void setStock(int stock) {   
        if (stock < 0) {
            System.out.println("Error: El stock no puede ser negativo. No se actualizará el valor.");
        } else {
            this.stock = stock;
        }
    }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    @Override
    public String toString() {
        return "Producto{codigo='" + codigo + "', nombre='" + nombre + "', ubicacion='" + ubicacion + "', stock=" + stock + ", lote='" + lote + "'}";
    }
}
