/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.barcos;

import batallanaval.mapa.Casilla;
import batallanaval.mapa.Orientacion;

/**
 *
 * @author Usuario Estandar
 */
public class Barco extends Danable {
    public final static short MIN_SIZE = 2;
    public final static short MAX_SIZE = 7;
    private Casilla posicion;
    private short size;
    private Pieza[] piezas;
    protected Orientacion orientacion;
    
    public Barco(short size, short salud) {
        setSize(size);
        piezas = new Pieza[this.size];
        for (int i = 0; i < this.size; i++)
            piezas[i] = new Pieza(this, salud);
        salud = piezas[0].getSalud();
        
        // Esto esta garantizado que esta en el rango
        this.salud = (short)(salud * this.size);
        
        posicion = null;
    }
    
    public void inflingir(Pieza pieza) {
        super.inflingir();
        for (Pieza thisPieza: piezas)
            if (thisPieza == pieza) {
                thisPieza.inflingir();
                break;
            }
    }
    
    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }
    
    public Orientacion getOrientacion() {
        return orientacion;
    }
    
    /**
     * Envoltorio publico para posicionamiento recursivo
     * @param cabeza Casilla inicial donde posicionar
     * @return Resultado de exito del posicionamiento
     */
    public boolean posicionar(Casilla cabeza) {
        return posicionar(cabeza, (short)0);
    }
    
    /**
     * Metodo de posicionamiento recursivo
     * @param cabeza Casilla inicial donde posicionar
     * @param pIndex Indice de la pieza actual a posicionar
     * @return Resultado de exito de posicionamiento
     */
    private boolean posicionar(Casilla cabeza, short pIndex) {
        if (pIndex == size)
            return true;
        if (cabeza.hasBarco())
            return false;
        cabeza.setPieza(piezas[pIndex]);
        boolean sigPos = posicionar(cabeza.siguiente(orientacion), (short) (pIndex +1));
        if ( ! sigPos )
            cabeza.setPieza(null);
        return sigPos;
    }
    
    public void remover(Casilla cabeza) {
        remover(cabeza, (short)0);
    }
    
    /**
     * Metodo que remueve las piezas de un barco recursivamente
     * @param cabeza
     * @param removidas 
     */
    private void remover(Casilla cabeza, short removidas) {
        if ( removidas == size )
            return;
        cabeza.setPieza(null);
        remover(cabeza.siguiente(orientacion), (short) (removidas +1));
    }
    
    protected final void setSize(short size) {
        if (size < MIN_SIZE)
            size = MIN_SIZE;
        else if (size > MAX_SIZE)
            size = MAX_SIZE;
        this.size = size;
    }
    
    public final short getSize() {
        return size;
    }
    
    public Pieza getPiezaEn(int index) {
        try {
            return piezas[index];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return null;
        }
    }
}
