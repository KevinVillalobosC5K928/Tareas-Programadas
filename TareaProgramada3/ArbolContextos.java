/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Árbol binario de búsqueda principal que asocia cada palabra con su contexto.
 * El contexto se guarda como otro árbol binario con los vecinos y sus frecuencias.
 */
import java.util.List;



public class ArbolContextos 
{
    /** Raíz del árbol principal. */
    private NodoContexto raiz;

    /**
     * Construye un árbol de contextos vacío.
     */
    public ArbolContextos() {
        this.raiz = null;
    }

    /**
     * Inserta una palabra principal si no existe y devuelve su nodo.
     * @param palabra palabra principal
     * @return nodo correspondiente a la palabra
     */
    public NodoContexto insertarPalabra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return null;
        }
        raiz = insertarRec(raiz, palabra);
        return buscar(palabra);
    }

    /**
     * Inserta recursivamente una palabra principal.
     * @param actual nodo actual
     * @param palabra palabra a insertar
     * @return referencia del subárbol
     */
    private NodoContexto insertarRec(NodoContexto actual, String palabra) {
        if (actual == null) {
            return new NodoContexto(palabra);
        }
        int comparacion = palabra.compareTo(actual.getPalabra());
        if (comparacion < 0) {
            actual.setIzquierdo(insertarRec(actual.getIzquierdo(), palabra));
        } else if (comparacion > 0) {
            actual.setDerecho(insertarRec(actual.getDerecho(), palabra));
        }
        return actual;
    }

    /**
     * Busca una palabra principal dentro del árbol.
     * @param palabra palabra a buscar
     * @return nodo encontrado o null
     */
    public NodoContexto buscar(String palabra) {
        return buscarRec(raiz, palabra);
    }

    /**
     * Busca recursivamente una palabra principal.
     * @param actual nodo actual
     * @param palabra palabra a buscar
     * @return nodo encontrado o null
     */
    private NodoContexto buscarRec(NodoContexto actual, String palabra) {
        if (actual == null || palabra == null) {
            return null;
        }
        int comparacion = palabra.compareTo(actual.getPalabra());
        if (comparacion == 0) {
            return actual;
        }
        if (comparacion < 0) {
            return buscarRec(actual.getIzquierdo(), palabra);
        }
        return buscarRec(actual.getDerecho(), palabra);
    }

    /**
     * Agrega una relación entre una palabra principal y una palabra vecina.
     * @param palabra palabra principal
     * @param vecino palabra vecina
     */
    public void agregarVecino(String palabra, String vecino) {
        if (palabra == null || vecino == null || palabra.isEmpty() || vecino.isEmpty()) {
            return;
        }
        NodoContexto nodo = insertarPalabra(palabra);
        nodo.getVecinos().insertar(vecino);
    }

    /**
     * Construye el árbol de contextos a partir de una lista de tokens.
     * Para cada palabra toma una ventana de vecinos de tamaño k a izquierda y derecha.
     * @param tokens lista de palabras limpias del corpus
     * @param k tamaño de la ventana
     */
    public void construirDesdeTokens(List<String> tokens, int k) {
        for (int i = 0; i < tokens.size(); i++) {
            String palabraCentral = tokens.get(i);
            for (int j = i - k; j <= i + k; j++) {
                if (j >= 0 && j < tokens.size() && j != i) {
                    agregarVecino(palabraCentral, tokens.get(j));
                }
            }
        }
    }

    /**
     * Obtiene el árbol de vecinos de una palabra dada.
     * @param palabra palabra principal
     * @return árbol de vecinos o null si la palabra no existe
     */
    public ArbolFrecuencias obtenerContexto(String palabra) {
        NodoContexto nodo = buscar(palabra);
        if (nodo == null) {
            return null;
        }
        return nodo.getVecinos();
    }
}
