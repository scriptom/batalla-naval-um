/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.utils;

import java.util.Scanner;
import java.io.StringReader;
import java.io.IOException;

/**
 *
 * @author Usuario Estandar
 */
public class Menu {
    private String planteamiento;
    private String[] opciones;
    private int eleccion;
    
    public Menu(String planteamiento, String ...opciones) {
        this.planteamiento = planteamiento;
        this.opciones = opciones;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(planteamiento);
        for (int i = 0; i < opciones.length; i++)
            sb.append("\n\t").append(i + 1).append(" ").append(opciones[i]);
            
        return sb.toString();
    }
    
    /**
     * Asegura que la entrada del usuario sea un numero de opcion valido
     * @param entrada
     * @return 
     */
    protected boolean validarEntrada(String entrada) {
        if (entrada == null)
            return false;
        eleccion = 0;
        for (char currentChar: entrada.toCharArray())
            if (!Character.isDigit(currentChar))
                return false;
            else
                eleccion = eleccion * 10 + (int)(currentChar - 48);
        
        // Si llegamos hasta aca, deberiamos tener un numero concreto. Validemos que este en las opciones
        return ( eleccion >= 0 && eleccion <= opciones.length );
    }
    
    /**
     * Garantiza la devolucion de una opcion valida del menu
     * @return int eleccion La seleccion del usuario
     */
    public int getEleccion() {
        Scanner scanner = new Scanner(System.in);
        boolean valid;
        do {
            if ( ! (valid = validarEntrada(scanner.next()) ) )
                System.out.println("Opcion invalida. Vuelva a intentar");
        } while ( ! valid );
        return eleccion;
    }
    
    /**
     * Coloca un nuevo planteamiento, y lo imprime
     * @param planteamiento
     * @param opciones 
     */
    public void set(String planteamiento, String ...opciones) {
        this.planteamiento = planteamiento;
        this.opciones = opciones;
        System.out.println(this);
    }
}
