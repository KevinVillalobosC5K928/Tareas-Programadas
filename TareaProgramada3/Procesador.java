import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 *  * Tarea programada 3
 * @author Kevin Villalobos
 * Clase utilitaria para leer archivos de texto y normalizar palabras.
 * Convierte a minúsculas, elimina tildes, descarta caracteres no alfabéticos
 * y filtra palabras vacías del español (stopwords).
 */
public class Procesador {

    /** Lista de palabras vacías del español que no aportan mucho, hasta entorpecen el analisis. */
    private static final String[] STOPWORDS = {
        "de", "la", "el", "en", "y", "a", "los", "las", "un", "una",
        "que", "con", "por", "para", "es", "se", "del", "al", "lo",
        "le", "su", "si", "no", "me", "te", "mi", "tu", "mas", "pero",
        "como", "cuando", "hay", "ser", "fue", "han", "ya", "era",
        "o", "e", "u", "ni", "muy", "bien", "tan", "esto", "esta",
        "este", "eso", "ese", "esa", "ellos", "ellas", "ella", "el",
        "nosotros", "vosotros", "ustedes", "yo", "tu", "usted",
        "son", "somos", "soy", "eres", "esta", "estar", "tiene",
        "tienen", "tengo", "hacia", "hasta", "sobre", "entre",
        "todo", "todos", "toda", "todas", "otro", "otros",
        "más", "menos", "cada", "mismo", "sin", "dos", "tres"
    };

    /**
     * Lee el contenido completo de un archivo de texto.
     * @param ruta ruta del archivo
     * @return contenido del archivo como String
     * @throws IOException si ocurre un error de lectura
     */
    public static String leerArchivo(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea).append(" ");
        }
        br.close();
        return sb.toString();
    }

    /**
     * Convierte un texto en una lista de palabras limpias sin stopwords.
     * @param texto texto original
     * @return lista de tokens válidos
     */
    public static List<String> tokenizar(String texto) {
        List<String> tokens = new ArrayList<String>();
        if (texto == null) {
            return tokens;
        }

        texto = Normalizer.normalize(texto.toLowerCase(), Normalizer.Form.NFD);
        texto = texto.replaceAll("\\p{M}", "");
        texto = texto.replaceAll("[^a-zn\\s]", " ");
        texto = texto.replaceAll("\\s+", " ").trim();

        if (texto.isEmpty()) {
            return tokens;
        }

        String[] partes = texto.split(" ");
        for (int i = 0; i < partes.length; i++) {
            String palabra = partes[i];
            if (!palabra.isEmpty() && !esStopword(palabra)) {
                tokens.add(palabra);
            }
        }
        return tokens;
    }

    /**
     * Verifica si una palabra es una stopword.
     * @param palabra palabra a verificar
     * @return true si es stopword, false si no
     */
    private static boolean esStopword(String palabra) {
        for (int i = 0; i < STOPWORDS.length; i++) {
            if (STOPWORDS[i].equals(palabra)) {
                return true;
            }
        }
        return false;
    }
}