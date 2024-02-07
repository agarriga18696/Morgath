package personajes;

import objetos.ListaObjetos;

public class ListaPNJS {
	
	private static StringBuilder nombre = new StringBuilder();
	private static StringBuilder conversacion = new StringBuilder();
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
				ListaObjetos.ESPADA,
				ListaObjetos.LAMPARA,
				ListaObjetos.BOLSA);
	}


}
