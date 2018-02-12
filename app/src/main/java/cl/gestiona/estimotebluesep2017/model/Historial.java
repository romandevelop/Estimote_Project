package cl.gestiona.estimotebluesep2017.model;

/**
 * Created by roman on 26-09-17.
 */

public class Historial {
    private int id;
    private String titular;
    private String fecha;
    private String hora;
    private String descripcion;

    public Historial() {
    }


    public Historial(int id, String titular, String fecha, String hora, String descripcion) {
        this.id = id;
        this.titular = titular;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
