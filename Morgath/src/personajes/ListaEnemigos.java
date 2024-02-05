package personajes;


public class ListaEnemigos {

	/*
	 * 
	 * DEFINICIÓN DE LOS ENEMIGOS
	 * 
	 */

	public static Enemigo LADRON = crearLadron();
	public static Enemigo MUTANTE = crearMutante();


	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * ENEMIGOS
	 * 
	 * (nombre, esJefe, recompensaPorMatar, vida)
	 * 
	 * 
	 * 
	 */

	// LADRON
	public static Enemigo crearLadron() {
		return new Enemigo("Ladrón", false, 5, 2);
	}

	// MUTANTE
	public static Enemigo crearMutante() {
		return new Enemigo("Mutante", false, 8, 3);
	}


}
