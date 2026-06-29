import java.util.List;

/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Clase utilitaria para calcular similitud de coseno entre dos modelos.
 */
public class Calcular {
    /**
     * Calcula la similitud de coseno entre dos árboles de frecuencias.
     * @param a primer modelo
     * @param b segundo modelo
     * @return valor de similitud entre 0 y 1
     */
    public static double similitudCoseno(ArbolFrecuencias a, ArbolFrecuencias b) {
        List<PalabraFrecuencia> listaA = a.aLista();
        List<PalabraFrecuencia> listaB = b.aLista();

        double productoPunto = 0.0;
        double normaA = 0.0;
        double normaB = 0.0;

        for (int i = 0; i < listaA.size(); i++) {
            double freqA = listaA.get(i).getFrecuencia();
            normaA += freqA * freqA;
            NodoFrecuencia enB = b.buscar(listaA.get(i).getPalabra());
            if (enB != null) {
                productoPunto += freqA * enB.getFrecuencia();
            }
        }

        for (int i = 0; i < listaB.size(); i++) {
            double freqB = listaB.get(i).getFrecuencia();
            normaB += freqB * freqB;
        }

        if (normaA == 0 || normaB == 0) {
            return 0.0;
        }

        double resultado = productoPunto / (Math.sqrt(normaA) * Math.sqrt(normaB));
        return Math.min(1.0, resultado);
    }
}