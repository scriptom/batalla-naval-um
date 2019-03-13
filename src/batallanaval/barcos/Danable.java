/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.barcos;

/**
 * Clase abstracta para objetos dañables en una partida
 * @author Usuario Estandar
 */
public abstract class Danable {
    public enum Estado {
        OPTIMO, DANADO, DESTRUIDO
    };
    
    protected Estado estado = Estado.OPTIMO;
    protected short salud;
    
    public void inflingir() {
        if (salud > 0) {
            salud--;
            if (estado == Estado.OPTIMO)
                estado = Estado.DANADO;
            else if (salud == 0)
                estado = Estado.DESTRUIDO;
        }
    }
    
    public void setSalud(short salud) {
        if (salud < 0)
            salud = 0;
        this.salud = salud;
    }
    
    public final short getSalud() {
        return salud;
    }
    
    public final Estado getEstado() {
        return estado;
    }
    
    public final boolean estaDestruido() {
        return getEstado().equals(Estado.DESTRUIDO);
    }
    
}
