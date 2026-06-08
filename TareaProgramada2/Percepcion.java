import java.util.ArrayList;
/**
 * Clase encargada de procesar la matriz de píxeles obtenida de una imagen
 * y convertirla en una representación simbólica del puzzle: un arreglo de
 * recipientes con sus piezas clasificadas por tipo.
 *
 * <p>El proceso sigue estos pasos:
 * <ol>
 *   <li>Detectar color de fondo (píxel en posición 0,0)</li>
 *   <li>Detectar regiones de borde usando flood fill</li>
 *   <li>Identificar el interior de cada recipiente</li>
 *   <li>Detectar y clasificar piezas dentro de cada recipiente</li>
 *   <li>Construir el arreglo de Recipiente con tipos simbólicos</li>
 * </ol>
 *
 * @author Kevin Villalobos Alfaro - C5K928
 * @version 1.0
 */
public class Percepcion
{
    /** Matriz de píxeles de la imagen original. */
    private int[][] imagen;

    /** Número de filas de la imagen. */
    private int filas;

    /** Número de columnas de la imagen. */
    private int cols;

    /** Color del fondo de la imagen (píxel 0,0). */
    private int colorFondo;

    /** Matriz de etiquetas: -1=fondo, 0=no procesado, N=ID de región. */
    private int[][] etiquetas;

    /** Lista de piezas detectadas y clasificadas. */
    private ArrayList<Pieza> tiposPiezas;

    /** Leyenda de tipos para el visualizador. */
    private Pieza[] leyenda;

    /**
     * Construye un objeto Percepcion con la matriz de píxeles de la imagen.
     *
     * @param imagen matriz de enteros RGB obtenida de Imagen.getMatriz()
     */
    public Percepcion(int[][] imagen) {
        this.imagen = imagen;
        this.filas = imagen.length;
        this.cols = imagen[0].length;
        this.colorFondo = imagen[0][0];
        this.etiquetas = new int[filas][cols];
        this.tiposPiezas = new ArrayList<Pieza>();
    }

    /**
     * Ejecuta el proceso completo de percepción y retorna el arreglo
     * de recipientes con sus piezas en orden simbólico.
     *
     * @return arreglo de Recipiente listo para el solucionador
     */
    public Recipiente[] procesar() {
        /** Paso 1: Detectar recipientes buscando regiones cerradas*/
        ArrayList<int[]> centrosRecipientes = detectarRecipientes();

        if (centrosRecipientes.isEmpty()) {
            System.out.println("No se detectaron recipientes en la imagen.");
            return new Recipiente[0];
        }

        /** Paso 2: Para cada recipiente, detectar sus piezas*/
        int capacidadMaxima = 0;
        ArrayList<ArrayList<int[]>> piezasPorRecipiente = new ArrayList<ArrayList<int[]>>();

        for (int[] centro : centrosRecipientes) {
            ArrayList<int[]> piezas = detectarPiezasEnRecipiente(centro[0], centro[1], centro[2], centro[3]);
            piezasPorRecipiente.add(piezas);
            if (piezas.size() > capacidadMaxima) capacidadMaxima = piezas.size();
        }

        /** Si la capacidad máxima es 0, usar un mínimo de 4*/
        if (capacidadMaxima == 0) capacidadMaxima = 4;

        /** Paso 3: Clasificar colores → tipos simbólicos*/
        int[][] tiposPorRecipiente = clasificarPiezas(piezasPorRecipiente);

        /** Paso 4: Construir recipientes*/
        Recipiente[] recipientes = new Recipiente[centrosRecipientes.size()];
        for (int i = 0; i < recipientes.length; i++) {
            recipientes[i] = new Recipiente(capacidadMaxima);
            // Apilar de abajo hacia arriba (el índice 0 de la lista es el fondo)
            for (int j = 0; j < tiposPorRecipiente[i].length; j++) {
                recipientes[i].apilar(tiposPorRecipiente[i][j]);
            }
        }

        // Construir leyenda
        construirLeyenda();

        return recipientes;
    }

    /**
     * Detecta los recipientes en la imagen buscando regiones de píxeles
     * que no son fondo y que encierran un área interior.
     * Retorna una lista de arrays [filaMin, colMin, filaMax, colMax] por recipiente.
     *
     * @return lista de bounding boxes de recipientes encontrados
     */
    private ArrayList<int[]> detectarRecipientes() {
        ArrayList<int[]> recipientes = new ArrayList<int[]>();
        boolean[][] visitado = new boolean[filas][cols];

        /** Buscar el color de borde (primer color != fondo)*/
        int colorBorde = buscarColorBorde();
        if (colorBorde == colorFondo) return recipientes;

        /** Encontrar componentes conectadas del color de borde*/
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < cols; c++) {
                if (!visitado[f][c] && imagen[f][c] == colorBorde) {
                    int[] bbox = floodFillBorde(f, c, colorBorde, visitado);
                    // Filtrar regiones muy pequeñas (ruido)
                    int alto = bbox[2] - bbox[0];
                    int ancho = bbox[3] - bbox[1];
                    if (alto > 3 && ancho > 3) {
                        recipientes.add(bbox);
                    }
                }
            }
        }

        /** Ordenar recipientes de izquierda a derecha por columna mínima*/
        for (int i = 0; i < recipientes.size() - 1; i++) {
            for (int j = i + 1; j < recipientes.size(); j++) {
                if (recipientes.get(j)[1] < recipientes.get(i)[1]) {
                    int[] tmp = recipientes.get(i);
                    recipientes.set(i, recipientes.get(j));
                    recipientes.set(j, tmp);
                }
            }
        }

        return recipientes;
    }

    /**
     * Busca el primer color en la imagen que no sea el color de fondo.
     * Este color se considera el color de borde de los recipientes.
     *
     * @return valor RGB del color de borde detectado
     */
    private int buscarColorBorde() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < cols; c++) {
                if (imagen[f][c] != colorFondo) {
                    return imagen[f][c];
                }
            }
        }
        return colorFondo;
    }

    /**
     * Realiza un flood fill desde una posición de borde y retorna
     * el bounding box (filaMin, colMin, filaMax, colMax) de la región.
     *
     * @param fInicio    fila de inicio del flood fill
     * @param cInicio    columna de inicio del flood fill
     * @param colorBorde color de los píxeles de borde
     * @param visitado   matriz de píxeles ya procesados
     * @return array [filaMin, colMin, filaMax, colMax]
     */
    private int[] floodFillBorde(int fInicio, int cInicio, int colorBorde, boolean[][] visitado) {
        int[] bbox = {fInicio, cInicio, fInicio, cInicio};
        ArrayList<int[]> cola = new ArrayList<int[]>();
        cola.add(new int[]{fInicio, cInicio});
        visitado[fInicio][cInicio] = true;

        int i = 0;
        while (i < cola.size()) {
            int[] pos = cola.get(i);
            i++;
            int f = pos[0], c = pos[1];

            if (f < bbox[0]) bbox[0] = f;
            if (c < bbox[1]) bbox[1] = c;
            if (f > bbox[2]) bbox[2] = f;
            if (c > bbox[3]) bbox[3] = c;

            // 4 direcciones (borde conectado en 4 direcciones)
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int nf = f + d[0], nc = c + d[1];
                if (nf >= 0 && nf < filas && nc >= 0 && nc < cols
                        && !visitado[nf][nc] && imagen[nf][nc] == colorBorde) {
                    visitado[nf][nc] = true;
                    cola.add(new int[]{nf, nc});
                }
            }
        }
        return bbox;
    }

    /**
     * Detecta las piezas dentro de un recipiente delimitado por su bounding box.
     * Las piezas son regiones de color distinto al fondo y al borde, ordenadas
     * de abajo hacia arriba (la primera en la lista es la del fondo).
     *
     * @param fMin fila mínima del recipiente
     * @param cMin columna mínima del recipiente
     * @param fMax fila máxima del recipiente
     * @param cMax columna máxima del recipiente
     * @return lista de arrays [colorPieza, tamanio, centroF, centroC, esCirculo]
     */
    private ArrayList<int[]> detectarPiezasEnRecipiente(int fMin, int cMin, int fMax, int cMax) {
        ArrayList<int[]> piezas = new ArrayList<int[]>();
        boolean[][] visitadoLocal = new boolean[filas][cols];

        /** Detectar el color de borde dentro de este recipiente*/
        int colorBorde = buscarColorBorde();

        /** Buscar regiones de color que no sean fondo ni borde dentro del bbox*/
        for (int f = fMin; f <= fMax; f++) {
            for (int c = cMin; c <= cMax; c++) {
                int px = imagen[f][c];
                if (!visitadoLocal[f][c] && px != colorFondo && px != colorBorde) {
                    // Posible pieza: hacer flood fill para medir
                    int[] info = floodFillPieza(f, c, px, visitadoLocal, fMin, cMin, fMax, cMax, colorBorde);
                    if (info[1] > 0) { // tamaño mínimo para ser preciso 
                        piezas.add(info);
                    }
                }
            }
        }

        /** Ordenar piezas de abajo hacia arriba (mayor fila = más abajo = fondo)*/
        for (int i = 0; i < piezas.size() - 1; i++) {
            for (int j = i + 1; j < piezas.size(); j++) {
                if (piezas.get(j)[2] > piezas.get(i)[2]) { // centroF mayor = más abajo
                    int[] tmp = piezas.get(i);
                    piezas.set(i, piezas.get(j));
                    piezas.set(j, tmp);
                }
            }
        }

        return piezas;
    }

    /**
     * Realiza flood fill sobre una pieza y retorna su información.
     *
     * @param fI          fila inicio
     * @param cI          columna inicio
     * @param color       color de la pieza
     * @param visitado    matriz de visitados
     * @param fMin        límite superior del recipiente
     * @param cMin        límite izquierdo del recipiente
     * @param fMax        límite inferior del recipiente
     * @param cMax        límite derecho del recipiente
     * @param colorBorde  color del borde a excluir
     * @return array [color, tamanio, centroF, centroC, esCirculo(1/0)]
     */
    private int[] floodFillPieza(int fI, int cI, int color, boolean[][] visitado,
                                  int fMin, int cMin, int fMax, int cMax, int colorBorde) {
        ArrayList<int[]> celdas = new ArrayList<int[]>();
        ArrayList<int[]> cola = new ArrayList<int[]>();
        cola.add(new int[]{fI, cI});
        visitado[fI][cI] = true;

        int idx = 0;
        int sumaF = 0, sumaC = 0;
        int bboxF0 = fI, bboxF1 = fI, bboxC0 = cI, bboxC1 = cI;

        while (idx < cola.size()) {
            int[] pos = cola.get(idx++);
            int f = pos[0], c = pos[1];
            celdas.add(pos);
            sumaF += f; sumaC += c;
            if (f < bboxF0) bboxF0 = f;
            if (f > bboxF1) bboxF1 = f;
            if (c < bboxC0) bboxC0 = c;
            if (c > bboxC1) bboxC1 = c;

            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
            for (int[] d : dirs) {
                int nf = f + d[0], nc = c + d[1];
                if (nf >= fMin && nf <= fMax && nc >= cMin && nc <= cMax
                        && !visitado[nf][nc] && imagen[nf][nc] == color) {
                    visitado[nf][nc] = true;
                    cola.add(new int[]{nf, nc});
                }
            }
        }

        int tamanio = celdas.size();
        int centroF = (tamanio > 0) ? sumaF / tamanio : fI;
        int centroC = (tamanio > 0) ? sumaC / tamanio : cI;

        /** Determinar forma: si el bounding box es aprox cuadrado → cuadrado, si no → círculo*/
        int altoBox = bboxF1 - bboxF0 + 1;
        int anchoBox = bboxC1 - bboxC0 + 1;
        double ratio = (anchoBox > 0) ? (double) altoBox / anchoBox : 1.0;
        int esCirculo = (ratio > 0.75 && ratio < 1.33 && tamanio < altoBox * anchoBox * 0.85) ? 1 : 0;

        return new int[]{color, tamanio, centroF, centroC, esCirculo};
    }

    /**
     * Clasifica las piezas detectadas en tipos simbólicos (enteros).
     * Dos piezas son del mismo tipo si tienen el mismo color, forma y tamaño similar.
     *
     * @param piezasPorRecipiente lista de piezas por recipiente
     * @return matriz de tipos simbólicos por recipiente
     */
    private int[][] clasificarPiezas(ArrayList<ArrayList<int[]>> piezasPorRecipiente) {
        ArrayList<int[]> prototipos = new ArrayList<int[]>(); // [color, esCirculo, tamanioRef]
        int[][] resultado = new int[piezasPorRecipiente.size()][];

        for (int i = 0; i < piezasPorRecipiente.size(); i++) {
            ArrayList<int[]> piezas = piezasPorRecipiente.get(i);
            resultado[i] = new int[piezas.size()];

            for (int j = 0; j < piezas.size(); j++) {
                int[] p = piezas.get(j);
                int color = p[0];
                int esCirculo = p[4];
                int tamanio = p[1];

                int tipo = buscarTipo(prototipos, color, esCirculo, tamanio);
                if (tipo == -1) {
                    tipo = prototipos.size() + 1;
                    prototipos.add(new int[]{color, esCirculo, tamanio});
                    // Agregar a lista de tipos conocidos
                    String forma = (esCirculo == 1) ? "circulo" : "cuadrado";
                    String nombreColor = obtenerNombreColor(color);
                    tiposPiezas.add(new Pieza(tipo, color, tamanio, forma, nombreColor));
                }
                resultado[i][j] = tipo;
            }
        }
        return resultado;
    }

    /**
     * Busca si ya existe un tipo simbólico para los atributos dados.
     *
     * @param prototipos lista de prototipos ya registrados [color, esCirculo, tamanio]
     * @param color      color de la pieza
     * @param esCirculo  1 si es círculo, 0 si es cuadrado
     * @param tamanio    tamaño en píxeles
     * @return tipo simbólico si ya existe, -1 si es nuevo
     */
    private int buscarTipo(ArrayList<int[]> prototipos, int color, int esCirculo, int tamanio) {
        for (int i = 0; i < prototipos.size(); i++) {
            int[] p = prototipos.get(i);
            if (p[0] == color && p[1] == esCirculo) {
                double diff = Math.abs(p[2] - tamanio) / (double) Math.max(p[2], tamanio);
                if (diff < 0.4) return i + 1;
            }
        }
        return -1;
    }

    /**
     * Obtiene un nombre de color aproximado a partir de un valor RGB.
     * Compara con colores básicos conocidos.
     *
     * @param rgb valor entero RGB del color
     * @return nombre del color más cercano en español
     */
    private String obtenerNombreColor(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8)  & 0xFF;
        int b =  rgb        & 0xFF;

        // Tabla de colores básicos
        String[][] tabla = {
            {"Rojo",      "255","0","0"},
            {"Verde",     "0","255","0"},
            {"Azul",      "0","0","255"},
            {"Amarillo",  "255","255","0"},
            {"Naranja",   "255","165","0"},
            {"Morado",    "128","0","128"},
            {"Rosa",      "255","192","203"},
            {"Cyan",      "0","255","255"},
            {"Blanco",    "255","255","255"},
            {"Negro",     "0","0","0"},
            {"Gris",      "128","128","128"},
            {"Marron",    "139","69","19"}
        };

        String mejor = "Color" + rgb;
        double menorDist = Double.MAX_VALUE;

        for (String[] entrada : tabla) {
            int tr = Integer.parseInt(entrada[1]);
            int tg = Integer.parseInt(entrada[2]);
            int tb = Integer.parseInt(entrada[3]);
            double dist = Math.sqrt(Math.pow(r - tr, 2) + Math.pow(g - tg, 2) + Math.pow(b - tb, 2));
            if (dist < menorDist) {
                menorDist = dist;
                mejor = entrada[0];
            }
        }
        return mejor;
    }

    /**
     * Construye la leyenda de tipos a partir de las piezas clasificadas.
     */
    private void construirLeyenda() {
        leyenda = new Pieza[tiposPiezas.size()];
        for (int i = 0; i < tiposPiezas.size(); i++) {
            leyenda[i] = tiposPiezas.get(i);
        }
    }

    /**
     * Retorna la leyenda de tipos de piezas detectados.
     *
     * @return arreglo de Pieza con la información de cada tipo simbólico
     */
    public Pieza[] getLeyenda() {
        return leyenda;
    }
}