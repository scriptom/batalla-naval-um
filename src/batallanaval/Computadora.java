/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval;

import batallanaval.Partida.Dificultad;
import batallanaval.mapa.Mapa;
import batallanaval.mapa.Orientacion;
import java.util.Random;

/**
 *
 * @author Usuario Estandar
 */
public class Computadora extends Jugador {
    
    public Computadora(Mapa mapa) {
        super();
        setNombre("CPU");
        this.mapa = mapa;
    }
    
    @Override
    public short saludPorDificultad(Dificultad d) {
        switch(d) {
            case MUY_FACIL:
            case FACIL:
            case NORMAL:
                return 1;
            case DIFICIL:
                return 2;
            case MUY_DIFICIL:
                return 3;
        }
        
        return 1;
    }

    @Override
    public void jugar() {
        // Coordenadas
        short fila, columna;
        
        // Aleatoriedad
        Random rand = new Random();
        
        do {
            fila = (short) rand.nextInt(mapa.dimension-1);
            columna = (short) rand.nextInt(mapa.dimension-1);
        } while (intentos.getCasilla(fila, columna) == null || // Prevencion de NullPointerException
                intentos.getCasilla(fila, columna).fueAtacada());
        
        boolean acierto = atacar(fila, columna);
        System.out.println("La computadora a atacado en " + fila + ", " + columna + " y " + (acierto ? "fue un acierto" : "ha fallado"));
    }

    /**
     * Implementacion de algoritmo de posicionamiento aletorio de barcos, para el CPU.
     * El algoritmo es:
     * 1. Seleccionar aleatoriamente coordenadas validas en el mapa
     * 2. Seleccionar aleatoriamente una orientacion valida
     * 3. Verficiar validez de ubicacion:
     * 3.1. Si es valida, se ubica y se pasa al siguiente barco
     * 3.2. Si no lo es:
     * 3.2.1. Se prueba la otra orientacion
     * 3.2.2. Si volvimos, volvemos a 1., con el mismo barco, y aumentamos contador
     * 3.2.3. Si el contador es 30, ubicamos barco anterior
     */
    @Override
    @SuppressWarnings("UnusedAssignment")
    public void posicionarBarcos() {
        // Coordenadas
        short fila, columna;
        
        // Orientacion
        Orientacion orientacion;
        
        // Numero de intentos
        short numIntentos;
        
        // Aleatoriedad
        Random rand = new Random();
        
        // Como lo accesaremos frecuentemente, conviene tener el vector de valores del enumerado
        Orientacion[] orientaciones = Orientacion.values();
        
        // Determinante para saber si debemos reintentar
        boolean reintentar;
        
        // Para iterar, conviene mejor que sea un for normal, para tener mejor control sobre el indice
        for (short barco = 0; barco < numBarcos;) {
            // Con cada nuevo barco, reiniciamos los intentos y el determinante
            reintentar = false;
            numIntentos = 0;
            do {
                numIntentos++;
                // 1.
                fila = (short) rand.nextInt(mapa.dimension);
                columna = (short) rand.nextInt(mapa.dimension);
                // 2.
                orientacion = orientaciones[rand.nextInt(orientaciones.length -1)];
                // 3., conectada con 3.2.1 para acelerar el proceso
                if (!mapa.colocarBarco(barcos[barco], fila, columna, orientacion) &&
                        !mapa.colocarBarco(barcos[barco], fila, columna, orientacion.not())) {
                    // 3.2.2
                    reintentar = true;
                    // 3.2.3
                    if (numIntentos == 30) {
                        barco--;
                        reintentar = false;
                    }
                } else {
                    reintentar = false;
                    barco++;
                }
            } while (reintentar);
        }
    }
}
