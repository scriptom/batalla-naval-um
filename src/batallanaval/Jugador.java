/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval;

import batallanaval.barcos.Barco;
import batallanaval.Partida.Dificultad;
import batallanaval.mapa.Mapa;

/**
 * 
 * @author Usuario Estandar
 */
public abstract class Jugador {
    public final static int MAX_BARCOS = 6;
    protected String nombre;
    protected Barco[] barcos;
    protected short numBarcos = 0;
    protected Mapa mapa;
    protected Mapa intentos;
    
    abstract public void jugar();
    abstract public void posicionarBarcos();
    abstract public short saludPorDificultad(Dificultad d);
    
    public boolean atacar(short fila, short columna) {
        return intentos.getCasilla(fila, columna).atacar();
    }
    
    public Mapa getMapa() {
        return mapa;
    }
    
    public void setMapaIntentos(Mapa intentos) {
        this.intentos = intentos;
    }
    
    public void setNombre(String nombre) {
        if (null == nombre || nombre.isEmpty())
            nombre = "(sin nombre)";
        this.nombre = nombre;
    }
    
    public Jugador() {
        barcos = new Barco[MAX_BARCOS];
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Barco getBarco(int index) {
        return barcos[index];
    }
    
    public Jugador addBarco(Barco barco) {
        barcos[numBarcos++] = barco;
        return this;
    }
    
    public short barcosRestantes(){
        short cuenta = numBarcos;
        for (Barco barco: barcos)
            if (barco.estaDestruido())
                cuenta--;
        return cuenta;
    }
    
    @Override
    public String toString() {
        return getNombre();
    }
}
