/**
 * Representa un jugador del equipo en el juego Dojo de Supervivencia.
 * Cada jugador tiene un nombre y tres cartas (una de cada tipo).
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Jugador {
    /** Nombre del jugador */
    private String nombre;
    /** Carta de tipo AIRE del jugador */
    private Carta cartaAire;
    /** Carta de tipo TIERRA del jugador */
    private Carta cartaTierra;
    /** Carta de tipo AGUA del jugador */
    private Carta cartaAgua;

    /**
     * Constructor del jugador. Crea las tres cartas (AIRE, TIERRA, AGUA) y las asigna.
     * @param nombre El nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.cartaAire = new Carta(Tipo.AIRE, this);
        this.cartaTierra = new Carta(Tipo.TIERRA, this);
        this.cartaAgua = new Carta(Tipo.AGUA, this);
    }

    /**
     * Devuelve verdadero si tiene al menos una carta con poder.
     * @return true si el jugador tiene al menos una carta con poder.
     */
    public boolean estaActivo() {
        return cartaAire.tienePoder() || cartaTierra.tienePoder() || cartaAgua.tienePoder();
    }

    /**
     * Devuelve la carta por el índice.
     * @param indice El índice de la carta (1, 2 o 3).
     * @return La carta correspondiente, o null si el índice es inválido.
     */
    public Carta getCarta(int indice) {
        switch (indice) {
            case 1: return cartaAire;
            case 2: return cartaTierra;
            case 3: return cartaAgua;
            default: return null;
        }
    }

    /**
     * Obtiene el nombre del jugador.
     * @return El nombre del jugador.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la carta de tipo AIRE del jugador.
     * @return La carta AIRE.
     */
    public Carta getCartaAire() { return cartaAire; }

    /**
     * Obtiene la carta de tipo TIERRA del jugador.
     * @return La carta TIERRA.
     */
    public Carta getCartaTierra() { return cartaTierra; }

    /**
     * Obtiene la carta de tipo AGUA del jugador.
     * @return La carta AGUA.
     */
    public Carta getCartaAgua() { return cartaAgua; }
}
