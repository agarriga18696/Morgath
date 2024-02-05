package objetos;

import objetos.Objeto_Contenedor.*;

public class ListaObjetos {

	/*
	 * 
	 * DEFINICIÓN DE LOS OBJETOS
	 * 
	 */

	public static Objeto ESPADA = crearEspada();
	public static Objeto LAMPARA = crearLampara();
	public static Objeto BOLSA = crearBolsaMonedas();


	/*
	 * 
	 * CREACIÓN DE LOS OBJETOS
	 * 
	 * (nombre, descripción, valorVenta, esObjetoMision)
	 * 
	 */

	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * OBJETOS COMUNES
	 * 
	 * 
	 * 
	 * 
	 */

	// LÁMPARA
	public static Objeto crearLampara() {
		return new Objeto_Comun("Lámpara", "Ilumina hasta el más oscuro pasadizo.", 0, true);
	}

	// MONEDA
	public static Objeto crearMoneda() {
		return new Objeto_Dinero("Moneda", "Moneda de plata tallada a mano en forma hexagonal.");
	}



	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * ARMAS
	 * 
	 * 
	 * 
	 * 
	 */

	// ESPADA
	public static Objeto crearEspada() {
		return new Objeto_Arma("Espada", "Una afilada espada de hierro que inflige 2 puntos de daño.", 0, true, 2);
	}

	// ESPADA LARGA
	public static Objeto crearEspadaLarga() {
		return new Objeto_Arma("Espada Larga", "Una elegante espada larga de acero que causa 3 puntos de daño.", 5, false, 3);
	}

	

	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * CONTENEDORES DE OBJETOS
	 * 
	 * 
	 * 
	 * 
	 */

	// BOLSA DE MONEDAS
	public static Objeto crearBolsaMonedas() {
		return new Objeto_Contenedor("Bolsa", "Una práctica bolsa de piel de gato.", Capacidad.BAJA);
	}

}
