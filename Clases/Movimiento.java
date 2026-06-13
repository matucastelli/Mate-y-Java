package Clases;
import java.time.LocalDateTime;

public class Movimiento {

    private String tipo;
    private String codigoProducto;
    private int cantidad;
    private LocalDateTime timestamp;
    private String usuario;

    // Construye un registro de movimiento validando el tipo y capturando la fecha actual.
    public Movimiento(String tipo, String codigoProducto, int cantidad, String usuario) {
        this.tipo = tipo;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    // Retorna la información del movimiento en formato legible.
    @Override
    public String toString() {
        return "Movimiento{tipo='" + tipo + "', producto='" + codigoProducto + "', cantidad=" + cantidad + ", usuario='" + usuario + "', fecha='" + timestamp + "'}";
    }
}
