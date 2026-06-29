/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Panel encargado de dibujar el plano cartesiano con las emociones opuestas.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;


public class PlanoEmocionesPanel extends JPanel 
{
    /** Resultado del análisis a representar. */
    private Resultado resultado;

    /**
     * Construye el panel con un resultado dado.
     * @param resultado resultado a mostrar
     */
    public PlanoEmocionesPanel(Resultado resultado) {
        this.resultado = resultado;
        setBackground(Color.WHITE);
    }

    /**
     * Dibuja el plano de emociones y el punto de la frase.
     * @param g objeto gráfico suministrado por Swing
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int ancho = getWidth();
        int alto = getHeight();
        int centroX = ancho / 2;
        int centroY = alto / 2;
        int margen = 70;

        g.setColor(Color.BLACK);
        g.drawLine(margen, centroY, ancho - margen, centroY);
        g.drawLine(centroX, margen, centroX, alto - margen);

        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Calmado", centroX - 35, margen - 15);
        g.drawString("Enojado", centroX - 35, alto - margen + 30);
        g.drawString("Triste", margen - 45, centroY - 10);
        g.drawString("Feliz", ancho - margen + 10, centroY - 10);

        int radioX = (ancho - 2 * margen) / 2;
        int radioY = (alto - 2 * margen) / 2;
        int puntoX = centroX + (int)(resultado.getX() * radioX);
        int puntoY = centroY - (int)(resultado.getY() * radioY);

        g.setColor(Color.RED);
        g.fillOval(puntoX - 5, puntoY - 5, 10, 10);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.drawString(String.format("Feliz: %.4f", resultado.getFeliz()), 20, 20);
        g.drawString(String.format("Triste: %.4f", resultado.getTriste()), 20, 40);
        g.drawString(String.format("Calmado: %.4f", resultado.getCalmado()), 20, 60);
        g.drawString(String.format("Enojado: %.4f", resultado.getEnojado()), 20, 80);
        
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g.drawString("Cercanía a cada emoción:", 20, 120);
    
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString(obtenerDescripcion("Feliz",   resultado.getFeliz()),   30, 145);
        g.drawString(obtenerDescripcion("Triste",  resultado.getTriste()),  30, 165);
        g.drawString(obtenerDescripcion("Calmado", resultado.getCalmado()), 30, 185);
        g.drawString(obtenerDescripcion("Enojado", resultado.getEnojado()), 30, 205);
    }
    /**
     * Genera una descripción textual de cercanía según el valor de similitud.
     * @param emocion nombre de la emoción
     * @param similitud valor de similitud entre 0 y 1
     * @return descripción con barra visual y etiqueta
     */
    private String obtenerDescripcion(String emocion, double similitud) {
        String nivel;
        if (similitud >= 0.6) {
            nivel = "Muy cercano";
        } else if (similitud >= 0.35) {
            nivel = "Cercano";
        } else if (similitud >= 0.1) {
            nivel = "Poco cercano";
        } else {
            nivel = "Muy lejano";
        }
        return String.format("%-10s %.4f  →  %s", emocion + ":", similitud, nivel);
}
}
