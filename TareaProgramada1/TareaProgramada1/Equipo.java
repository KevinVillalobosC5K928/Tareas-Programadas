/**
 * Representa un equipo de jugadores en el juego Dojo de Supervivencia.
 * El equipo tiene un nombre, tres jugadores y registra estadísticas de juego.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Equipo {
    /** Nombre del equipo */
    private String nombre;
    /** Jugador 1 del equipo */
    private Jugador jugador1;
    /** Jugador 2 del equipo */
    private Jugador jugador2;
    /** Jugador 3 del equipo */
    private Jugador jugador3;
    /** Número de oponentes derrotados */
    private int oponentesDerrotados;
    /** Total de combos realizados durante el juego */
    private int totalCombos;

    /**
     * Constructor del equipo con sus tres jugadores.
     * @param nombre El nombre del equipo.
     * @param jugador1 El primer jugador.
     * @param jugador2 El segundo jugador.
     * @param jugador3 El tercer jugador.
     */
    public Equipo(String nombre, Jugador jugador1, Jugador jugador2, Jugador jugador3) {
        this.nombre = nombre;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.oponentesDerrotados = 0;
        this.totalCombos = 0;
    }

    /**
     * Verifica la cantidad de jugadores activos y vuelve la cantidad.
     * @return El número de jugadores con al menos una carta con poder.
     */
    public int jugadoresActivos() {
        int activos = 0;
        if (jugador1.estaActivo()) activos++;
        if (jugador2.estaActivo()) activos++;
        if (jugador3.estaActivo()) activos++;
        return activos;
    }

    /**
     * Devuelve verdadero si hay jugadores activos.
     * @return true si hay al menos un jugador activo.
     */
    public boolean tieneJugadoresActivos() {
        return jugadoresActivos() > 0;
    }

    /**
     * Aumenta el contador de oponentes derrotados en 1.
     * @param num El número del jugador.
     * @return El jugador correspondiente, o null si el número es inválido.
     */
    public Jugador getJugador(int num) {
        switch (num) {
            case 1: return jugador1;
            case 2: return jugador2;
            case 3: return jugador3;
            default: return null;
        }
    }

    /**
     * Aumenta el contador de oponentes derrotados en 1.
     */
    public void incrementarOponentesDerrotados() { oponentesDerrotados++; }

    /**
     * Incrementa el total de combos realizados.
     */
    public void incrementarCombos() { totalCombos++; }

    /**
     * Aumenta el contador de combos en 1.
     * @return El nombre del equipo.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el número de oponentes derrotados.
     * @return Los oponentes derrotados.
     */
    public int getOponentesDerrotados() { return oponentesDerrotados; }

    /**
     * Obtiene el total de combos realizados.
     * @return El total de combos.
     */
    public int getTotalCombos() { return totalCombos; }

    /**
     * Obtiene el jugador 1.
     * @return El jugador 1.
     */
    public Jugador getJugador1() { return jugador1; }

    /**
     * Obtiene el jugador 2.
     * @return El jugador 2.
     */
    public Jugador getJugador2() { return jugador2; }

    /**
     * Obtiene el jugador 3.
     * @return El jugador 3.
     */
    public Jugador getJugador3() { return jugador3; }
}
