import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Implementa el algoritmo BFS (Búsqueda en Anchura) para encontrar
 * la secuencia de movimientos mínima que resuelve el puzzle.
 * Si el puzzle no tiene solución, lo indica de forma textual.
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Solucionar
{

    /** Límite máximo de estados a explorar para evitar ciclos infinitos. */
    private static final int LIMITE = 200000;

    /**
     * Ejecuta BFS desde el estado inicial y retorna la lista ordenada
     * de estados desde el inicio hasta la solución.
     * Retorna null si no existe solución.
     *
     * @param inicial estado inicial del puzzle
     * @return lista de estados que forman el camino de solución, o null
     */
    public ArrayList<Estado> resolver(Estado inicial) {
        if (inicial.esSolucion()) {
            ArrayList<Estado> camino = new ArrayList<Estado>();
            camino.add(inicial);
            return camino;
        }

        Queue<Estado> cola = new LinkedList<Estado>();
        ArrayList<String> visitados = new ArrayList<String>();

        cola.add(inicial);
        visitados.add(inicial.getClave());

        int explorados = 0;

        while (!cola.isEmpty() && explorados < LIMITE) {
            Estado actual = cola.poll();
            explorados++;

            ArrayList<Estado> hijos = actual.generarHijos();

            for (Estado hijo : hijos) {
                String clave = hijo.getClave();

                if (!visitados.contains(clave)) {
                    if (hijo.esSolucion()) {
                        return reconstruirCamino(hijo);
                    }
                    visitados.add(clave);
                    cola.add(hijo);
                }
            }
        }

        return null; // Sin solución
    }

    /**
     * Reconstruye el camino de solución siguiendo los punteros padre
     * desde el estado final hasta el estado inicial.
     *
     * @param final_ estado solución desde el cual se reconstruye el camino
     * @return lista de estados en orden desde el inicio hasta la solución
     */
    private ArrayList<Estado> reconstruirCamino(Estado final_) {
        ArrayList<Estado> camino = new ArrayList<Estado>();
        Estado actual = final_;

        while (actual != null) {
            camino.add(0, actual);
            actual = actual.getPadre();
        }

        return camino;
    }
}