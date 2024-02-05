package localizaciones;

public class Habitaciones {
	
	/*
	 * 
	 * HABITACIÓN 0, CALLEJÓN SIN SALIDA
	 * 
	 */
	
	public static String h_00(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Callejón sin salida";
		case "desc":
			return "Estás en mitad de un sendero sin salida, al este hay un sendero que parece ser la única ruta de escape.";
		default:
			return "NaN";
		}
	}
	
	
	/*
	 * 
	 * HABITACIÓN 1, SENDERO
	 * 
	 */
	
	public static String h_01(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Sendero";
		case "desc":
			return "El sendero es muy estrecho, parece desviarse al norte hacia una vieja casa de campo. ¿Estará abandonada? Tal vez sea un buen sitio para empezar a buscar.";
		default:
			return "NaN";
		}
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2, CASA
	 * 
	 */
	
	public static String h_02(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Casa, Exterior";
		case "desc":
			return "Te encuentras frente a la casa, las ventanas están cerradas desde el interior. No parece haber nadie cera.";
		default:
			return "NaN";
		}
	}

	
	/*
	 * 
	 * HABITACIÓN 2_01, CASA: ENTRADA
	 * 
	 */
	
	public static String h_02_01(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Casa, Entrada";
		case "desc":
			return "Estás dentro de la casa, en la entrada principal. La luz es tenue y el distintivo olor a armario antiguo "
					+ "impregna el aire. Al frente ves unas escaleras que parecen subir a un ático. "
					+ "Al fondo ves una entrada a un sótano.";
		default:
			return "NaN";
		}
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_02, CASA: ÁTICO
	 * 
	 */
	
	public static String h_02_02(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Casa, Ático";
		case "desc":
			return "Hay un pequeño tragaluz que ilumina ligeramente el lugar. El ambiente es frío.";
		default:
			return "NaN";
		}
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_03, CASA: SÓTANO
	 * 
	 */
	
	public static String h_02_03(String solicitud) {
		switch(solicitud.toLowerCase().trim()) {
		case "nombre":
			return "Casa, Sótano";
		case "desc":
			return "Aquí abajo no hay luz, y el aire se percibe pesado y seco. Es escalofriante.";
		default:
			return "NaN";
		}
	}
	
}
