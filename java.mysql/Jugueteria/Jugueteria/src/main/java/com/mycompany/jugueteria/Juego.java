
package com.mycompany.jugueteria;

/**
 *
 * @author Brian Guerra C.
 */
public class Juego {
   int codigo;
   String nombre;
   double precio;

    public Juego(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return " codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio;
    }
   
   
}
