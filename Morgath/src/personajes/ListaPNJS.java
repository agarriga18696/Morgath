package personajes;

import objetos.ListaObjetos;

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
	 * (nombre, conversación, vida, objetos)
	 * 
	 * 
	 * 
	 */

	// ALDEANO
	public static PNJ crearAldeano() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append("Eydran");
		conversacion.append("Mi nombre es " + nombre + ". ¿En qué puedo ayudarte?");
		vidas = 1;
		
		return new PNJ(nombre.toString(), conversacion.toString(), vidas);
	}

	// COMERCIANTE
	public static PNJ crearComerciante() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append("Comerciante");
		conversacion.append("");
		vidas = 2;
		
		return new PNJ(nombre.toString(), conversacion.toString(), vidas,
				ListaObjetos.ESPADA_CORTA,
				ListaObjetos.LAMPARA,
				ListaObjetos.BOLSA);
	}


}
