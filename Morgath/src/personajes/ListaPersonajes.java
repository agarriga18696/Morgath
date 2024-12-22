package personajes;

import objetos.ListaObjetos;
import personajes.Personaje.TipoPNJ;
import utilidades.Aleatorio;

public class ListaPersonajes {
	
	private static StringBuffer nombre = new StringBuffer();
	private static StringBuffer conversacion = new StringBuffer();
	private static int vidas = 1;

	/*
	 * 
	 * DEFINICIÓN DE LOS PNJ
	 * 
	 */

	//public static final Personaje ALDEANO = crearAldeano();
	//public static final Personaje COMERCIANTE = crearComerciante();


	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * PNJ
	 * 
	 * (nombre, conversación, tipo, vida, objetos)
	 * 
	 * 
	 * 
	 */

	// ALDEANO
	public static Personaje crearAldeano() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append(Aleatorio.Boolean() ? NombresPersonajes.getNombreMasculino() : NombresPersonajes.getNombreFemenino());
		conversacion.append("¿En qué puedo ayudarte?");
		vidas = 1;
		
		return new Personaje(nombre.toString(), conversacion.toString(), TipoPNJ.ALDEANO, vidas);
	}

	// COMERCIANTE
	public static Personaje crearComerciante() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append(Aleatorio.Boolean() ? NombresPersonajes.getNombreMasculino() : NombresPersonajes.getNombreFemenino());
		conversacion.append("¿Te interesa comerciar?");
		vidas = 2;
		
		return new Personaje(nombre.toString(), conversacion.toString(), TipoPNJ.COMERCIANTE, vidas,
				ListaObjetos.crearEspadaCorta(),
				ListaObjetos.crearLampara(),
				ListaObjetos.crearBolsa());
	}


}
