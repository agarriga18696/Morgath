package configuracion;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class Fuente {

	// Atributos Fuente
	public static final float INTERLINEADO = 0.27f;
	public static final float TAMANO_FUENTE = 34;

	// Fuentes
	public static Font fuenteBase;
	public static Font fuenteSimbolos;

	static {
		try {
			// Cargar y derivar la fuente principal.
			fuenteBase = cargarFuente("resources/fonts/Flexi_IBM_VGA_True.ttf", TAMANO_FUENTE);

			// Cargar y registrar la fuente de símbolos.
			Font fuenteBaseSimbolos = cargarFuente("resources/fonts/Gnomish-Regular.otf", 0);
			registrarFuente(fuenteBaseSimbolos);

			// Crear fuente de símbolos ajustada al tamaño.
			fuenteSimbolos = fuenteBaseSimbolos.deriveFont(TAMANO_FUENTE);

		} catch(Exception e) {
			e.printStackTrace();

			// Fuentes de respaldo en caso de error.
			fuenteBase = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
			fuenteSimbolos = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
		}
	}

	// Cargar una fuente desde un archivo y derivarla al tamaño especificado.
	private static Font cargarFuente(String rutaArchivo, float tamano) throws Exception {
		File archivoFuente = new File(rutaArchivo);
		Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, archivoFuente);
		return tamano > 0 ? fuenteBase.deriveFont(tamano) : fuenteBase;
	}

	// Registrar una fuente en el entorno gráfico del sistema.
	private static void registrarFuente(Font fuente) throws Exception {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(fuente);
	}
}
