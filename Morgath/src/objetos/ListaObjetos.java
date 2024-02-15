package objetos;

import java.util.ArrayList;
import java.util.List;

import juego.Juego;
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
		listaTodosLosObjetos.add(crearMoneda());
		listaTodosLosObjetos.add(crearEspadaCorta());
		listaTodosLosObjetos.add(crearBolsa());
		listaTodosLosObjetos.add(crearMochila());
	}

	/*
	 * DEFINICIÓN DE LOS OBJETOS
	 */

	// OBJETOS COMUNES
	public static final Objeto LAMPARA = crearLampara();
	public static final Objeto MONEDA = crearMoneda();

	// ARMAS
	public static final Objeto ESPADA_JUGADOR = crearEspadaJugador();
	public static final Objeto ESPADA_CORTA = crearEspadaCorta();
	public static final Objeto ESPADA_LARGA = crearEspadaLarga();

	// CONTENEDORES
	public static final Objeto BOLSA = crearBolsa();
	public static final Objeto MOCHILA = crearMochila();

	// OBJETOS CON CERRADURA
	public static final Objeto COFRE = crearCofre();

	// LLAVES
	public static final Objeto LLAVE_VIEJA = crearLlaveVieja(); 

	/*
	 * CREACIÓN DE LOS OBJETOS
	 */

	//////////////////////////////
	//							//
	//			COMUNES			//
	//							//
	//////////////////////////////

	// Lámpara
	private static Objeto crearLampara() {
		return new Objeto_Comun("Lámpara", "Ilumina hasta el más oscuro pasadizo.", false, 0);
	}

	//////////////////////////////
	//							//
	//			DINERO			//
	//							//
	//////////////////////////////

	// Moneda
	private static Objeto crearMoneda() {
		return new Objeto_Dinero("Moneda", "Tallada a mano en forma hexagonal.");
	}

	//////////////////////////////
	//							//
	//			ARMAS			//
	//							//
	//////////////////////////////

	// Espada Jugador
	private static Objeto crearEspadaJugador() {
		return new Objeto_Arma("Espada " + Juego.nombreJugador, "Una ligera espada de hierro.", true, 0, 1);
	}

	// Espada Corta
	private static Objeto crearEspadaCorta() {
		return new Objeto_Arma("Espada corta", "Una afilada espada corta de acero.", false, 15, 3);
	}

	// Espada Larga
	private static Objeto crearEspadaLarga() {
		return new Objeto_Arma("Espada larga", "Una elegante espada larga de acero.", false, 30, 5);
	}

	//////////////////////////////
	//							//
	//	     CONTENEDORES		//
	//							//
	//////////////////////////////

	// Bolsa
	private static Objeto crearBolsa() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("Bolsa", "Una pequeña bolsa de piel de gato.", Capacidad.BAJA, objetosContenidos);
	}

	// Mochila
	private static Objeto crearMochila() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		return new Objeto_Contenedor("Mochila", "Una práctica mochila de cuero de vaca.", Capacidad.MEDIA, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// CONTENEDOR CON CERRADURA	//
	//							//
	//////////////////////////////

	// Cofre
	private static Objeto crearCofre() {
		List<Objeto> objetosContenidos = new ArrayList<>();
		objetosContenidos.add(LAMPARA);
		return new Objeto_Cerradura("Cofre", "Un polvoriento cofre de madera.", true, objetosContenidos);
	}

	//////////////////////////////
	//							//
	// 			LLAVES			//
	//							//
	//////////////////////////////

	// Llave vieja
	private static Objeto crearLlaveVieja() {
		return new Objeto_Llave("Llave", "Una vieja llave de hierro.", COFRE.getId());
	}

}
