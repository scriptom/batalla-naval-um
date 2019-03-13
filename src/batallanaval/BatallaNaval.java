/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval;

import batallanaval.mapa.Mapa;
import batallanaval.utils.Menu;
import batallanaval.Partida.Dificultad;
import batallanaval.barcos.Barco;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Usuario Estandar
 */
public class BatallaNaval {
    static boolean salir = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(null);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
        
        do {
            System.out.println("Bienvenido al juego de Batalla Naval");
            
            menu.set("Seleccione modo de juego", "Batalla", "Campaña");
            int modoJuego = menu.getEleccion();
            
            menu.set("Seleccione dificultad", Dificultad.toStringArray());
            // le quitamos 1 a la dificultad recibida para que este en los limites del vector.
            int dificultadOrd = menu.getEleccion() -1; 
            Dificultad dificultad = Dificultad.values()[dificultadOrd];
            
            System.out.println("Ingrese su nombre de jugador");
            String nombre = scanner.next();
            
            menu.set("Ingrese el tamaño del mapa a usar", "7x7", "8x8", "9x9");
            // Sumamos 6 a la opcion del usuario para poder llegar a la dimension deseada
            short tamañoMapa = (short) (menu.getEleccion() + 6);
            
            int largoBarcoExtra;
            System.out.println("Se le daran 6 barcos:");
            System.out.println("1 de tamaño 2");
            System.out.println("2 de tamaño 3");
            System.out.println("1 de tamaño 4");
            System.out.println("1 de tamaño 5");
            System.out.println("Y uno de tamaño variable.");
            // Ciclo de validacion del largo del barco deseado
            do {
                System.out.println("Por favor introduzca un numero entre 2 y 7");
                largoBarcoExtra = scanner.nextInt();
                if (largoBarcoExtra < 2 || largoBarcoExtra > 7)
                    System.out.println("El numero que introdujo es invalido.");
            } while(largoBarcoExtra < 2 || largoBarcoExtra > 7);
            // Asignamos aleatoriamente el largo del barco del CPU
            int lbeCPU = new java.util.Random().nextInt(largoBarcoExtra);
            // Corregimos el largo en caso de que quede en 1 o 0
            if (lbeCPU < 2)
                lbeCPU += 2;
                        
            /* Empieza la diversion */ 

            // Instanciamos a los jugadores
            Jugador jugadores[] = { 
                new Humano(nombre, new Mapa(tamañoMapa)), 
                new Computadora(new Mapa(tamañoMapa)) 
            };
            for (Jugador j: jugadores) {
                // asignamos sus barcos
                short salud = j.saludPorDificultad(dificultad);
                j.addBarco(new Barco((short)2, salud))
                 .addBarco(new Barco((short)3, salud))
                 .addBarco(new Barco((short)3, salud))
                 .addBarco(new Barco((short)4, salud))
                 .addBarco(new Barco((short)5, salud));
            }
            
            // Particularidad: Tenemos que asignar el barco personalizado a c/u
            jugadores[0].addBarco(
                new Barco(
                    (short)(largoBarcoExtra), 
                    jugadores[0].saludPorDificultad(dificultad)
                )
            );
            jugadores[1].addBarco(
                new Barco(
                    (short)(lbeCPU), 
                    jugadores[1].saludPorDificultad(dificultad)
                )
            );
            
            // Cada uno tiene que posicionar sus barcos
            for (Jugador j: jugadores)
                j.posicionarBarcos();
            
            /**
             * DEVELOPMENT: Ver el mapa del CPU
             */
            System.out.println(jugadores[1].getMapa());
            
            // Ya tenemos los barcos posicionados, ahora podemos comenzar la partida (o campaña,hagamos un pequeño switch
            switch (modoJuego) {
                case 1:/* Batalla */
                    // Creamos una nueva partida
                    Partida partida = new Partida(jugadores[0], jugadores[1], dificultad);
                    // obtenemos el ganador de la partida inicializandola
                    Jugador ganador = partida.init();
                    // Imprimimos los datos del ganador
                    System.out.println("El ganador de esta Batalla ha sido " + ganador);
                    System.out.println("Duracion de la partida: " + partida.getDuracion() + " ms");
                    // planteamos la opcion de continuar o irnos
                    System.out.println("Desea volver al inicio del juego? (1 para volver, otro numero para salir)");
                    salir = scanner.nextInt() != 1;
                    break;
                case 2: /* Campaña */
                    break;
            }
        } while (!salir);
    }
    
}
