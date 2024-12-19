package localizaciones;

import objetos.ListaObjetos;
import objetos.Objeto;
import personajes.Enemigo;
import personajes.ListaEnemigos;
import personajes.ListaPNJS;
import personajes.PNJ;
import localizaciones.Habitacion.*;

public class Habitaciones {
	
	/*
	 * 
	 * HABITACIÓN 0, CALLEJÓN SIN SALIDA
	 * 
	 */
	
	public static Habitacion h_00() {
		String nombre = "Callejón sin salida";
		String desc = "Estás en un sendero sin salida. Al este hay un sendero que parece ser la única ruta de escape.";
		Tipo tipo = Tipo.EXTERIOR;
		
		return new Habitacion(0, nombre, desc, tipo,
				new Objeto[]{
						ListaObjetos.LAMPARA,
						ListaObjetos.LLAVE_VIEJA,
						ListaObjetos.COFRE
				},
				new PNJ[]{
						ListaPNJS.ALDEANO,
						ListaPNJS.COMERCIANTE
				}, 
				new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 1, SENDERO
	 * 
	 */
	
	public static Habitacion h_01() {
		String nombre = "Sendero";
		String desc = "El sendero es muy estrecho, parece desviarse al norte hacia una vieja casa de campo. ¿Estará abandonada? Tal vez sea un buen sitio para empezar a buscar.";
		Tipo tipo = Tipo.EXTERIOR;
		
		return new Habitacion(1, nombre, desc, tipo, 
				new Objeto[]{}, 
				new PNJ[]{}, 
				new Enemigo[]{
						ListaEnemigos.LADRON,
						ListaEnemigos.MUTANTE
				});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2, CASA: EXTERIOR
	 * 
	 */
	
	public static Habitacion h_02() {
		String nombre = "Sur de la casa";
		String desc = "Te encuentras frente a la casa, las ventanas están cerradas desde el interior. No parece haber nadie cera.";
		Tipo tipo = Tipo.EXTERIOR;
		
		return new Habitacion(2, nombre, desc, tipo, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}

	
	/*
	 * 
	 * HABITACIÓN 2_01, CASA: ENTRADA
	 * 
	 */
	
	public static Habitacion h_02_01() {
		String nombre = "Casa";
		String desc = "Estás dentro de la casa, en la entrada principal. La luz es tenue y el distintivo olor a armario antiguo "
					+ "impregna el aire. Al frente ves unas escaleras que parecen subir a un ático. "
					+ "Al fondo ves una entrada a un sótano.";
		Tipo tipo = Tipo.INTERIOR;

		return new Habitacion(2.1, nombre, desc, tipo, new Objeto[]{}, new PNJ[]{}, new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_02, CASA: ÁTICO
	 * 
	 */
	
	public static Habitacion h_02_02() {
		String nombre = "Ático";
		String desc = "Hay un pequeño tragaluz que ilumina ligeramente el lugar. El ambiente es frío.";
		Tipo tipo = Tipo.SUPERIOR;

		return new Habitacion(2.2, nombre, desc, tipo, new Objeto[]{
				ListaObjetos.BOLSA}, 
				new PNJ[]{}, 
				new Enemigo[]{});
	}
	
	
	/*
	 * 
	 * HABITACIÓN 2_03, CASA: SÓTANO
	 * 
	 */
	
	public static Habitacion h_02_03() {
		String nombre = "Sótano";
		String desc = "Aquí abajo no hay luz, y el aire se percibe pesado y seco. Es escalofriante.";
		Tipo tipo = Tipo.INFERIOR;

		return new Habitacion(2.3, nombre, desc, tipo, new Objeto[]{
				ListaObjetos.ESPADA_CORTA}, 
				new PNJ[]{}, 
				new Enemigo[]{});
	}
	
}
