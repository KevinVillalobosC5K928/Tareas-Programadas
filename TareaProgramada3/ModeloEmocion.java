/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Representa el modelo de frecuencias de una emoción o de una frase.
 * El modelo se construye uniendo los contextos de las palabras buscadas en el árbol principal.
 */
import java.util.List;



public class ModeloEmocion 
{
    /** Nombre del modelo, por ejemplo Feliz, Triste o Frase. */
    private String nombre;
    /** Árbol de frecuencias acumuladas del modelo. */
    private ArbolFrecuencias frecuencias;

    /**
     * Construye un modelo vacío con un nombre dado.
     * @param nombre nombre del modelo
     */
    public ModeloEmocion(String nombre) {
        this.nombre = nombre;
        this.frecuencias = new ArbolFrecuencias();
    }

    /**
     * Construye el modelo a partir de una lista de palabras base.
     * Para cada palabra se busca su contexto y se acumula en el árbol de frecuencias.
     * @param palabrasBase palabras del archivo o frase
     * @param arbolPrincipal árbol global de contextos
     */
    public void construirModelo(List<String> palabrasBase, ArbolContextos arbolPrincipal) {
        for (int i = 0; i < palabrasBase.size(); i++) {
            ArbolFrecuencias contexto = arbolPrincipal.obtenerContexto(palabrasBase.get(i));
            if (contexto != null) {
                contexto.acumularEn(frecuencias);
            }
        }
    }

    /** @return nombre del modelo */
    public String getNombre() { return nombre; }
    /** @return árbol de frecuencias del modelo */
    public ArbolFrecuencias getFrecuencias() { return frecuencias; }
}
