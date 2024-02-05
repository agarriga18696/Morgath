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
	public static int anchoVentana = 1280;
	public static int altoVentana = 800;

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
	public static final Tema TEMA_1 = new Tema(Color.decode("#262a34"), Color.decode("#ebefed"), Color.decode("#d34a55"));
	public static final Tema TEMA_2 = new Tema(Color.decode("#f9fcfc"), Color.decode("#192c53"), Color.decode("#5433fb"));
	public static final Tema TEMA_3 = new Tema(Color.decode("#112544"), Color.decode("#edecf4"), Color.decode("#23acc9"));
	public static final Tema TEMA_4 = new Tema(Color.decode("#383735"), Color.decode("#dbd4ca"), Color.decode("#fecaa7"));
	public static final Tema TEMA_5 = new Tema(Color.decode("#000000"), Color.decode("#21e379"), Color.decode("#21e379"));
	public static final Tema TEMA_6 = new Tema(Color.decode("#000000"), Color.decode("#ffffff"), Color.decode("#ffffff"));
	public static final Tema TEMA_7 = new Tema(Color.decode("#d0a407"), Color.decode("#46494a"), Color.decode("#322c13"));
	public static final Tema TEMA_8 = new Tema(Color.decode("#f6bce6"), Color.decode("#472a74"), Color.decode("#924eb7"));
	public static final Tema TEMA_9 = new Tema(Color.decode("#303232"), Color.decode("#d1d3d4"), Color.decode("#14e0af"));
	public static Tema temaActual = TEMA_1;
}
