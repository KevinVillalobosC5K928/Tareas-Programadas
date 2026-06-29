/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Nodo del árbol principal de contextos.
 * Cada nodo almacena una palabra del corpus y un árbol de vecinos con frecuencias.
 */
public class NodoContexto 
{
    /** Palabra principal del nodo. */
    private String palabra;
    /** Árbol con los vecinos de la palabra y sus frecuencias. */
    private ArbolFrecuencias vecinos;
    /** Hijo izquierdo del BST principal. */
    private NodoContexto izquierdo;
    /** Hijo derecho del BST principal. */
    private NodoContexto derecho;

    /**
     * Construye un nodo de contexto para una palabra dada.
     * @param palabra palabra principal del contexto
     */
    public NodoContexto(String palabra) {
        this.palabra = palabra;
        this.vecinos = new ArbolFrecuencias();
        this.izquierdo = null;
        this.derecho = null;
    }

    /** @return la palabra principal */
    public String getPalabra() { return palabra; }
    /** @return el árbol de vecinos */
    public ArbolFrecuencias getVecinos() { return vecinos; }
    /** @return hijo izquierdo */
    public NodoContexto getIzquierdo() { return izquierdo; }
    /** @return hijo derecho */
    public NodoContexto getDerecho() { return derecho; }
    /** @param izquierdo nuevo hijo izquierdo */
    public void setIzquierdo(NodoContexto izquierdo) { this.izquierdo = izquierdo; }
    /** @param derecho nuevo hijo derecho */
    public void setDerecho(NodoContexto derecho) { this.derecho = derecho; }
}
