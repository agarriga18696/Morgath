package objetos;

public class FabricaObjetos {

	// Método para obtener un objeto específico por nombre.
	public static Objeto obtenerObjetoPorNombre(String nombre) {
		switch (nombre.trim().toUpperCase()) {
		case "ESPADA":
			return crearEspada();
		case "BOLSA":
			return crearBolsaMonedas();
		case "LÁMPARA":
			return crearLampara();
		default:
			return null;
		}
	}

	/* ----------------------------------------------------------------------------------------------------------
	 * OBJETOS NORMALES
	 * 
	 */

	// ESPADA
	public static Objeto crearEspada() {
		return new Objeto("Espada", "Una afilada espada de acero.");
	}

	// LÁMPARA
	public static Objeto crearLampara() {
		return new Objeto("Lámpara", "Ilumina hasta el más oscuro pasadizo.");
	}

	// LÁMPARA
	public static Objeto crearMoneda() {
		return new Objeto("Moneda", "Moneda de plata tallada a mano en forma hexagonal.");
	}

	/* ----------------------------------------------------------------------------------------------------------
	 * CONTENEDORES
	 */

	// BOLSA DE MONEDAS
	public static Contenedor crearBolsaMonedas() {
		return new Contenedor("Bolsa", "Una bolsa de cuero de vaca que contiene 30 monedas de plata.");
	}

}
