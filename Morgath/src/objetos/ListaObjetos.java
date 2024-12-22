package objetos;

import java.util.ArrayList;
import java.util.List;

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

	// Lámpara
	public static Objeto crearLampara() {
		return new Objeto_Comun("%icon[lantern-flame]", "Lámpara", "Ilumina hasta el más oscuro pasadizo.", false, 0);
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

	// Espada Jugador
	public static Objeto crearEspadaRota() {
		return new Objeto_Arma("%icon[shattered-sword]", "Espada rota", "Una espada rota que le falta el filo.", true, 0, 1);
	}

	// Espada Corta
	public static Objeto crearEspadaCorta() {
		return new Objeto_Arma("%icon[sword]", "Espada corta", "Una ligera espada corta de acero.", false, 15, 3);
	}

	// Espada Larga
	public static Objeto crearEspadaLarga() {
		return new Objeto_Arma("%icon[longsword]", "Espada larga", "Una elegante espada larga de acero templado.", false, 30, 5);
	}

	//////////////////////////////
	//							//
	//	     CONTENEDORES		//
	//							//
	//////////////////////////////

	// Bolsa
	public static Objeto crearBolsa() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("%icon[swap-bag]", "Bolsa", "Una pequeña bolsa de piel de gato.", Capacidad.BAJA, objetosContenidos);
	}

	// Mochila
	public static Objeto crearMochila() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("%icon[knapsack]", "Mochila", "Una práctica mochila de cuero de vaca.", Capacidad.MEDIA, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// CONTENEDOR CON CERRADURA	//
	//							//
	//////////////////////////////

	// Cofre
	public static Objeto crearCofre() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		objetosContenidos.add(crearLampara());
		objetosContenidos.add(crearBolsa());
		return new Objeto_Cerradura("%icon[locked-chest]", "Cofre", "Un polvoriento cofre de madera.", true, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// 			LLAVES			//
	//							//
	//////////////////////////////

	// Llave vieja
	public static Objeto crearLlave() {
		return new Objeto_Llave("%icon[key]", "Llave", "Una vieja llave oxidada. Me pregunto qué abrirá...");
	}

}
