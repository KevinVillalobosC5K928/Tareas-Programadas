/**
 * Interfaz que define los métodos de entrada/salida del juego.
 * Permite implementar tanto la versión de consola como la de GUI (JOptionPane).
 * @author Kevin Villalobos
 * @version 1.0
 */
public interface InterfazJuego {

    /**
     * Muestra un mensaje en la interfaz.
     * @param mensaje El mensaje a mostrar.
     */
    void mostrarMensaje(String mensaje);

    /**
     * Solicita y devuelve un texto ingresado por el usuario.
     * @param prompt El texto de solicitud.
     * @return El texto ingresado por el usuario.
     */
    String leerTexto(String prompt);

    /**
     * Solicita un número entero dentro del rango indicado.
     * @param prompt El texto de solicitud.
     * @param min Valor mínimo permitido.
     * @param max Valor máximo permitido.
     * @return El entero ingresado por el usuario, dentro del rango [min, max].
     */
    int leerEntero(String prompt, int min, int max);

    /**
     * Muestra el menú principal de la interfaz segun la opcion elegida.
     * @return La opción elegida (1, 2 o 3).
     */
    int mostrarMenuPrincipal();
}
