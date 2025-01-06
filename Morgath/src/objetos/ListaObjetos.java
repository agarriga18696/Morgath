package objetos;

import java.util.ArrayList;
import java.util.List;

import objetos.Objeto.Rareza;
import objetos.Objeto_Contenedor.*;

public class ListaObjetos {

	private static final List<Objeto> listaTodosLosObjetos = new ArrayList<>();

	// Constructor, inicializa la lista de objetos.
	public ListaObjetos() {
		inicializarListaObjetos();
	}

	// Método para inicializar la lista de objetos.
	private static void inicializarListaObjetos() {
		listaTodosLosObjetos.clear();
		listaTodosLosObjetos.add(crearLampara());
		listaTodosLosObjetos.add(crearEspadaCorta());
		listaTodosLosObjetos.add(crearBolsa());
		listaTodosLosObjetos.add(crearMochila());
		listaTodosLosObjetos.add(crearLlave());
	}

	/*
	 * DEFINICIÓN DE LOS OBJETOS
	 */

	// OBJETOS COMUNES
	/*public static final Objeto LAMPARA = crearLampara();
	public static final Objeto MONEDA = crearMoneda();

	// ARMAS
	public static final Objeto ESPADA_ROTA = crearEspadaRota();
	public static final Objeto ESPADA_CORTA = crearEspadaCorta();
	public static final Objeto ESPADA_LARGA = crearEspadaLarga();

	// CONTENEDORES
	public static final Objeto BOLSA = crearBolsa();
	public static final Objeto MOCHILA = crearMochila();

	// OBJETOS CON CERRADURA
	public static final Objeto COFRE = crearCofre();

	// LLAVES
	public static final Objeto LLAVE = crearLlave(); */

	/*
	 * CREACIÓN DE LOS OBJETOS
	 */

	//////////////////////////////
	//							//
	//			COMUNES			//
	//							//
	//////////////////////////////
	
	/*
	 * 
	 * Atributos:
	 * 
	 * - icono
	 * - nombre
	 * - descripción
	 * - rareza
	 * - objeto de misión
	 * - valor de venta
	 * 
	 * 
	 */

	// Lámpara
	public static Objeto crearLampara() {
		return new Objeto_Comun("%icon[old-lantern]", "Lámpara", "Ilumina hasta el más oscuro pasadizo.", Rareza.COMUN, false, 0);
	}

	//////////////////////////////
	//							//
	//			JOYAS			//
	//							//
	//////////////////////////////




	//////////////////////////////
	//							//
	//			ARMAS			//
	//							//
	//////////////////////////////
	
	/*
	 * 
	 * Atributos:
	 * 
	 * - icono
	 * - nombre
	 * - descripción
	 * - rareza
	 * - objeto de misión
	 * - valor de venta
	 * - daño
	 * 
	 * 
	 */

	// Espada Rota
	public static Objeto crearEspadaRota() {
		return new Objeto_Arma("%icon[shattered-sword]", "Espada rota", "Restos de una espada con la hoja fragmentada, inútil en combate.", Rareza.UNICO, false, 0, 1);
	}

	// Espada Corta
	public static Objeto crearEspadaCorta() {
		return new Objeto_Arma("%icon[sword]", "Espada corta", "Una ligera espada corta de acero.", null, true, 15, 3);
	}

	// Espada Larga
	public static Objeto crearEspadaLarga() {
		return new Objeto_Arma("%icon[longsword]", "Espada larga", "Una elegante espada larga de acero templado.", null, false, 30, 5);
	}

	//////////////////////////////
	//							//
	//	     CONTENEDORES		//
	//							//
	//////////////////////////////

	/*
	 * 
	 * Atributos:
	 * 
	 * - icono
	 * - nombre
	 * - descripción
	 * - rareza
	 * - objeto de misión
	 * - valor de venta
	 * - capacidad
	 * - lista de objetos contenidos
	 * 
	 * 
	 */
	
	// Bolsa
	public static Objeto crearBolsa() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("%icon[swap-bag]", "Bolsa", "Una pequeña bolsa de piel de gato.", Rareza.COMUN, false, 30, Capacidad.BAJA, objetosContenidos);
	}

	// Mochila
	public static Objeto crearMochila() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("%icon[knapsack]", "Mochila", "Una práctica mochila de cuero de vaca.", null, false, 70, Capacidad.MEDIA, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// CONTENEDOR CON CERRADURA	//
	//							//
	//////////////////////////////

	/*
	 * 
	 * Atributos:
	 * 
	 * - icono
	 * - nombre
	 * - descripción
	 * - rareza
	 * - objeto de misión
	 * - valor de venta
	 * - cerrado (t/f)
	 * - lista de objetos contenidos
	 * 
	 * 
	 */
	
	// Cofre
	public static Objeto crearCofre() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		objetosContenidos.add(crearLampara());
		objetosContenidos.add(crearBolsa());
		return new Objeto_Cerradura("%icon[locked-chest]", "Cofre", "Un polvoriento cofre de madera.", null, false, null, true, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// 			LLAVES			//
	//							//
	//////////////////////////////

	/*
	 * 
	 * Atributos:
	 * 
	 * - icono
	 * - nombre
	 * - descripción
	 * - rareza
	 * - objeto de misión
	 * - valor de venta
	 * - llave maestra (t/f)
	 * 
	 * 
	 */
	
	// Llave común
	public static Objeto crearLlave() {
		return new Objeto_Llave("%icon[key]", "Llave", "Una vieja llave oxidada. Me pregunto qué abrirá...", Rareza.COMUN, false, 100, false);
	}

	// Llave maestra
	public static Objeto crearLlaveMaestra() {
		return new Objeto_Llave("%icon[star-key]", "Llave maestra", "Una llave para abrir todo tipo de cerraduras.", Rareza.SUPREMO, false, 5000, true);
	}

}
