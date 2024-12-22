package configuracion;

import javax.swing.border.Border;
import java.awt.Color;

import javax.swing.border.EmptyBorder;

public class Config {
	
	// SISTEMA
	public static final String CURSOR = ">";

	// RESOLUCION VENTANA
	public static int anchoVentana = 1280; //1280
	public static int altoVentana = 800; //800
	
	// ICONOS
	public static final int TAMANO_ICONO = 34;

	// PADDING
	private static final int PADDING = 50;
	public static Border borde = new EmptyBorder(PADDING, PADDING, PADDING, PADDING);

	// TEMAS
	public static final Tema TEMA_1 = new Tema(Color.decode("#282b33"), Color.decode("#cfd1d8"), Color.decode("#d3ad4a"));
	public static final Tema TEMA_2 = new Tema(Color.decode("#eeecf0"), Color.decode("#1a2043"), Color.decode("#e42262"));
	public static final Tema TEMA_3 = new Tema(Color.decode("#112544"), Color.decode("#edecf4"), Color.decode("#23acc9"));
	public static final Tema TEMA_4 = new Tema(Color.decode("#3b3733"), Color.decode("#dbd4ca"), Color.decode("#fecaa7"));
	public static final Tema TEMA_5 = new Tema(Color.decode("#172c28"), Color.decode("#e1d8d5"), Color.decode("#a2a489"));
	public static final Tema TEMA_6 = new Tema(Color.decode("#300d05"), Color.decode("#ffbfc9"), Color.decode("#d80859"));
	public static final Tema TEMA_7 = new Tema(Color.decode("#ffffff"), Color.decode("#00b5cb"), Color.decode("#00b5cb"));
	public static final Tema TEMA_8 = new Tema(Color.decode("#0e0909"), Color.decode("#fffeff"), Color.decode("#b7a98b"));
	public static final Tema TEMA_9 = new Tema(Color.decode("#000000"), Color.decode("#ffffff"), Color.decode("#ffffff"));
	public static Tema temaActual = TEMA_1;
	
	// COLORES
	// Variables estáticas finales para los colores, basadas en el tema actual
	public static final Color COLOR_PNJ = getColorPnj();
	public static final Color COLOR_ENEMIGO = getColorEnemigo();
	public static final Color COLOR_COMANDO = getColorComando();

	// Métodos auxiliares para cambiar a monocromo si se usa el TEMA CLÁSICO.
	public static Color getColorPnj() {
	    return temaActual == TEMA_8 ? Color.decode("#ffffff") : Color.decode("#2d9fe0");
	}

	private static Color getColorEnemigo() {
	    return temaActual == TEMA_8 ? Color.decode("#ffffff") : Color.decode("#d62f35");
	}

	private static Color getColorComando() {
	    return temaActual == TEMA_8 ? Color.decode("#ffffff") : Color.decode("#b27059");
	}
	
}
