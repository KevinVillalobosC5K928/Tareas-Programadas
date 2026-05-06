import javax.swing.JOptionPane;

/**
 * Implementación de InterfazJuego usando ventanas JOptionPane (GUI de texto).
 * Muestra mensajes y recoge entradas mediante diálogos gráficos.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class InterfazGUI implements InterfazJuego {

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Dojo de Supervivencia",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String leerTexto(String prompt) {
        String respuesta = JOptionPane.showInputDialog(null, prompt, "Dojo de Supervivencia",
                JOptionPane.QUESTION_MESSAGE);
        if (respuesta == null) respuesta = "";
        return respuesta.trim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int leerEntero(String prompt, int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            String entrada = JOptionPane.showInputDialog(null,
                    prompt + " (" + min + "-" + max + "):", "Dojo de Supervivencia",
                    JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) entrada = "";
            try {
                valor = Integer.parseInt(entrada.trim());
                if (valor < min || valor > max) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor ingrese un valor entre " + min + " y " + max + ".",
                            "Valor inválido", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Entrada inválida. Ingrese un número entero.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return valor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int mostrarMenuPrincipal() {
        String[] opciones = {
            "1. Ver el historial del mejor juego hasta el momento.",
            "2. Enfrentar al oponente 1 para iniciar su juego.",
            "3. Salir y abandonar el juego."
        };
        String menu = "=== Bienvenido al Dojo de Supervivencia ===\n\n" +
                      "1. Ver el historial del mejor juego hasta el momento.\n" +
                      "2. Enfrentar al oponente 1 para iniciar su juego.\n" +
                      "3. Salir y abandonar el juego.";
        String seleccion = (String) JOptionPane.showInputDialog(null, menu,
                "Dojo de Supervivencia", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);
        if (seleccion == null) return 3;
        return Integer.parseInt(seleccion.substring(0, 1));
    }
}
