package configuracion;

import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.border.EmptyBorder;

public class Config {

	// CURSOR
	public static final String CURSOR = ">";

	// RESOLUCION VENTANA
	public static int anchoVentana = 1024;
	public static int altoVentana = 768;

	// PADDING
	public static Border borde = new EmptyBorder(20, 20, 20, 20);

	// FUENTE
	public static float tamanoFuente = 25;
	public static Font fuente;
	
	static {
		try {
			File archivo = new File("resources/fonts/Flexi_IBM_VGA_True.ttf");
			fuente = Font.createFont(Font.TRUETYPE_FONT, archivo).deriveFont(tamanoFuente); 
		} catch (Exception e) {
			e.printStackTrace();
			fuente = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
		}
	}

	// TEMAS
	public static final Tema TEMA_1 = new Tema(new Color(9, 3, 4), new Color(190, 190, 190), new Color(241, 195, 80));
	public static final Tema TEMA_2 = new Tema(new Color(240, 240, 240), new Color(36, 41, 57), new Color(83, 68, 228));
	public static final Tema TEMA_3 = new Tema(new Color(13, 43, 53), new Color(247, 251, 255), new Color(109, 227, 192));
	public static final Tema TEMA_4 = new Tema(new Color(54, 44, 37), new Color(240, 224, 152), new Color(122, 224, 249));
	public static final Tema TEMA_5 = new Tema(new Color(0, 0, 0), new Color(104, 225, 27), new Color(104, 225, 27));
	public static final Tema TEMA_6 = new Tema(new Color(0, 0, 0), new Color(255, 255, 255), new Color(255, 255, 255));
	public static final Tema TEMA_7 = new Tema(new Color(1, 1, 2), new Color(255, 245, 34), new Color(174, 161, 48));
	public static final Tema TEMA_8 = new Tema(new Color(87, 49, 151), new Color(248, 252, 252), new Color(254, 204, 141));
	public static Tema temaActual = TEMA_1;
}
