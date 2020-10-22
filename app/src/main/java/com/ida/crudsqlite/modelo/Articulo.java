package com.ida.crudsqlite.modelo;

public class Articulo {
    private String id, descripcion, precio;

    public Articulo() {
    }

    public Articulo(String id, String descripcion, String precio) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
