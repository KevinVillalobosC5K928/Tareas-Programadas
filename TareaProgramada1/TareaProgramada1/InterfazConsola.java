import java.util.Scanner;

/**
 * Implementación de InterfazJuego usando la consola estándar.
 * Lee la entrada del usuario desde System.in y muestra salida en System.out.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class InterfazConsola implements InterfazJuego {

    /** Scanner para leer datos del usuario */
    private Scanner scanner;

    /**
     * Constructor que inicializa el Scanner sobre System.in.
     */
    public InterfazConsola() {
        scanner = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String leerTexto(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int leerEntero(String prompt, int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            System.out.print(prompt + " (" + min + "-" + max + "): ");
            String linea = scanner.nextLine().trim();
            try {
                valor = Integer.parseInt(linea);
                if (valor < min || valor > max) {
                    System.out.println("Por favor ingrese un valor entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
        return valor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int mostrarMenuPrincipal() {
        System.out.println("\n===================================");
        System.out.println("  Bienvenido al Dojo de Supervivencia");
        System.out.println("===================================");
        System.out.println("1. Ver el historial del mejor juego hasta el momento.");
        System.out.println("2. Enfrentar al oponente 1 para iniciar su juego.");
        System.out.println("3. Salir y abandonar el juego.");
        return leerEntero("Elija una opción", 1, 3);
    }
}
