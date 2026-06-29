/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Árbol binario de búsqueda que almacena palabras y sus frecuencias.
 * Sirve para representar contextos, modelos de emociones y modelo de frase.
 */
import java.util.ArrayList;
import java.util.List;



public class ArbolFrecuencias 
{
    
    
    
    
    /** Raíz del árbol. */
    private NodoFrecuencia raiz;

    /**
     * Construye un árbol vacío.
     */
    public ArbolFrecuencias() {
        this.raiz = null;
    }

    /**
     * Inserta una palabra. Si ya existe, incrementa su frecuencia en 1.
     * @param palabra palabra a insertar
     */
    public void insertar(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return;
        }
        raiz = insertarRec(raiz, palabra, 1);
    }

    /**
     * Inserta una palabra sumando una frecuencia específica.
     * @param palabra palabra a insertar
     * @param cantidad frecuencia a agregar
     */
    public void insertarConFrecuencia(String palabra, int cantidad) {
        if (palabra == null || palabra.isEmpty() || cantidad <= 0) {
            return;
        }
        raiz = insertarRec(raiz, palabra, cantidad);
    }

    /**
     * Inserta recursivamente una palabra en el árbol.
     * @param actual nodo actual
     * @param palabra palabra a insertar
     * @param cantidad cantidad a sumar
     * @return la nueva referencia del subárbol
     */
    private NodoFrecuencia insertarRec(NodoFrecuencia actual, String palabra, int cantidad) {
        if (actual == null) {
            NodoFrecuencia nuevo = new NodoFrecuencia(palabra);
            if (cantidad > 1) {
                nuevo.sumarFrecuencia(cantidad - 1);
            }
            return nuevo;
        }

        int comparacion = palabra.compareTo(actual.getPalabra());
        if (comparacion < 0) {
            actual.setIzquierdo(insertarRec(actual.getIzquierdo(), palabra, cantidad));
        } else if (comparacion > 0) {
            actual.setDerecho(insertarRec(actual.getDerecho(), palabra, cantidad));
        } else {
            actual.sumarFrecuencia(cantidad);
        }
        return actual;
    }

    /**
     * Busca una palabra en el árbol.
     * @param palabra palabra a buscar
     * @return el nodo encontrado o null si no existe
     */
    public NodoFrecuencia buscar(String palabra) {
        return buscarRec(raiz, palabra);
    }

    /**
     * Busca recursivamente una palabra.
     * @param actual nodo actual
     * @param palabra palabra a buscar
     * @return nodo encontrado o null
     */
    private NodoFrecuencia buscarRec(NodoFrecuencia actual, String palabra) {
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
     * Verifica si una palabra existe en el árbol.
     * @param palabra palabra a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean contiene(String palabra) {
        return buscar(palabra) != null;
    }

    /**
     * Agrega al árbol destino todos los nodos de este árbol con sus frecuencias.
     * @param destino árbol en el que se acumulan las frecuencias
     */
    public void acumularEn(ArbolFrecuencias destino) {
        acumularEnRec(raiz, destino);
    }

    /**
     * Recorre el árbol en inorden y acumula palabras en el árbol destino.
     * @param actual nodo actual
     * @param destino árbol destino
     */
    private void acumularEnRec(NodoFrecuencia actual, ArbolFrecuencias destino) {
        if (actual == null) {
            return;
        }
        acumularEnRec(actual.getIzquierdo(), destino);
        destino.insertarConFrecuencia(actual.getPalabra(), actual.getFrecuencia());
        acumularEnRec(actual.getDerecho(), destino);
    }

    /**
     * Convierte el árbol a una lista de pares palabra-frecuencia en inorden.
     * @return lista con las entradas del árbol
     */
    public List<PalabraFrecuencia> aLista() {
        List<PalabraFrecuencia> lista = new ArrayList<PalabraFrecuencia>();
        aListaRec(raiz, lista);
        return lista;
    }

    /**
     * Recorre el árbol y llena una lista con sus elementos.
     * @param actual nodo actual
     * @param lista lista resultado
     */
    private void aListaRec(NodoFrecuencia actual, List<PalabraFrecuencia> lista) {
        if (actual == null) {
            return;
        }
        aListaRec(actual.getIzquierdo(), lista);
        lista.add(new PalabraFrecuencia(actual.getPalabra(), actual.getFrecuencia()));
        aListaRec(actual.getDerecho(), lista);
    }

    /**
     * Obtiene la raíz del árbol.
     * @return nodo raíz
     */
    public NodoFrecuencia getRaiz() {
        return raiz;
    }

    /**
     * Genera una representación en texto del árbol en inorden.
     * @return texto con palabra y frecuencia por línea
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(raiz, sb);
        return sb.toString();
    }

    /**
     * Construye el texto de salida recorriendo el árbol.
     * @param actual nodo actual
     * @param sb acumulador de texto
     */
    private void toStringRec(NodoFrecuencia actual, StringBuilder sb) {
        if (actual == null) {
            return;
        }
        toStringRec(actual.getIzquierdo(), sb);
        sb.append(actual.getPalabra()).append(" -> ").append(actual.getFrecuencia()).append("\n");
        toStringRec(actual.getDerecho(), sb);
    }
}
