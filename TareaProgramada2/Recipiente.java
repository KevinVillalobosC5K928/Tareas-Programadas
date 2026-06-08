import java.util.Stack;
/**
 * Modela un recipiente del puzzle como una pila de tipos de piezas.
 * El índice 0 de la pila representa el fondo del recipiente.
 * Solo se puede acceder a la pieza en la cima (la más alta).
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Recipiente
{
    /** Pila de tipos de piezas (enteros simbólicos). */
    private Stack<Integer> piezas;

    /** Capacidad máxima del recipiente. */
    private int capacidad;

    /**
     * Construye un recipiente vacío con la capacidad indicada.
     *
     * @param capacidad número máximo de piezas que puede contener
     */
    public Recipiente(int capacidad) {
        this.capacidad = capacidad;
        this.piezas = new Stack<Integer>();
    }

    /**
     * Construye un recipiente copiando la pila de otro recipiente.
     *
     * @param otro recipiente del cual se copia el estado
     */
    public Recipiente(Recipiente otro) {
        this.capacidad = otro.capacidad;
        this.piezas = new Stack<Integer>();
        this.piezas.addAll(otro.piezas);
    }

    /**
     * Agrega una pieza en la cima del recipiente si hay espacio.
     *
     * @param tipo tipo simbólico de la pieza a agregar
     * @return true si se pudo agregar, false si está lleno
     */
    public boolean apilar(int tipo) {
        if (piezas.size() < capacidad) {
            piezas.push(tipo);
            return true;
        }
        return false;
    }

    /**
     * Retira y retorna la pieza de la cima del recipiente.
     *
     * @return tipo simbólico de la pieza retirada, o -1 si está vacío
     */
    public int desapilar() {
        if (!piezas.isEmpty()) {
            return piezas.pop();
        }
        return -1;
    }

    /**
     * Retorna el tipo de la pieza en la cima sin retirarla.
     *
     * @return tipo simbólico de la pieza en la cima, o -1 si vacío
     */
    public int cima() {
        if (!piezas.isEmpty()) {
            return piezas.peek();
        }
        return -1;
    }

    /**
     * Indica si el recipiente está vacío.
     *
     * @return true si no contiene piezas
     */
    public boolean estaVacio() {
        return piezas.isEmpty();
    }

    /**
     * Indica si el recipiente está lleno.
     *
     * @return true si alcanzó su capacidad máxima
     */
    public boolean estaLleno() {
        return piezas.size() == capacidad;
    }

    /**
     * Indica si el recipiente está resuelto: vacío o con todas las piezas
     * del mismo tipo y lleno hasta su capacidad.
     *
     * @return true si el recipiente está en estado de solución
     */
    public boolean estaSolucionado() {
        if (piezas.isEmpty()) return true;
        int primero = piezas.get(0);
        for (int t : piezas) {
            if (t != primero) return false;
        }
        return piezas.size() == capacidad;
    }

    /**
     * Retorna la cantidad actual de piezas en el recipiente.
     *
     * @return número de piezas apiladas
     */
    public int cantidadPiezas() {
        return piezas.size();
    }

    /**
     * Retorna la capacidad máxima del recipiente.
     *
     * @return capacidad del recipiente
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Retorna el tipo de pieza en la posición indicada (0 = fondo).
     *
     * @param indice posición dentro de la pila
     * @return tipo simbólico en esa posición, o 0 si no hay pieza
     */
    public int getPiezaEn(int indice) {
        if (indice < piezas.size()) {
            return piezas.get(indice);
        }
        return 0;
    }

    /**
     * Retorna una representación en texto del recipiente de arriba hacia abajo.
     *
     * @return cadena con las piezas del recipiente
     */
    @Override
    public String toString() {
        return piezas.toString();
    }
}