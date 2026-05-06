import java.util.Random;

/**
 * Representa un oponente en el juego Dojo de Supervivencia.
 * Cada oponente tiene tres cartas aleatorias y una intensidad de energía.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Oponente {
    /** Número identificador del oponente (1, 2 o 3) */
    private int numero;
    /** Intensidad del oponente que determina el consumo de energía por combo */
    private double intensidad;
    /** Carta 1 del oponente (tipo aleatorio) */
    private Carta carta1;
    /** Carta 2 del oponente (tipo aleatorio) */
    private Carta carta2;
    /** Carta 3 del oponente (tipo aleatorio) */
    private Carta carta3;

    /**
     * Constructor del oponente. Crea tres cartas con tipos aleatorios.
     * @param numero El número del oponente (1, 2 o 3).
     * @param intensidad La intensidad del oponente (0.2, 0.3 o 0.4).
     */
    public Oponente(int numero, double intensidad) {
        this.numero = numero;
        this.intensidad = intensidad;
        Random rand = new Random();
        Tipo[] tipos = Tipo.values();
        // Las cartas del oponente son aleatorias (pueden repetirse)
        this.carta1 = new Carta(tipos[rand.nextInt(3)], null);
        this.carta2 = new Carta(tipos[rand.nextInt(3)], null);
        this.carta3 = new Carta(tipos[rand.nextInt(3)], null);
    }

    /**
     * Verifica que las cartas tiene vida menor o igual a 0 si el oponente ha sido derrotado (las tres cartas sin poder).
     * @return true si las tres cartas del oponente tienen vida menor o igual a 0.
     */
    public boolean estaDerrotado() {
        return !carta1.tienePoder() && !carta2.tienePoder() && !carta3.tienePoder();
    }

    /**
     * Obtiene una carta del oponente por índice: 1, 2 o 3.
     * @param indice El índice de la carta (1, 2 o 3).
     * @return La carta correspondiente, o null si el índice es inválido.
     */
    public Carta getCarta(int indice) {
        switch (indice) {
            case 1: return carta1;
            case 2: return carta2;
            case 3: return carta3;
            default: return null;
        }
    }

    /**
     * Obtiene el número identificador del oponente.
     * @return El número del oponente.
     */
    public int getNumero() { return numero; }

    /**
     * Obtiene la intensidad actual del oponente.
     * @return La intensidad del oponente.
     */
    public double getIntensidad() { return intensidad; }

    /**
     * Reduce la intensidad del oponente a la mitad (cuando un jugador es derrotado).
     */
    public void reducirIntensidad() {
        this.intensidad = this.intensidad / 2.0;
    }
}
