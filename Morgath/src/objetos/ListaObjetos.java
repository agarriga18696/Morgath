package objetos;

import java.util.ArrayList;
import java.util.List;

import objetos.Objeto_Contenedor.*;

public class ListaObjetos {

	private static StringBuilder nombre = new StringBuilder();
	private static StringBuilder descripcion = new StringBuilder();
	private static int valorVenta = 0;
	private static boolean esObjetoMision = false;
	private static int ataque = 1;
	private static Capacidad capacidad = Capacidad.BAJA;
	private static List<Objeto> objetosContenidos = new ArrayList<>();

	public static List<Objeto> listaTodosLosObjetos = new ArrayList<>();

	
	public ListaObjetos() {
		inicializarListaObjetos();
	}
	
	
	/*
	 * 
	 * DEFINICIÓN DE LOS OBJETOS
	 * 
	 */

	// OBJETOS COMUNES
	public static final Objeto LAMPARA = crearLampara();

	// DINERO
	public static final Objeto MONEDA = crearMoneda();

	// ARMAS
	public static final Objeto ESPADA = crearEspada();

	// CONTENEDORES
	public static final Objeto BOLSA = crearBolsa();
	public static final Objeto MOCHILA = crearMochila();

	// Añadir todos los objetos creados a la lista.
	public static void inicializarListaObjetos() {
		listaTodosLosObjetos.clear();
		listaTodosLosObjetos.add(LAMPARA);
		listaTodosLosObjetos.add(MONEDA);
		listaTodosLosObjetos.add(ESPADA);
		listaTodosLosObjetos.add(BOLSA);
		listaTodosLosObjetos.add(MOCHILA);
	}


	/*
	 * 
	 * CREACIÓN DE LOS OBJETOS
	 * 
	 * 
	 * 
	 */

	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * OBJETOS COMUNES
	 * 
	 * (nombre, descripción, valorVenta, esObjetoMision)
	 * 
	 * 
	 */

	// LÁMPARA
	public static Objeto crearLampara() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Lámpara");
		descripcion.append("Ilumina hasta el más oscuro pasadizo.");
		valorVenta = 0;
		esObjetoMision = true;

		return new Objeto_Comun(nombre.toString(), descripcion.toString(), esObjetoMision, valorVenta);
	}


	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * DINERO
	 * 
	 * (nombre, descripción)
	 * 
	 * 
	 */

	// MONEDA
	public static Objeto crearMoneda() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Moneda");
		descripcion.append("Reluciente y tallada a mano en forma hexagonal");

		return new Objeto_Dinero(nombre.toString(), descripcion.toString());
	}



	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * ARMAS
	 * 
	 * (nombre, descripción, valorVenta, esObjetoMision, ataque)
	 * 
	 * 
	 */

	// ESPADA
	public static Objeto crearEspada() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Espada");
		descripcion.append("Una afilada espada de hierro.");
		valorVenta = 0;
		esObjetoMision = true;
		ataque = 2;

		return new Objeto_Arma(nombre.toString(), descripcion.toString(), esObjetoMision, valorVenta, ataque);
	}

	// ESPADA LARGA
	public static Objeto crearEspadaLarga() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Espada Larga");
		descripcion.append("Una elegante espada larga de acero.");
		valorVenta = 25;
		esObjetoMision = true;
		ataque = 3;

		return new Objeto_Arma(nombre.toString(), descripcion.toString(), esObjetoMision, valorVenta, ataque);
	}



	/* ----------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 * 
	 * CONTENEDORES DE OBJETOS
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * 
	 * CAPACIDAD BAJA
	 * 
	 */

	// BOLSA
	public static Objeto crearBolsa() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Bolsa");
		descripcion.append("Una pequeña bolsa de piel de gato.");
		capacidad = Capacidad.BAJA;
		//objetosContenidos.add(MONEDA);

		return new Objeto_Contenedor(nombre.toString(), descripcion.toString(), capacidad, objetosContenidos);
	}


	/*
	 * 
	 * CAPACIDAD MEDIA
	 * 
	 */


	/*
	 * 
	 * CAPACIDAD ALTA
	 * 
	 */

	// MOCHILA
	public static Objeto crearMochila() {
		nombre.setLength(0);
		descripcion.setLength(0);
		nombre.append("Mochila");
		descripcion.append("Una práctica mochila de cuero de vaca.");
		capacidad = Capacidad.ALTA;
		//objetosContenidos.add(MONEDA);

		return new Objeto_Contenedor(nombre.toString(), descripcion.toString(), capacidad, objetosContenidos);
	}

}
