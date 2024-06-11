
package com.mycompany.jugueteria;

/**
 *
 * @author Brian Guerra C.
 */
public class JuegoMesa extends Juego{
    String marca;
    String tipo;

    public JuegoMesa(String marca, String tipo, int codigo, String nombre, double precio) {
        super(codigo, nombre, precio);
        this.marca = marca;
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "JuegoMesa " +super.toString()+ " marca=" + marca + ", tipo=" + tipo;
    }
    
    
}
