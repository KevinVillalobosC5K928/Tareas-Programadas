/**
 * Representa una pieza individual detectada dentro de un recipiente en la imagen.
 * Cada pieza tiene un tipo simbólico (entero), un color RGB original, un tamaño
 * en píxeles, una forma geométrica y un nombre de color legible.
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Pieza
{
    /** Identificador simbólico del tipo de pieza (1, 2, 3, ...). */
    private int tipo;

    /** Valor RGB original del color dominante de la pieza. */
    private int color;

    /** Cantidad de píxeles que conforman la pieza. */
    private int tamanio;

    /** Forma geométrica de la pieza: "circulo" o "cuadrado". */
    private String forma;

    /** Nombre legible del color (ej: "Rojo", "Azul", "Verde"). */
    private String nombreColor;

    /**
     * Construye una Pieza con todos sus atributos definidos.
     *
     * @param tipo        identificador simbólico del tipo
     * @param color       valor RGB del color dominante
     * @param tamanio     cantidad de píxeles de la pieza
     * @param forma       "circulo" o "cuadrado"
     * @param nombreColor nombre legible del color
     */
    public Pieza(int tipo, int color, int tamanio, String forma, String nombreColor) {
        this.tipo = tipo;
        this.color = color;
        this.tamanio = tamanio;
        this.forma = forma;
        this.nombreColor = nombreColor;
    }

    /**
     * Retorna el identificador simbólico del tipo de pieza.
     *
     * @return tipo numérico de la pieza
     */
    public int getTipo() { return tipo; }

    /**
     * Asigna el identificador simbólico del tipo de pieza.
     *
     * @param tipo nuevo tipo numérico
     */
    public void setTipo(int tipo) { this.tipo = tipo; }

    /**
     * Retorna el valor RGB del color de la pieza.
     *
     * @return entero con el valor RGB
     */
    public int getColor() { return color; }

    /**
     * Retorna la cantidad de píxeles de la pieza.
     *
     * @return tamaño en píxeles
     */
    public int getTamanio() { return tamanio; }

    /**
     * Retorna la forma geométrica de la pieza.
     *
     * @return "circulo" o "cuadrado"
     */
    public String getForma() { return forma; }

    /**
     * Retorna el nombre legible del color de la pieza.
     *
     * @return nombre del color en español
     */
    public String getNombreColor() { return nombreColor; }

    /**
     * Retorna una representación en texto de la pieza.
     *
     * @return cadena con tipo, color y forma
     */
    @Override
    public String toString() {
        return "Pieza[tipo=" + tipo + ", color=" + nombreColor + ", forma=" + forma + "]";
    }
}