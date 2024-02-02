package configuracion;

import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.border.EmptyBorder;

public class Config {
	
	public static final String CURSOR = "> ";
	public static int anchoVentana = 800;
	public static int altoVentana = 600;
	public static Border borde = new EmptyBorder(10, 10, 10, 10);
	public static Color colorPrincipal = new Color(0, 0, 0);
	public static Color colorSecundario = new Color(180, 180, 180);
	public static Font fuente = new Font("Helvetica", Font.PLAIN, 18);
	
	public static Font fuente2;
	public static final float tamanoFuente = 25;
	
	static {
		try {
			File archivo = new File("C:\\Users\\Andreu\\git\\morgath\\Morgath\\resources\\fonts\\clacon2.ttf");
			fuente2 = Font.createFont(Font.TRUETYPE_FONT, archivo).deriveFont(tamanoFuente);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
