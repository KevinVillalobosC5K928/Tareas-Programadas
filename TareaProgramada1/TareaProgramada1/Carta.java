import java.util.Random;

/**
 * Representa una carta de poder en el juego Dojo de Supervivencia.
 * Cada carta tiene un tipo, vida, poder de ataque y poder de defensa.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Carta {
    /** Tipo de la carta (AIRE, TIERRA o AGUA) */
    private Tipo tipo;
    /** Referencia al jugador propietario de la carta */
    private Jugador jugador;
    /** Puntos de vida de la carta (inicia en 1.0) */
    private double vida;
    /** Poder de ataque aleatorio entre 0.6 y 1.0 */
    private double ataque;
    /** Poder de defensa aleatorio entre 0.1 y 0.5 */
    private double defensa;

    /**
     * Constructor de la carta. Inicializa vida en 1.0, ataque aleatorio [0.6,1.0]
     * y defensa aleatoria [0.1,0.5].
     * @param tipo El tipo de la carta.
     * @param jugador El jugador propietario.
     */
    public Carta(Tipo tipo, Jugador jugador) {
        Random rand = new Random();
        this.tipo = tipo;
        this.jugador = jugador;
        this.vida = 1.0;
        this.ataque = 0.6 + rand.nextDouble() * 0.4; // [0.6, 1.0]
        this.defensa = 0.1 + rand.nextDouble() * 0.4; // [0.1, 0.5]
    }

    /**
     * Devuelve verdadero si la carta afecta al objetivo según su tipo.
     * AIRE afecta TIERRA, TIERRA afecta AGUA, AGUA afecta AIRE. Iguales se afectan mutuamente.
     * @param objetivo La carta objetivo.
     * @return true si esta carta afecta a la carta objetivo.
     */
    public boolean afectaA(Carta objetivo) {
        if (this.tipo == objetivo.tipo) return true;
        if (this.tipo == Tipo.AIRE && objetivo.tipo == Tipo.TIERRA) return true;
        if (this.tipo == Tipo.TIERRA && objetivo.tipo == Tipo.AGUA) return true;
        if (this.tipo == Tipo.AGUA && objetivo.tipo == Tipo.AIRE) return true;
        return false;
    }

    /**
     * Aplica la operacion para restar vida.
     * La vida de la afectada se reduce: vida -= vida * (ataque_atacante - defensa_afectada).
     * @param objetivo La carta que recibe el daño.
     */
    public void atacar(Carta objetivo) {
        if (this.vida <= 0) return; // carta sin poder no afecta
        if (this.afectaA(objetivo)) {
            double nuevaVida = objetivo.vida - objetivo.vida * (this.ataque - objetivo.defensa);
            objetivo.vida = nuevaVida;
        }
    }

    /**
     * Devuelve verdadero si la vida es mayor a 0.
     * @return true si la carta tiene vida mayor a cero.
     */
    public boolean tienePoder() {
        return vida > 0;
    }

    /**
     * Obtiene el tipo de la carta.
     * @return El tipo de la carta.
     */
    public Tipo getTipo() { return tipo; }

    /**
     * Obtiene la vida actual de la carta.
     * @return La vida de la carta.
     */
    public double getVida() { return vida; }

    /**
     * Obtiene el poder de ataque de la carta.
     * @return El poder de ataque.
     */
    public double getAtaque() { return ataque; }

    /**
     * Obtiene el poder de defensa de la carta.
     * @return El poder de defensa.
     */
    public double getDefensa() { return defensa; }

    /**
     * Obtiene el jugador propietario de la carta.
     * @return El jugador propietario.
     */
    public Jugador getJugador() { return jugador; }

    /**
     * Establece el jugador propietario de esta carta.
     * @param jugador El jugador propietario.
     */
    public void setJugador(Jugador jugador) { this.jugador = jugador; }
}
