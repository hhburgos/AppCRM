package com.example.akirucrm;

public class Articulo {
    private int stock, cantidad;
    private double precio;
    private boolean tipo;
    private String nombre, codigo, familia;

    public Articulo(int stock, double precio, boolean tipo, String nombre, String codigo, String familia) {
        this.stock = stock;
        this.precio = precio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.codigo = codigo;
        this.familia = familia;
        this.cantidad = 0;
    }

    @Override
    public String toString(){
        String cadena;
        cadena = this.getCodigo() + " - " + this.getNombre() + " - " + this.getPrecio() + "€/ud";
        return cadena;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return this.stock;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cant) {
        this.cantidad = cant;
    }

    public void sumaCantidad() {
        this.cantidad++;
    }

    public void restaCantidad() {
        this.cantidad--;
    }
}
