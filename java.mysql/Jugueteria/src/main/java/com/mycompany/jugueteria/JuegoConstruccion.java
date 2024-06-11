
package com.mycompany.jugueteria;

/**
 *
 * @author Brian Guerra C.
 */
public class JuegoConstruccion extends Juego{
    String tipoBloque;

    public JuegoConstruccion(String tipoBloque, int codigo, String nombre, double precio) {
        super(codigo, nombre, precio);
        this.tipoBloque = tipoBloque;
    }

    public String getTipoBloque() {
        return tipoBloque;
    }

    public void setTipoBloque(String tipoBloque) {
        this.tipoBloque = tipoBloque;
    }

    @Override
    public String toString() {
        return " JuegoConstruccion " +super.toString()+ " tipoBloque=" + tipoBloque;
    }
    
    
}
