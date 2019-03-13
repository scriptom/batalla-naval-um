/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batallanaval.mapa;

import batallanaval.barcos.Barco;

/**
 * Mapa de Batalla Naval
 * @author Usuario Estandar
 */
public class Mapa {
    public final short dimension;
    private Casilla[][] tablero;
    public Mapa(short dimension) {
        this.dimension = dimension;
        tablero = new Casilla[dimension][dimension];
        for (short fila = 0; fila < dimension; fila++)
            for (short columna = 0; columna < dimension; columna++) {
                Casilla nueva = tablero[fila][columna] = new Casilla();
                // Si no es la primera fila, tenemos que conectar el nodo superior
                if ( fila != 0 )
                    tablero[fila-1][columna].setNodoAba(nueva);
                // Si no es la primera columna, tenemos que conectar el nodo adyacente
                if ( columna != 0 )
                    tablero[fila][columna-1].setNodoDer(nueva);
            }
    }
    
    public boolean colocarBarco(Barco barco, short fila, short columna, Orientacion orientacion) {
        // Verificamos que la cabeza del barco este en la matriz
        if ( fila < 1 || fila > dimension || columna < 1 || columna > dimension )
            return false;
        // Verificamos que el resto del barco tambien lo este (Dependemos de la orientacion)
        // Determinamos si la Orientacion es Horizontal (En cuyo caso sumaremos la columna y el largo del barco
        // en caso contrario, sumaremos la fila y el largo del barco
        // El -1 es para tener el indice correcto de la fila y columna.
        if ( (orientacion == Orientacion.HORIZONTAL ? columna : fila) + barco.getSize() -1 > dimension )
            return false;
        // Actualizamos la orientacion del barco, si no lo estaba
        if ( barco.getOrientacion() == null || barco.getOrientacion() != orientacion )
            barco.setOrientacion(orientacion);
        // Si no hemos regresado hasta ahora, el barco puede ser posicionado
//        boolean resultado = colocarBarcoPiezaPorPieza(barco, (short) 0, fila, columna, orientacion);
        return barco.posicionar(tablero[fila-1][columna-1]);
    }
    
    public boolean quitarBarco(Barco barco, short fila, short columna) {
        // Verificamos que la cabeza del barco este en la matriz
        if ( fila < 1 || fila > dimension || columna < 1 || columna > dimension )
            return false;
        // Quitamos comodamente
        barco.remover(tablero[fila-1][columna-1]);
        return true;
    }
    
    /**
     * Coloca pieza por pieza, recursivamente, un barco en el mapa
     * @param barco El barco a posicionar
     * @param pieza La pieza actual a posicionar
     * @param fila  La fila donde colocarlo
     * @param columna La columna donde colocarlo
     * @param o     La orientacion de la ubicacion (Para saber en que eje movernos)
     * @return 
     */
    private boolean colocarBarcoPiezaPorPieza(Barco barco, short pieza, short fila, short columna, Orientacion o) {
        /* Forwarding */
        
        // Comprobamos si ya terminamos de posicionar
        if (pieza > barco.getSize())
            return true;
        // Comprobamos si hay barco en esta posicion
        if (tablero[fila-1][columna-1].hasBarco())
            return false;
        // Posicionamos tranquilamente
        tablero[fila-1][columna-1].setPieza(barco.getPiezaEn(pieza));
        // Guardamos el resultado del siguiente posicionamiento
        short sigFila = (short) (o == Orientacion.HORIZONTAL ? fila : fila + 1);
        short sigColumna = (short) (o == Orientacion.HORIZONTAL ? columna + 1 : columna);
        // Tratamos de colocar la siguiente pieza recursivamente
        boolean sigPos = colocarBarcoPiezaPorPieza(barco, (short)(pieza + 1), sigFila, sigColumna, o);
        
        /* Backwarding */
        
        // Si la siguiente fallo, entonces deshacemos lo que hicimos
        if (!sigPos)
            tablero[fila-1][columna-1].setPieza(null);
        // Regresamos
        return sigPos;
    }
    
    public Casilla getCasilla(short fila, short columna) {
        fila--; columna--;
        if (fila < 0 || fila >= dimension || columna < 0 || columna >= dimension)
            return null;
        return tablero[fila][columna];
    }
    
    public String toString(boolean ocultar) {
        // inicializamos el StringBuilder con el espacio inicial
        StringBuilder sb = new StringBuilder("  ");
        // anexamos los numeros de las columnas
        for (int i = 1; i <= dimension; i++)
            sb.append(i).append(" "); 
        // saltamos a la siguiente linea y de una vez colocamos un espacio
        sb.append("\n  ");
        // anexamos 2 "_" para delimitar el borde superior del tablero
        for (int i = 1; i <= dimension; i++)
            sb.append("__");
        // continuamos imprimiendo las filas
        sb.append("\n");
        for (int f = 0; f < dimension; f++) { // iteracion de filas
            // Sumamos 1 para tener numeracion basada en 1, y un "|" como espaciador
            sb.append(f + 1).append("|");
            for (int c = 0; c < dimension; c++ ) // iteracion de columnas
                sb.append(tablero[f][c].toString(ocultar)).append("|");
            sb.append("\n");
        }
        sb.append("  ");
        for (int i = 1; i <= dimension; i++)
            sb.append("__");
        sb.append("\n");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return toString(false);
    }
}
