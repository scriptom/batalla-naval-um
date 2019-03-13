/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.mapa;

import batallanaval.barcos.Barco;
import batallanaval.barcos.Danable;
import batallanaval.barcos.Pieza;

/**
 *
 * @author Usuario Estandar
 */
public class Casilla {
    protected Pieza pieza;
    protected boolean atacado = false;
    protected Casilla nodoDer; // Casilla a la derecha de esta
    protected Casilla nodoAba;   // Casilla debajo de esta
    
    public Casilla() {
        this.pieza = null;        
        this.nodoDer = null;
        this.nodoAba = null;
    }
    
    public Casilla(Pieza pieza) {
        this.pieza = pieza;
        this.nodoDer = null;
        this.nodoAba = null;
    }
    
    public Casilla(Pieza pieza, Casilla nodoDer) {
        this.pieza = pieza;
        this.nodoDer = nodoDer;
        this.nodoAba = null;
    }
    
    public Casilla(Pieza pieza, Casilla nodoDer, Casilla nodoAba) {
        this.pieza = pieza;
        this.nodoDer = nodoDer;
        this.nodoAba = nodoAba;
    }
    
    public Casilla siguiente(Orientacion orientacion) {
        return Orientacion.HORIZONTAL.equals(orientacion) ? nodoDer : nodoAba;
    }
    
    public void setNodoDer(Casilla casilla) {
        this.nodoDer = casilla;
    }
    
    public void setNodoAba(Casilla casilla) {
        this.nodoAba = casilla;
    }
    
    public boolean hasBarco() {
        return pieza != null;
    }
    
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }
    
    public Pieza getPieza() {
        return pieza;
    }
    
    public Barco getBarco() {
        if (hasBarco())
            return pieza.getBarco();
        return null;
    }
    
    public boolean atacar() {
        this.atacado = true;
        if (hasBarco()) {
            pieza.getBarco().inflingir(pieza);
            return true;
        }
        return false;
    }
    
    public boolean fueAtacada() {
        if (hasBarco())
            return pieza.getEstado().equals(Danable.Estado.DESTRUIDO);
        return atacado;
    }
    
    public String toString(boolean ocultar) {
        /* Leyenda:
         '~': Vacio
         'O': Barco
         '+': Ataque
         num: Dañado
         '0': Destruido
        */
        String retchar = "~";
        if (hasBarco())
            if (ocultar) {
                retchar = "~";
                if (Danable.Estado.DANADO.equals(getPieza().getEstado()))
                    retchar = String.valueOf(getPieza().getSalud());
            } else {
                retchar = "O";
                if (Danable.Estado.DANADO.equals(getPieza().getEstado()))
                    retchar = String.valueOf(getPieza().getSalud());
                else if (Danable.Estado.DESTRUIDO.equals(getPieza().getEstado()))
                    retchar = "X";
            }
        else if (atacado)
            retchar = "+";
        
        return retchar;
    }
    
    @Override
    public String toString() {
        return toString(false);
    }
}
