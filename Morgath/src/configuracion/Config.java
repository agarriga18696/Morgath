package configuracion;

import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.border.EmptyBorder;

public class Config {
	
	public static final String CURSOR = ">";
	public static int anchoVentana = 1024;
	public static int altoVentana = 768;
	public static Border borde = new EmptyBorder(20, 20, 20, 20);
	public static Color colorPrincipal = new Color(0, 0, 0);
	public static Color colorSecundario = new Color(180, 180, 180);
	public static float tamanoFuente = 25;
	public static Font fuente;

    static {
    	
        try {
            File archivo = new File("resources/fonts/Flexi_IBM_VGA_True.ttf");
            fuente = Font.createFont(Font.TRUETYPE_FONT, archivo).deriveFont(tamanoFuente);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción (por ejemplo, proporcionar una fuente predeterminada)
            fuente = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
        }
        
    }

}
