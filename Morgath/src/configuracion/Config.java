package configuracion;

import java.awt.Font;
import java.io.File;

public class Config {
	
	public static final String CURSOR = "> ";
	public static int anchoVentana = 800;
	public static int altoVentana = 600;
	
	public static Font fuente;
	public static final float tamanoFuente = 25;
	
	static {
		try {
			File archivo = new File("C:\\Users\\Andreu\\git\\morgath\\Morgath\\resources\\fonts\\clacon2.ttf");
			fuente = Font.createFont(Font.TRUETYPE_FONT, archivo).deriveFont(tamanoFuente);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
