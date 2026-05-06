/**
 * Almacena el mejor marcador registrado durante el torneo del Dojo.
 * Guarda el nombre del equipo, oponentes derrotados, jugadores activos y total de combos.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Marcador {
    /** Nombre del equipo con el mejor marcador */
    private String nombreEquipo;
    /** Número de oponentes que el equipo derrotó */
    private int oponentesDerrotados;
    /** Número de jugadores activos al finalizar */
    private int jugadoresActivos;
    /** Total de combos realizados */
    private int totalCombos;
    /** Indica si hay un marcador registrado */
    private boolean hayMarcador;

    /**
     * Constructor del marcador. Inicia sin registro.
     */
    public Marcador() {
        hayMarcador = false;
    }

    /**
     * Devuelve verdadero si el marcador es mejor que el actual.
     * Criterios en orden: más oponentes derrotados, más jugadores activos, menos combos.
     * @param equipo El equipo a comparar.
     * @param jugActivos Jugadores activos del equipo al finalizar.
     * @return true si el equipo supera el marcador actual.
     */
    public boolean esMejor(Equipo equipo, int jugActivos) {
        if (!hayMarcador) return true;
        if (equipo.getOponentesDerrotados() > oponentesDerrotados) return true;
        if (equipo.getOponentesDerrotados() == oponentesDerrotados) {
            if (jugActivos > jugadoresActivos) return true;
            if (jugActivos == jugadoresActivos && equipo.getTotalCombos() < totalCombos) return true;
        }
        return false;
    }

    /**
     * Actualiza el marcador con los datos del mejor equipo actual.
     * @param equipo El equipo con el nuevo mejor marcador.
     * @param jugActivos Los jugadores activos del equipo al finalizar.
     */
    public void actualizar(Equipo equipo, int jugActivos) {
        this.nombreEquipo = equipo.getNombre();
        this.oponentesDerrotados = equipo.getOponentesDerrotados();
        this.jugadoresActivos = jugActivos;
        this.totalCombos = equipo.getTotalCombos();
        this.hayMarcador = true;
    }

    /**
     * Genera una representación textual del mejor marcador.
     * @return El historial del mejor marcador como texto.
     */
    public String toString() {
        if (!hayMarcador) return "No hay récord registrado aún.";
        return "=== MEJOR MARCADOR ===\n" +
               "Equipo: " + nombreEquipo + "\n" +
               "Oponentes derrotados: " + oponentesDerrotados + "\n" +
               "Jugadores activos al finalizar: " + jugadoresActivos + "\n" +
               "Total de combos realizados: " + totalCombos;
    }

    /**
     * Indica si existe un marcador registrado.
     * @return true si hay al menos un registro.
     */
    public boolean hayMarcador() { return hayMarcador; }
}
