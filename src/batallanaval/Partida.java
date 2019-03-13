/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval;

/**
 *
 * @author Usuario Estandar
 */
public class Partida {
    public enum Dificultad {
        MUY_FACIL("Muy fácil"), FACIL("Fácil"), NORMAL("Normal"), DIFICIL("Díficil"), MUY_DIFICIL("Muy díficil");
        private String etiqueta;
        static String[] stringArray;
        static String[] toStringArray() {
            if (null == stringArray){ 
                Dificultad[] values = values(); // esto ocurre durante la primera ejecucion de la funcion nada mas
                stringArray = new String[values.length];
                for (short s = 0; s < values.length; s++)
                    stringArray[s] = values[s].toString();
            }
            return stringArray;
        }
        
        Dificultad(String etiqueta) {
            this.etiqueta = etiqueta;
        }
        
        @Override 
        public String toString() {
            return etiqueta;
        }
    };
    
    protected Jugador[] jugadores = new Jugador[2];
    protected Dificultad dificultad;
    protected long duracion;
    
    public Partida(Jugador jug1, Jugador jug2, Dificultad dificultad) {
        jug1.setMapaIntentos(jug2.getMapa());
        jug2.setMapaIntentos(jug1.getMapa());
        jugadores[0] = jug1;
        jugadores[1] = jug2;
        this.dificultad = dificultad;
        duracion = 0L;
    }
    
    /**
     * Inicia el ciclo de una partida, y devuelve el jugador que haya ganado
     * @return Jugador el jugador que haya ganado la partida
     * @author Tomás El Fakih
     */
    public Jugador init() {
        long inicio = System.currentTimeMillis();
        int numTurnos = 0;
        do {
            jugadores[numTurnos % 2].jugar();
            numTurnos++;
        } while (jugadores[1].barcosRestantes()> 0 && 
                jugadores[0].barcosRestantes() > 0);
        duracion = System.currentTimeMillis() - inicio;
        // Determinamos el ganador
//        if (jugadores[1].barcosRestantes() > 0)
//            return jugadores[1];
//        else 
//            return jugadores[0];
        return jugadores[1].barcosRestantes()> 0 ? jugadores[1] : jugadores[0];
    }
    
    public long getDuracion() {
        return duracion;
    }
    
}
