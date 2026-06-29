/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Clase principal del programa.
 * Carga los archivos de emociones, construye los modelos y permite analizar frases ingresadas por el usuario.
 */

import java.io.IOException;
import java.util.Scanner;


public class Main 
{
    /**
     * Método principal de ejecución.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnalizadorEmociones analizador = new AnalizadorEmociones();

        try {
            analizador.inicializar("alegria.txt", "tristeza.txt", "calma.txt", "enojo.txt");
            System.out.println("Modelos cargados correctamente.");

            boolean continuar = true;
            while (continuar) {
                System.out.println("Digite una frase a analizar:");
                scanner.skip("\\R?");
                String frase = scanner.nextLine();

                Resultado resultado = analizador.analizarFrase(frase);

                System.out.println("Similitud con Feliz: " + resultado.getFeliz());
                System.out.println("Similitud con Triste: " + resultado.getTriste());
                System.out.println("Similitud con Calmado: " + resultado.getCalmado());
                System.out.println("Similitud con Enojado: " + resultado.getEnojado());
                System.out.println("Coordenada X: " + resultado.getX());
                System.out.println("Coordenada Y: " + resultado.getY());

                PlanoEmocionesFrame frame = new PlanoEmocionesFrame(resultado);
                frame.setVisible(true);

                System.out.println("¿Desea analizar otra frase? (s/n)");
                String respuesta = scanner.nextLine();
                continuar = respuesta.equalsIgnoreCase("s");
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivos: " + e.getMessage());
        }

        scanner.close();
    }
}

//Más que una tarea programada, una campo de batalla.
