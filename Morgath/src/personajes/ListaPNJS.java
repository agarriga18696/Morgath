package personajes;

import objetos.ListaObjetos;
import personajes.PNJ.TipoPNJ;
import utilidades.Aleatorio;

public class ListaPNJS {
	
	private static StringBuffer nombre = new StringBuffer();
	private static StringBuffer conversacion = new StringBuffer();
	private static int vidas = 1;

	/*
	 * 
	 * DEFINICIÓN DE LOS PNJ
	 * 
	 */

	public static final PNJ ALDEANO = crearAldeano();
	public static final PNJ COMERCIANTE = crearComerciante();


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
	private static PNJ crearAldeano() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append(Aleatorio.Boolean() ? NombresPersonajes.getNombreMasculino() : NombresPersonajes.getNombreFemenino());
		conversacion.append("¿En qué puedo ayudarte?");
		vidas = 1;
		
		return new PNJ(nombre.toString(), conversacion.toString(), TipoPNJ.ALDEANO, vidas);
	}

	// COMERCIANTE
	private static PNJ crearComerciante() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append(Aleatorio.Boolean() ? NombresPersonajes.getNombreMasculino() : NombresPersonajes.getNombreFemenino());
		conversacion.append("¿Te interesa comerciar?");
		vidas = 2;
		
		return new PNJ(nombre.toString(), conversacion.toString(), TipoPNJ.COMERCIANTE, vidas,
				ListaObjetos.ESPADA_CORTA,
				ListaObjetos.LAMPARA,
				ListaObjetos.BOLSA);
	}


}
