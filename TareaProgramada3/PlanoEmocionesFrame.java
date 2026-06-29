/**
 * Tarea programada 3
 * @author Kevin Villalobos
 * Ventana principal para mostrar el plano de emociones.
 */
import javax.swing.JFrame;


public class PlanoEmocionesFrame extends JFrame 
{
    /** Panel que dibuja el plano y el punto. */
    private PlanoEmocionesPanel panel;

    /**
     * Construye la ventana del plano.
     * @param resultado resultado a dibujar
     */
    public PlanoEmocionesFrame(Resultado resultado) {
        setTitle("Plano de emociones");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        panel = new PlanoEmocionesPanel(resultado);
        add(panel);
    }
}
