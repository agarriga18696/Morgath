package objetos;

public class FabricaObjetos {

	// ESPADA
	public static Objeto crearEspada() {
		return new Objeto("Espada", "Una afilada espada de acero.", false);
	}

	//
	public static Objeto crearLampara() {
		return new Objeto("Lámpara", "Ilumina hasta el más oscuro averno.", false);
	}

	// Método para obtener un objeto específico por nombre.
	public static Objeto obtenerObjetoPorNombre(String nombre) {
		switch (nombre.trim().toUpperCase()) {
		case "ESPADA":
			return crearEspada();
		case "POCIÓN":
			return crearLampara();
		default:
			return null;
		}
	}

}
