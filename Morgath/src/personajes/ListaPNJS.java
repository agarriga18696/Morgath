package personajes;

import objetos.ListaObjetos;

public class ListaPNJS {

	/*
	 * 
	 * DEFINICIÓN DE LOS PNJ
	 * 
	 */

	public static PNJ ALDEANO = crearAldeano();
	public static PNJ COMERCIANTE = crearComerciante();


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
		return new PNJ("Aldeano", "", 2);
	}

	// COMERCIANTE
	public static PNJ crearComerciante() {
		return new PNJ("Comerciante", "", 2, ListaObjetos.ESPADA, ListaObjetos.BOLSA, ListaObjetos.LAMPARA);
	}


}
