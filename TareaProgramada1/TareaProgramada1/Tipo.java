/**
 * Enumeración que representa los tres tipos de cartas posibles en el juego.
 * Los tipos determinan las relaciones de afectación entre cartas.
 * @author Kevin Villalobos
 * @version 1.0
 */
public enum Tipo {
    /** Tipo Aire: afecta a Tierra */
    AIRE,
    /** Tipo Tierra: afecta a Agua */
    TIERRA,
    /** Tipo Agua: afecta a Aire */
    AGUA
}
