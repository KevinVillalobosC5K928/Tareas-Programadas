import java.util.ArrayList;

/**
 * Clase principal del programa. Coordina la percepción de la imagen,
 * la resolución del puzzle y la visualización de la solución paso a paso.
 *
 * <p>Uso desde consola:</p>
 * <pre>
 *   java -cp .:Imagen.jar Main imagen.gif
 * </pre>
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Main
{

    /**
     * Punto de entrada del programa.
     * Lee la imagen indicada por argumento, procesa la percepción,
     * ejecuta el Solucionar BFS y muestra el resultado en consola.
     *
     * @param args argumentos de consola: args[0] = nombre del archivo de imagen
     */
    public static void main(String[] args) {
        /** Determinar nombre de archivo */
        String nombreArchivo = "dibujo.gif";
        if (args.length > 0) {
            nombreArchivo = args[0];
        }

        System.out.println("Cargando imagen: " + nombreArchivo);

        /** Cargar imagen con el utilitario provisto*/
        Imagen imagen;
        try {
            imagen = new Imagen(nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error: No se pudo cargar la imagen '" + nombreArchivo + "'");
            System.out.println("Asegúrese de que el archivo existe en el directorio del proyecto.");
            return;
        }

        /** Obtener matriz de píxeles */
        int[][] matrizPixeles = imagen.getMatriz();

        if (matrizPixeles == null || matrizPixeles.length == 0) {
            System.out.println("Error: La imagen está vacía o no pudo leerse.");
            return;
        }

        System.out.println("Imagen cargada: " + matrizPixeles.length + " filas x "
                + matrizPixeles[0].length + " columnas");

        /** Percepción: convertir píxeles en recipientes simbólicos */
        System.out.println("Procesando imagen...");
        Percepcion percepcion = new Percepcion(matrizPixeles);
        Recipiente[] recipientes = percepcion.procesar();
        Pieza[] leyenda = percepcion.getLeyenda();

        if (recipientes == null || recipientes.length == 0) {
            System.out.println("No se detectaron recipientes en la imagen.");
            return;
        }

        System.out.println("Recipientes detectados: " + recipientes.length);

        /** Crear estado inicial*/
        Estado estadoInicial = new Estado(recipientes);

        /** Mostrar estado inicial*/
        Visualizar Visualizar = new Visualizar();
        System.out.println();
        System.out.println("=== ESTADO INICIAL EN FORMA SIMBÓLICA ===");
        Visualizar.mostrarLeyenda(leyenda);
        Visualizar.imprimirMatriz(estadoInicial.getRecipientes());
        System.out.println();

        /** Resolver con BFS*/
        System.out.println("Buscando solución...");
        Solucionar Solucionar = new Solucionar();
        ArrayList<Estado> camino = Solucionar.resolver(estadoInicial);

        /** Mostrar resultado*/
        if (camino == null) {
            Visualizar.mostrarSinSolucion();
        } else {
            System.out.println("Solución encontrada. Mostrando pasos:");
            System.out.println();
            /** Mostrar desde el segundo estado (el primero es el inicial ya impreso)*/
            for (int i = 1; i < camino.size(); i++) {
                Estado e = camino.get(i);
                System.out.println(e.getMovimiento());
                Visualizar.imprimirMatriz(e.getRecipientes());
                System.out.println();
            }
            System.out.println("=== SOLUCIÓN ENCONTRADA EN " + (camino.size() - 1) + " MOVIMIENTOS ===");
        }
    }
}