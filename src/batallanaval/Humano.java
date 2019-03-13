/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval;

import batallanaval.Partida.Dificultad;
import batallanaval.barcos.Barco;
import batallanaval.mapa.Casilla;
import batallanaval.mapa.Mapa;
import batallanaval.mapa.Orientacion;
import java.util.Scanner;


/**
 *
 * @author Usuario Estandar
 */
public class Humano extends Jugador {
    
    public Humano(String nombre, Mapa mapa) {
        super();
        setNombre(nombre);
        this.mapa = mapa;
    }
    
    @Override
    public short saludPorDificultad(Dificultad d) {
        switch(d) {
            case MUY_FACIL:
                return 3;
            case FACIL:
                return 2;
            case NORMAL:
            case DIFICIL:
            case MUY_DIFICIL:
                return 1;
        }
        
        return 1;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void jugar() {
        Scanner sc = new Scanner (System.in);
        short fila = 1, columna = 1;
        String entrada;
        String[] coords;
        boolean repetirEntrada;
        
        System.out.println("Tu mapa de intentos:");
        System.out.println(intentos.toString(true));
        System.out.println("Tu mapa de barcos");
        System.out.println(mapa);
        System.out.println("Ingrese la coordenada donde desea atacar usando el formato \"fila,columna\" (Sin comillas ni espacio)");
        do {
            entrada = sc.next();
            if ( ! entrada.matches("\\d,\\d") ) {
                System.out.println("Coordenada invalida. Intente nuevamente");
                repetirEntrada = true;
            } else {
                coords = entrada.split(",");
                fila = (short) Integer.parseInt(coords[0]);
                columna = (short) Integer.parseInt(coords[1]);
                if ( fila < 1 || fila > mapa.dimension || columna < 1 || columna > mapa.dimension ) {
                    System.out.println("Coordenada invalida. Intente nuevamente");
                    repetirEntrada = true;
                } else {
                    repetirEntrada = false;
                    // tenemos una coordenada valida, vamos a atacar
                    if ( intentos.getCasilla(fila, columna).fueAtacada() )  {
                        System.out.println("Esta casilla ya fue atacada. Por favor seleccione otra ");
                        repetirEntrada = true;
                    } else {
                        boolean acierto = atacar(fila, columna);
                        System.out.println(acierto ? "El ataque fue un acierto!!" : "El ataque fue fallido :(" );
                        repetirEntrada = false;
                    }
                }
            }
        } while (repetirEntrada);
    }
    
    @Override
    public void posicionarBarcos() {
        Scanner sc = new Scanner(System.in);
        String entrada;
        
        System.out.println("Es hora de que posicione los barcos");
        for (Barco barco: barcos) {
            // Orientacion inicial de cada barco
            barco.setOrientacion(Orientacion.HORIZONTAL);
            // Posicion y coordenada inicial de cada barco.
            short fila = 1, columna = 1; 
            // Mapa actual
            // Las coordenadas de ubicacion actual seran actualizadas hasta que haya una posicion inicial valida
            System.out.println("Configuracion actual:");
            System.out.println(mapa);
            while(!mapa.colocarBarco(barco, fila, columna, barco.getOrientacion()))
                if (fila < mapa.dimension)
                    fila++;
                else
                    columna++;
            boolean siguiente = false;
            do {
                System.out.println("Vista previa al añadir este barco:");
                System.out.println(mapa);
                System.out.println("Longitud del barco actual: " + barco.getSize());
                System.out.println("Ingrese la coordenada deseada siguiendo el formato: \"fila,columna\" (Sin comillas ni espacio despues de la coma)(Actual: " + fila + ", " + columna + ")");
                System.out.println("Presione \"R\" para rotar la orientacion del barco (Actual: " + barco.getOrientacion() + ")");
                System.out.println("Presione \"C\" para confirmar la ubicacion de este barco");
                // tratamos de leer la entrada
                entrada = sc.next();
                if (!"r".equalsIgnoreCase(entrada) && !"c".equalsIgnoreCase(entrada) && ! entrada.matches("\\d,\\d"))
                    System.out.println("Entrada invalida");
                else {
                    // Tenemos que rotar 
                    if ("r".equalsIgnoreCase(entrada)) {
                        // Control de rotaciones validas:
                        // Tal vez despues de rotar un barco la posicion sea invalida,
                        // En ese caso, tendremos que deshacer la rotacion
                        boolean rotacionValida = true;
                        do {
                            // Primero quitamos el barco
                            if ( rotacionValida )
                                mapa.quitarBarco(barco, fila, columna);
                            // Cambiamos la orientacion
                            barco.setOrientacion(barco.getOrientacion().not());
                            // Volvemos a colocar el barco
                            rotacionValida = mapa.colocarBarco(barco, fila, columna, barco.getOrientacion());
                            // Verficamos que hayamos tenido exito
                            if (!rotacionValida) {
                                System.out.println("No se puede rotar el barco debido a que lo dejaria en una posicion invalida");
                                System.out.println("Por favor escoja otra coordenada antes de intentar rotar este barco");
                            }
                            // Si fallamos, la orientacion cambiara a la que tenia originalmente
                        } while (!rotacionValida);
                    }
                    else if ("c".equalsIgnoreCase(entrada)) {
                        siguiente = true;
                    }
                    else { // Hay que asignar la nueva fila y nueva columna 
                        // Separamos la entrada en dos strings con los valores
                        String[] coordsStr = entrada.split(",");
                        // Guardamos la ultima coordenada valida
                        short uFila = fila, uCol = columna;
                        // Asignamos los valores correspondientes
                        fila = (short) (Integer.parseInt(coordsStr[0]));
                        columna = (short) (Integer.parseInt(coordsStr[1]));
                        // Los validamos
                        if (fila < 1 || fila > mapa.dimension || 
                            columna < 1 || columna > mapa.dimension) {
                            System.out.println("Coordenada invalida");
                            fila = uFila;
                            columna = uCol;
                            siguiente = false;
                        } else {
                            mapa.quitarBarco(barco, uFila, uCol);
                            boolean posValida = mapa.colocarBarco(barco, fila, columna, barco.getOrientacion());
                            if (!posValida) {
                                System.out.println("No se puede colocar el barco en esta coordenada con esta posicion");
                                // Sabemos que la ultima posicion es valida siempre
                                mapa.colocarBarco(barco, uFila, uCol, barco.getOrientacion());
                            }
                        }
                    }
                }
            } while (!siguiente);
        }
    }
}
