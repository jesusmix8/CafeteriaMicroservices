package com.cafeteria.crud.producto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private long id;

    @Column( unique = true)
    private String nombre;

    private String descripcion;

    private float precio;

    private int cantidad;

    private String categoria;

    private LocalDate fecha;



    public Producto(long id, String nombre, String descripcion, float precio, int cantidad, String categoria, LocalDate fecha) {

        this.id = id;

        this.nombre = nombre;

        this.descripcion = descripcion;

        this.precio = precio;

        this.cantidad = cantidad;

        this.categoria = categoria;

        this.fecha = fecha;

    }

   
    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, float precio, int cantidad, String categoria, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public Producto(String nombre, String descripcion, float precio, int cantidad, String categoria, LocalDate fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.fecha = fecha;
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
