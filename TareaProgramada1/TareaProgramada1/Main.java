/**
 * Clase principal del juego Dojo de Supervivencia.
 * Inicia el torneo y permite a múltiples equipos participar.
 * 
 * <p>Uso:</p>
 * <pre>
 *   java Main         -> interfaz de consola
 *   java Main -consola -> interfaz de consola
 *   java Main -gui    -> interfaz gráfica con JOptionPane
 * </pre>
 *
 * @author Equipo
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de la aplicación.
     * @param args Argumentos de línea de comandos: -gui para ventanas, -consola o ninguno para consola.
     */
    public static void main(String[] args) {
        // Seleccionar interfaz según argumento
        InterfazJuego ui;
        if (args.length > 0 && args[0].equalsIgnoreCase("-gui")) {
            ui = new InterfazGUI();
        } else {
            ui = new InterfazConsola();
        }

        Marcador marcador = new Marcador();
        boolean continuar = true;

        ui.mostrarMensaje("¡Bienvenido al Torneo del Dojo de Supervivencia!");

        while (continuar) {
            int opcion = ui.mostrarMenuPrincipal();
            switch (opcion) {
                case 1:
                    ui.mostrarMensaje(marcador.toString());
                    break;
                case 2:
                    Juego juego = new Juego(ui, marcador);
                    juego.iniciarPartida();
                    break;
                case 3:
                    ui.mostrarMensaje("¡Hasta pronto! Gracias por visitar el Dojo.");
                    continuar = false;
                    break;
            }
        }
    }
}
