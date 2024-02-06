package localizaciones;

import objetos.ListaObjetos;
import objetos.Objeto;
import personajes.Enemigo;
import personajes.ListaEnemigos;
import personajes.ListaPNJS;
import personajes.PNJ;

public class Habitaciones {
	
	/*
	 * 
	 * HABITACIÓN 0, CALLEJÓN SIN SALIDA
	 * 
	 */
	
	public static Habitacion h_00() {
		String nombre = "Callejón sin salida";
		String desc = "Estás en mitad de un sendero sin salida, al este hay un sendero que parece ser la única ruta de escape.";
		
		return new Habitacion(0, nombre, desc, 
				new Objeto[]{
						ListaObjetos.ESPADA, 
						ListaObjetos.BOLSA,
						ListaObjetos.MONEDA,
						ListaObjetos.LAMPARA,
						ListaObjetos.MOCHILA}, 
				
				new PNJ[]{
						ListaPNJS.COMERCIANTE}, 
				
				new Enemigo[]{ListaEnemigos.LADRON});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 1, SENDERO
	 * 
	 */
	
	public static Habitacion h_01() {
		String nombre = "Sendero";
		String desc = "El sendero es muy estrecho, parece desviarse al norte hacia una vieja casa de campo. ¿Estará abandonada? Tal vez sea un buen sitio para empezar a buscar.";

		return new Habitacion(1, nombre, desc, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2, CASA
	 * 
	 */
	
	public static Habitacion h_02() {
		String nombre = "Casa, Exterior";
		String desc = "Te encuentras frente a la casa, las ventanas están cerradas desde el interior. No parece haber nadie cera.";
		
		return new Habitacion(2, nombre, desc, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}

	
	/*
	 * 
	 * HABITACIÓN 2_01, CASA: ENTRADA
	 * 
	 */
	
	public static Habitacion h_02_01() {
		String nombre = "Casa, Entrada";
		String desc = "Estás dentro de la casa, en la entrada principal. La luz es tenue y el distintivo olor a armario antiguo "
					+ "impregna el aire. Al frente ves unas escaleras que parecen subir a un ático. "
					+ "Al fondo ves una entrada a un sótano.";

		return new Habitacion(2.1, nombre, desc, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_02, CASA: ÁTICO
	 * 
	 */
	
	public static Habitacion h_02_02() {
		String nombre = "Casa, Ático";
		String desc = "Hay un pequeño tragaluz que ilumina ligeramente el lugar. El ambiente es frío.";

		return new Habitacion(2.2, nombre, desc, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_03, CASA: SÓTANO
	 * 
	 */
	
	public static Habitacion h_02_03() {
		String nombre = "Casa, Sótano";
		String desc = "Aquí abajo no hay luz, y el aire se percibe pesado y seco. Es escalofriante.";

		return new Habitacion(2.3, nombre, desc, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}
	
}
