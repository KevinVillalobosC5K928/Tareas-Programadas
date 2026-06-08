import java.util.ArrayList;
/**
 * Representa un estado del puzzle en un momento dado.
 * Contiene el arreglo de recipientes, el movimiento que generó este estado
 * y una referencia al estado padre para reconstruir el camino de solución.
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Estado
{
    /** Arreglo de recipientes que conforman este estado. */
    private Recipiente[] recipientes;

    /** Descripción textual del movimiento que generó este estado. */
    private String movimiento;

    /** Estado anterior en el camino de solución (null si es el inicial). */
    private Estado padre;

    /**
     * Construye el estado inicial a partir de un arreglo de recipientes.
     *
     * @param recipientes arreglo de recipientes del estado inicial
     */
    public Estado(Recipiente[] recipientes) {
        this.recipientes = recipientes;
        this.movimiento = "Estado inicial reconocido";
        this.padre = null;
    }

    /**
     * Construye un estado derivado de un movimiento sobre un estado padre.
     *
     * @param recipientes arreglo de recipientes del nuevo estado
     * @param movimiento  descripción del movimiento realizado
     * @param padre       estado del cual se derivó este
     */
    public Estado(Recipiente[] recipientes, String movimiento, Estado padre) {
        this.recipientes = recipientes;
        this.movimiento = movimiento;
        this.padre = padre;
    }

    /**
     * Indica si el estado actual es una solución: todos los recipientes
     * están solucionados (vacíos o con un solo tipo de pieza y llenos).
     *
     * @return true si el puzzle está resuelto
     */
    public boolean esSolucion() {
        for (Recipiente r : recipientes) {
            if (!r.estaSolucionado()) return false;
        }
        return true;
    }

    /**
     * Genera todos los estados válidos alcanzables desde este estado
     * aplicando un movimiento legal.
     *
     * @return lista de estados hijos válidos
     */
    public ArrayList<Estado> generarHijos() {
        ArrayList<Estado> hijos = new ArrayList<Estado>();
        int n = recipientes.length;

        for (int origen = 0; origen < n; origen++) {
            if (recipientes[origen].estaVacio()) continue;

            int tipoCima = recipientes[origen].cima();

            for (int destino = 0; destino < n; destino++) {
                if (origen == destino) continue;
                if (recipientes[destino].estaLleno()) continue;

                boolean destinoVacio = recipientes[destino].estaVacio();
                boolean mismoTipo = !destinoVacio && recipientes[destino].cima() == tipoCima;

                if (destinoVacio || mismoTipo) {
                    // Verificar que el movimiento sea útil (no mover a vacío si origen ya está uniforme)
                    if (destinoVacio && esUniforme(recipientes[origen])) continue;

                    Recipiente[] nuevos = copiarRecipientes(recipientes);
                    int pieza = nuevos[origen].desapilar();
                    nuevos[destino].apilar(pieza);

                    String mov = "Mover del recipiente " + (origen + 1) + " al " + (destino + 1);
                    hijos.add(new Estado(nuevos, mov, this));
                }
            }
        }
        return hijos;
    }

    /**
     * Indica si un recipiente ya tiene todas sus piezas del mismo tipo.
     *
     * @param r recipiente a verificar
     * @return true si todas las piezas son del mismo tipo
     */
    private boolean esUniforme(Recipiente r) {
        if (r.estaVacio()) return true;
        int base = r.getPiezaEn(0);
        for (int i = 1; i < r.cantidadPiezas(); i++) {
            if (r.getPiezaEn(i) != base) return false;
        }
        return true;
    }

    /**
     * Crea una copia profunda del arreglo de recipientes.
     *
     * @param original arreglo de recipientes a copiar
     * @return nuevo arreglo con copias independientes de cada recipiente
     */
    private Recipiente[] copiarRecipientes(Recipiente[] original) {
        Recipiente[] copia = new Recipiente[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = new Recipiente(original[i]);
        }
        return copia;
    }

    /**
     * Genera una clave única en texto que representa este estado,
     * usada para detectar estados ya visitados en el BFS.
     *
     * @return cadena única que identifica la configuración actual
     */
    public String getClave() {
        StringBuilder sb = new StringBuilder();
        for (Recipiente r : recipientes) {
            sb.append("|");
            for (int i = 0; i < r.cantidadPiezas(); i++) {
                sb.append(r.getPiezaEn(i)).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Retorna el arreglo de recipientes del estado.
     *
     * @return arreglo de recipientes
     */
    public Recipiente[] getRecipientes() { return recipientes; }

    /**
     * Retorna la descripción del movimiento que generó este estado.
     *
     * @return texto del movimiento
     */
    public String getMovimiento() { return movimiento; }

    /**
     * Retorna el estado padre de este estado.
     *
     * @return estado padre, o null si es el estado inicial
     */
    public Estado getPadre() { return padre; }
}