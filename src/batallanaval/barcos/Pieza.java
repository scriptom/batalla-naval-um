/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.barcos;

/**
 *
 * @author Usuario Estandar
 */
public class Pieza extends Danable {
    public final static short MAX_SALUD = 3;
    protected Barco barco;
    
    public Pieza(Barco barco, short salud) {
        this.barco = barco;
        this.salud = salud;
    }
    
    @Override
    public final void setSalud(short salud) {
        if (salud < 0)
            salud = 0;
        else if (salud > MAX_SALUD)
            salud = MAX_SALUD;
        this.salud = salud;
    }
    
    public Barco getBarco() {
        return barco;
    }
    
    
}
