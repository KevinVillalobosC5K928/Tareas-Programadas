/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Nodo de un árbol binario de búsqueda que almacena una palabra y su frecuencia.
 * Se utiliza tanto para modelos de frecuencia como para árboles de vecinos.
 */
public class NodoFrecuencia 
{
    /** Palabra almacenada en el nodo. */
    private String palabra;
    /** Frecuencia acumulada de la palabra. */
    private int frecuencia;
    /** Referencia al hijo izquierdo. */
    private NodoFrecuencia izquierdo;
    /** Referencia al hijo derecho. */
    private NodoFrecuencia derecho;

    /**
     * Construye un nodo con frecuencia inicial 1.
     * @param palabra palabra a almacenar
     */
    public NodoFrecuencia(String palabra) {
        this.palabra = palabra;
        this.frecuencia = 1;
        this.izquierdo = null;
        this.derecho = null;
    }

    /** @return la palabra del nodo */
    public String getPalabra() { return palabra; }
    /** @return la frecuencia del nodo */
    public int getFrecuencia() { return frecuencia; }
    /** @return el hijo izquierdo */
    public NodoFrecuencia getIzquierdo() { return izquierdo; }
    /** @return el hijo derecho */
    public NodoFrecuencia getDerecho() { return derecho; }
    /** @param izquierdo nuevo hijo izquierdo */
    public void setIzquierdo(NodoFrecuencia izquierdo) { this.izquierdo = izquierdo; }
    /** @param derecho nuevo hijo derecho */
    public void setDerecho(NodoFrecuencia derecho) { this.derecho = derecho; }

    /**
     * Incrementa la frecuencia en una unidad.
     */
    public void incrementar() {
        this.frecuencia++;
    }

    /**
     * Suma una cantidad dada a la frecuencia actual.
     * @param cantidad valor a sumar
     */
    public void sumarFrecuencia(int cantidad) {
        this.frecuencia += cantidad;
    }
}
