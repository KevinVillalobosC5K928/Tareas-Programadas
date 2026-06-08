import java.util.ArrayList;
/**
 * Se encarga de toda la salida en consola del programa.
 * Imprime la matriz simbólica de cada estado, la instrucción del movimiento
 * realizado y la leyenda de tipos de piezas con sus colores.
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Visualizar
{

    /**
     * Imprime en consola todo el proceso de solución paso a paso,
     * mostrando la matriz del estado y el movimiento realizado en cada paso.
     *
     * @param camino   lista ordenada de estados desde inicio hasta solución
     * @param leyenda  arreglo de Pieza con la información de cada tipo
     */
    public void mostrarSolucion(ArrayList<Estado> camino, Pieza[] leyenda) {
        System.out.println("=== ESTADO INICIAL EN FORMA SIMBÓLICA ===");
        mostrarLeyenda(leyenda);
        System.out.println();

        for (int i = 0; i < camino.size(); i++) {
            Estado e = camino.get(i);
            System.out.println(e.getMovimiento());
            imprimirMatriz(e.getRecipientes());
            System.out.println();
        }

        System.out.println("=== SOLUCIÓN ENCONTRADA EN " + (camino.size() - 1) + " MOVIMIENTOS ===");
    }

    /**
     * Imprime un mensaje indicando que el puzzle no tiene solución.
     */
    public void mostrarSinSolucion() {
        System.out.println("=== EL PROBLEMA NO TIENE SOLUCIÓN ===");
    }

    /**
     * Imprime la matriz simbólica de un arreglo de recipientes.
     * Las filas representan niveles de arriba hacia abajo,
     * las columnas representan recipientes.
     *
     * @param recipientes arreglo de recipientes a imprimir
     */
    public void imprimirMatriz(Recipiente[] recipientes) {
        int capacidad = 0;
        for (Recipiente r : recipientes) {
            if (r.getCapacidad() > capacidad) capacidad = r.getCapacidad();
        }

        for (int nivel = capacidad - 1; nivel >= 0; nivel--) {
            StringBuilder fila = new StringBuilder();
            for (int col = 0; col < recipientes.length; col++) {
                int tipo = recipientes[col].getPiezaEn(nivel);
                if (tipo == 0) {
                    fila.append(String.format("%4s", " "));
                } else {
                    fila.append(String.format("%4d", tipo));
                }
            }
            System.out.println(fila.toString());
        }

        // Separador visual inferior
        StringBuilder linea = new StringBuilder();
        for (int col = 0; col < recipientes.length; col++) {
            linea.append("----");
        }
        System.out.println(linea.toString());
    }

    /**
     * Imprime la leyenda de tipos de piezas con su color y forma asociados.
     *
     * @param leyenda arreglo de Pieza donde el índice+1 es el tipo simbólico
     */
    public void mostrarLeyenda(Pieza[] leyenda) {
        if (leyenda == null) return;
        System.out.print("Leyenda: ");
        for (int i = 0; i < leyenda.length; i++) {
            if (leyenda[i] != null) {
                System.out.print((i + 1) + "=" + leyenda[i].getNombreColor()
                    + "(" + leyenda[i].getForma() + ")  ");
            }
        }
        System.out.println();
    }
}