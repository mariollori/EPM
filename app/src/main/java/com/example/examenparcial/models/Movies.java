package com.example.examenparcial.models;

public class Movies {
    String id;
    String titulo;
    String categoria;
    String duracion;

    public Movies() {
    }

    public Movies(String id, String titulo, String categoria, String duracion) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.duracion = duracion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
