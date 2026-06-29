/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Orquesta la construcción del árbol global, los modelos de emoción y el análisis de frases.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class AnalizadorEmociones 
{
    /** Ventana de contexto usada para vecinos. */
    public static final int VENTANA = 2;

    /** Árbol principal de contextos construido con el corpus. */
    private ArbolContextos arbolPrincipal;
    /** Modelo para alegría/felicidad. */
    private ModeloEmocion modeloFeliz;
    /** Modelo para tristeza. */
    private ModeloEmocion modeloTriste;
    /** Modelo para calma. */
    private ModeloEmocion modeloCalmado;
    /** Modelo para enojo. */
    private ModeloEmocion modeloEnojado;

    /**
     * Construye el analizador vacío.
     */
    public AnalizadorEmociones() {
        this.arbolPrincipal = new ArbolContextos();
        this.modeloFeliz = new ModeloEmocion("Feliz");
        this.modeloTriste = new ModeloEmocion("Triste");
        this.modeloCalmado = new ModeloEmocion("Calmado");
        this.modeloEnojado = new ModeloEmocion("Enojado");
    }

    /**
     * Carga los archivos de emociones, construye el corpus global y luego crea los modelos.
     * @param archivoFeliz ruta del archivo de alegría
     * @param archivoTriste ruta del archivo de tristeza
     * @param archivoCalma ruta del archivo de calma
     * @param archivoEnojo ruta del archivo de enojo
     * @throws IOException si falla la lectura de archivos
     */
    public void inicializar(String archivoFeliz, String archivoTriste, String archivoCalma, String archivoEnojo) throws IOException {
        List<String> tokensFeliz = Procesador.tokenizar(Procesador.leerArchivo(archivoFeliz));
        List<String> tokensTriste = Procesador.tokenizar(Procesador.leerArchivo(archivoTriste));
        List<String> tokensCalma = Procesador.tokenizar(Procesador.leerArchivo(archivoCalma));
        List<String> tokensEnojo = Procesador.tokenizar(Procesador.leerArchivo(archivoEnojo));

        List<String> corpusGlobal = new ArrayList<String>();
        corpusGlobal.addAll(tokensFeliz);
        corpusGlobal.addAll(tokensTriste);
        corpusGlobal.addAll(tokensCalma);
        corpusGlobal.addAll(tokensEnojo);

        arbolPrincipal.construirDesdeTokens(corpusGlobal, VENTANA);

        modeloFeliz.construirModelo(tokensFeliz, arbolPrincipal);
        
        modeloTriste.construirModelo(tokensTriste, arbolPrincipal);
        modeloCalmado.construirModelo(tokensCalma, arbolPrincipal);
        modeloEnojado.construirModelo(tokensEnojo, arbolPrincipal);
    }
    

    /**
     * Analiza una frase del usuario y devuelve sus similitudes y coordenadas del plano.
     * @param frase frase digitada por el usuario
     * @return resultado del análisis
     */
    public Resultado analizarFrase(String frase) {
        List<String> tokensFrase = Procesador.tokenizar(frase);
        ModeloEmocion modeloFrase = new ModeloEmocion("Frase");
        modeloFrase.construirModelo(tokensFrase, arbolPrincipal);

        double feliz = Calcular.similitudCoseno(modeloFrase.getFrecuencias(), modeloFeliz.getFrecuencias());
        double triste = Calcular.similitudCoseno(modeloFrase.getFrecuencias(), modeloTriste.getFrecuencias());
        double calmado = Calcular.similitudCoseno(modeloFrase.getFrecuencias(), modeloCalmado.getFrecuencias());
        double enojado = Calcular.similitudCoseno(modeloFrase.getFrecuencias(), modeloEnojado.getFrecuencias());

        double sumaHorizontal = feliz + triste;
        double sumaVertical = calmado + enojado;
        
        double x = (sumaHorizontal > 0) ? feliz / sumaHorizontal : 0.5;
        double y = (sumaVertical > 0) ? calmado / sumaVertical : 0.5;
        return new Resultado(feliz, triste, calmado, enojado, x, y);
    }
}
