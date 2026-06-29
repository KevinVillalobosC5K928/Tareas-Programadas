/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Representa un par palabra-frecuencia.
 * Sirve como estructura auxiliar para recorrer árboles y calcular similitudes.
 */
public class PalabraFrecuencia 
{
    /** Palabra del par. */
    private String palabra;
    /** Frecuencia asociada. */
    private int frecuencia;

    /**
     * Construye un nuevo par palabra-frecuencia.
     * @param palabra palabra a almacenar
     * @param frecuencia frecuencia asociada
     */
    public PalabraFrecuencia(String palabra, int frecuencia) {
        this.palabra = palabra;
        this.frecuencia = frecuencia;
    }

    /** @return la palabra almacenada */
    public String getPalabra() { return palabra; }
    /** @return la frecuencia asociada */
    public int getFrecuencia() { return frecuencia; }
}
