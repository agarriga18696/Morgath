package personajes;


public class ListaEnemigos {
	
	private static StringBuilder nombre = new StringBuilder();
	private static StringBuilder conversacion = new StringBuilder();
	private static boolean esJefe = false;
	private static int recompensa = 0;
	private static int vidas = 1;

	/*
	 * 
	 * DEFINICIÓN DE LOS ENEMIGOS
	 * 
	 */

	// Enemigos normales.
	public static final Enemigo LADRON = crearLadron();
	public static final Enemigo MUTANTE = crearMutante();


	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * ENEMIGOS
	 * 
	 * (nombre, vidas, conversacion, esJefe, recompensaPorMatar)
	 * 
	 * 
	 * 
	 */

	// LADRON
	public static Enemigo crearLadron() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append("Ladrón");
		conversacion.append("¡Dame todo lo que lleves encima o morirás!");
		vidas = 2;
		esJefe = false;
		recompensa = 5;
		
		return new Enemigo(nombre.toString(), vidas, conversacion.toString(), esJefe, recompensa);
	}

	// MUTANTE
	public static Enemigo crearMutante() {
		nombre.setLength(0);
		conversacion.setLength(0);
		nombre.append("Mutante");
		conversacion.append("(Sonidos irreconocibles)");
		vidas = 3;
		esJefe = false;
		recompensa = 8;
		
		return new Enemigo(nombre.toString(), vidas, conversacion.toString(), esJefe, recompensa);
	}


}
