package batallanaval.mapa;

/**
 * Tipo enumerado de Orientacion
 * @author TomasDev
 */
public enum Orientacion{ 
    VERTICAL, HORIZONTAL;
    public Orientacion not() {
        return this == HORIZONTAL ? VERTICAL : HORIZONTAL;
    }
};
