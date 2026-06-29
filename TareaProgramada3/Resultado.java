/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Almacena los resultados numéricos del análisis de una frase.
 * Incluye similitudes con las cuatro emociones y coordenadas calculadas para el plano.
 */
public class Resultado
{
    /** Similitud con felicidad. */
    private double feliz;
    /** Similitud con tristeza. */
    private double triste;
    /** Similitud con calma. */
    private double calmado;
    /** Similitud con enojo. */
    private double enojado;
    /** Coordenada horizontal normalizada. */
    private double x;
    /** Coordenada vertical normalizada. */
    private double y;

    /**
     * Construye un resultado completo.
     * @param feliz similitud con felicidad
     * @param triste similitud con tristeza
     * @param calmado similitud con calma
     * @param enojado similitud con enojo
     * @param x coordenada horizontal
     * @param y coordenada vertical
     */
    public Resultado(double feliz, double triste, double calmado, double enojado, double x, double y) {
        this.feliz = feliz;
        this.triste = triste;
        this.calmado = calmado;
        this.enojado = enojado;
        this.x = x;
        this.y = y;
    }

    /** @return similitud con felicidad */
    public double getFeliz() { return feliz; }
    /** @return similitud con tristeza */
    public double getTriste() { return triste; }
    /** @return similitud con calma */
    public double getCalmado() { return calmado; }
    /** @return similitud con enojo */
    public double getEnojado() { return enojado; }
    /** @return coordenada x */
    public double getX() { return x; }
    /** @return coordenada y */
    public double getY() { return y; }
}
