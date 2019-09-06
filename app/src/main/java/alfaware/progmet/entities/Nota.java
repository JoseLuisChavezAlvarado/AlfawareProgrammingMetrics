package alfaware.progmet.entities;

import java.io.Serializable;

public class Nota extends SuperObject implements Serializable {

    private String titulo;
    private String descripcion;

    //==============================================================================================

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
