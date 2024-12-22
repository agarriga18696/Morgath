package comandos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;

import configuracion.Config;
import configuracion.Fuente;
import configuracion.Tema;
import juego.Juego;
import localizaciones.Habitacion;
import localizaciones.Mapa;
import localizaciones.Habitacion.Tipo;
import localizaciones.Mapa.Direccion;
import misiones.Mision;
import misiones.Misiones;
import objetos.ListaObjetos;
import objetos.Objeto;
import objetos.Objeto_Arma;
import objetos.Objeto_Cerradura;
import objetos.Objeto_Comun;
import objetos.Objeto_Contenedor;
import objetos.Objeto_Dinero;
import objetos.Objeto_Llave;
import personajes.Jugador;
import personajes.ListaEnemigos;
import personajes.Personaje;
import personajes.Enemigo;
import utilidades.Aleatorio;
import utilidades.NormalizarCadena;

public class Comandos {

	// Atributos.
	private List<String> listaComandos = new ArrayList<>();
	private List<String> listaComandosActivados = new ArrayList<>();
	private Jugador jugador;
	private Juego juego;
	private Mapa mapa;
	private Direccion ultimaDireccionUsada;
	private Personaje ultimoPersonajeHablado;
	private String comandoNormalizado;

	private int contadorEspaciosVacios = 0;
	private boolean alternarMensajeComandoVolver = true;

	// Constructor.
	public Comandos(Jugador jugador, Juego juego, Mapa mapa) {
		this.jugador = jugador;
		this.juego = juego;
		this.mapa = mapa;
		this.ultimaDireccionUsada = null;
		this.ultimoPersonajeHablado = null;

		inicializarComandos();
	}

	// Método para obtener la lista de comandos.
	public List<String> getListaComandos() {
		return listaComandos;
	}

	// Método para obtener la lista de comandos activados.
	public List<String> getListaComandosActivados() {
		return listaComandosActivados;
	}

	// Lista de comandos.
	public enum ListaComandos {

		// MOVIMIENTO
		LEVANTARSE("LEVANTAR", "LEVANTA", "INCORPORARSE"),

		// Recoger todos los atajos de la lista enum 'Direccion' de la clase 'Mapa'.
		IR(combinarAtajos(Direccion.NORTE.getAtajo(), Direccion.SUR.getAtajo(), Direccion.ESTE.getAtajo(), 
				Direccion.OESTE.getAtajo(), Direccion.NORESTE.getAtajo(), Direccion.NOROESTE.getAtajo(), 
				Direccion.SUDESTE.getAtajo(), Direccion.SUDOESTE.getAtajo(), 
				Direccion.ARRIBA.getAtajo(), Direccion.ABAJO.getAtajo())),

		VOLVER("RETROCEDER"),
		ENTRAR,
		SALIR,

		// EXPLORACIÓN
		LUGAR("L", "SITIO", "ZONA"),
		EXPLORAR("EX"),
		MIRAR("OBSERVAR"),
		BUSCAR("B"),
		COGER("AGARRAR"),
		SOLTAR("TIRAR"),
		ABRIR,

		// JUGADOR
		ESTADO("SALUD"),

		// INVENTARIO
		ALMACENAR("DEPOSITAR", "INTRODUCIR", "INSERTAR", "METER"),
		SACAR("QUITAR", "EXTRAER", "SUSTRAER"),
		DESTRUIR("ROMPER"),
		INVENTARIO("I", "OBJETOS"),

		// MISIONES
		MISION("M", "OBJETIVO", "COMETIDO"),
		DIARIO("D", "MISIONES"),

		// SOCIAL
		HABLAR,
		RUMORES,
		INTIMIDAR,
		PERSUADIR,
		HALAGAR,
		SOBORNAR,
		COMERCIAR,
		TRASFONDO,
		ADIOS,

		// JUEGO
		TERMINAR,
		REINICIAR,
		GUARDAR("G", "SALVAR", "GRABAR", "PARTIDA"),
		AYUDA("A"),
		CREDITOS("CREADOR", "JUEGO"),
		TOC,
		VERSION,
		TEMA("COLOR");

		private final String[] atajo;

		// Constructor que maneja tanto los atajos directos como los obtenidos de Direccion
		ListaComandos(String... atajo) {
			this.atajo = atajo;
		}

		// Getter de atajos
		public String[] getAtajo() {
			return atajo;
		}

		// Método para obtener un comando a partir de un atajo
		public static ListaComandos obtenerAtajo(String atajo) {
			for(ListaComandos comando : values()) {
				if(comando.name().equalsIgnoreCase(atajo) || Arrays.asList(comando.atajo).contains(atajo.toUpperCase())) {
					return comando;
				}
			}
			return null;
		}

		// Método estático para combinar los atajos de las direcciones
		private static String[] combinarAtajos(String[]... atajos) {
			// Combina todos los arrays de atajos en uno solo
			return Arrays.stream(atajos)
					.flatMap(Arrays::stream)
					.toArray(String[]::new);
		}

	}

	// Método para inicializar los comandos.
	private void inicializarComandos() {
		// Añadir todos los comandos de la lista enum a la lista de comandos.
		for(ListaComandos comando : ListaComandos.values()){
			listaComandos.add(comando.name());
			listaComandos.addAll(Arrays.asList(comando.getAtajo()));
		}
		// Agregar comandos iniciales a la lista de comandos activados.
		setComandoActivado(ListaComandos.LEVANTARSE, true);
		setComandoActivado(ListaComandos.LUGAR, true);
		setComandoActivado(ListaComandos.MISION, true);
		setComandoActivado(ListaComandos.DIARIO, true);
		setComandoActivado(ListaComandos.TERMINAR, true);
		setComandoActivado(ListaComandos.GUARDAR, true);
		setComandoActivado(ListaComandos.AYUDA, true);
		setComandoActivado(ListaComandos.VERSION, true);
		setComandoActivado(ListaComandos.TEMA, true);
	}

	// Inicializar lista de comandos Activados.
	private void inicializarComandosActivados() {
		listaComandosActivados.clear();
		for(ListaComandos comando : ListaComandos.values()) {
			listaComandosActivados.add(comando.name());
			setComandoActivado(comando, true);
		}

		desactivarComandosSociales();
	}

	// Desactivar los comandos excepto...
	private void desactivarComandosExcepto(ListaComandos... comandos) {
		// Desactiva todos los comandos.
		for(ListaComandos comandoLista : ListaComandos.values()) {
			setComandoActivado(comandoLista, false);
		}

		// Activa solo los comandos especificados.
		for(ListaComandos comandoJugador : comandos) {
			setComandoActivado(comandoJugador, true);
		}
	}

	// Desactivar comandos sociales (excepto HABLAR).
	private void desactivarComandosSociales() {
		setComandoActivado(ListaComandos.RUMORES, false);
		setComandoActivado(ListaComandos.INTIMIDAR, false);
		setComandoActivado(ListaComandos.PERSUADIR, false);
		setComandoActivado(ListaComandos.HALAGAR, false);
		setComandoActivado(ListaComandos.SOBORNAR, false);
		setComandoActivado(ListaComandos.TRASFONDO, false);
		setComandoActivado(ListaComandos.COMERCIAR, false);
		setComandoActivado(ListaComandos.ADIOS, false);
	}

	// Setter para activar o desactivar un comando.
	public void setComandoActivado(ListaComandos comando, boolean activado) {
		String nombreComando = comando.name();

		if(activado) {
			if(!listaComandosActivados.contains(comando.name())){
				listaComandosActivados.add(nombreComando);
			}

		} else {
			listaComandosActivados.remove(nombreComando);
		}
	}

	// Verificar si un comando está activado.
	public boolean esComandoActivado(ListaComandos comando) {
		return listaComandosActivados.contains(comando.name());
	}


	/*
	 * 
	 *  
	 *  
	 *  
	 *  EJECUCCIÓN DE LOS COMANDOS
	 *  
	 *  
	 *  
	 *  
	 */

	public void ejecutarComando(String comando) {
		// Normalizar comando.
		comando = comando.trim().toUpperCase();
		comandoNormalizado = NormalizarCadena.quitarAcentos(comando).toUpperCase();

		// Verificar que el comando no esté vacío después de quitar los espacios.
		if(!(comandoNormalizado.isEmpty() || comandoNormalizado.isBlank()) && comandoNormalizado != null) {
			String[] partesComando = comandoNormalizado.split("\\s+");
			String[] partesComandoDetallado = comandoNormalizado.split("\\s+", 2);

			String accion = partesComando[0];
			String argumento = partesComando.length > 1 ? partesComando[1] : null;
			String argumentoDetallado = partesComandoDetallado.length > 1 ? partesComandoDetallado[1] : null;

			// Comprobar que el comando exista en la lista enum.
			ListaComandos comandoEnum = obtenerComandoEnum(accion);
			if(comandoEnum != null && esComandoActivado(comandoEnum)) {
				switch(ListaComandos.obtenerAtajo(accion)) {
				case LEVANTARSE:
					comandoLevantarse();
					break;
				case IR:
					comandoIr(argumento);
					break;
				case VOLVER:
					comandoVolver();
					break;
				case ENTRAR:
					comandoEntrar(argumentoDetallado);
					break;
				case SALIR:
					comandoSalir(argumentoDetallado);
					break;
				case LUGAR:
					comandoLugar();
					break;
				case EXPLORAR:
					comandoExplorar();
					break;
				case MIRAR:
					comandoMirar();
					break;
				case BUSCAR:
					comandoBuscar(argumento);
					break;
				case COGER:
					comandoCoger(argumentoDetallado);
					break;
				case SOLTAR:
					comandoSoltar(argumentoDetallado);
					break;	
				case ABRIR:
					comandoAbrir(argumentoDetallado);
					break;
				case ESTADO: 
					comandoEstado();
					break;
				case ALMACENAR:
					comandoAlmacenar(argumentoDetallado);
					break;
				case SACAR:
					comandoSacar(argumentoDetallado);
					break;
				case DESTRUIR:
					comandoDestruir(argumentoDetallado);
					break;
				case INVENTARIO:
					comandoInventario();
					break;
				case MISION:
					comandoMision();
					break;
				case DIARIO:
					comandoDiario();
					break;
				case HABLAR:
					comandoHablar(argumentoDetallado);
					break;
				case ADIOS:
					comandoAdios();
					break;
				case TERMINAR:
					comandoTerminar();
					break;
				case REINICIAR:
					comandoReiniciar();
					break;
				case GUARDAR:
					comandoGuardar();
					break;
				case AYUDA:
					comandoAyuda();
					break;
				case CREDITOS:
					comandoCreditos();
					break;
				case TOC:
					comandoToc(argumento);
					break;
				case VERSION:
					comandoVersion();
					break;
				case TEMA:
					comandoTema(argumento);
					break;
				default:
					juego.outputTexto("No conozco esa acción.", Fuente.fuenteBase);
					break;
				}

			} else {
				// Comprobar que el comando exista y se haya desactivado.
				boolean comandoExistenteEncontrado = false;

				for(String comandoExistente : listaComandos) {
					if(NormalizarCadena.quitarAcentos(accion).equalsIgnoreCase(comandoExistente)) {
						comandoExistenteEncontrado = true;
						break;
					}
				}

				if(!comandoExistenteEncontrado) {
					juego.outputTexto("No conozco esa acción.", Fuente.fuenteBase);

				} else if(!jugador.isDePie()) {
					juego.outputTexto("Sería mejor que te levantaras del suelo antes de hacer eso.", Fuente.fuenteBase);

				} else {
					if(comandoEnum != null && !esComandoActivado(comandoEnum)) {
						StringBuilder mensaje = new StringBuilder();
						ListaComandos accionJugador = ListaComandos.obtenerAtajo(accion);
						if(accionJugador.equals(ListaComandos.LEVANTARSE)) {
							mensaje.append(jugador.isDePie() ? "Ya estás de pie." : "Ahora no puedes levantarte.");
						} else if(accionJugador.equals(ListaComandos.IR)) {
							mensaje.append("En este momento no puedes ir a ningún sitio.");
						} else if(accionJugador.equals(ListaComandos.VOLVER)){
							mensaje.append("En estos momentos no puedes volver a ningún lugar.");
						} else if(accionJugador.equals(ListaComandos.ENTRAR)) {
							mensaje.append("Ahora mismo no puedes entrar a ningún sitio.");
						} else if(accionJugador.equals(ListaComandos.EXPLORAR)){
							mensaje.append("Ahora no puedes explorar.");
						} else if(accionJugador.equals(ListaComandos.MIRAR)){
							mensaje.append("En este momento no puedes echar un vistazo.");
						} else if(accionJugador.equals(ListaComandos.BUSCAR)){
							mensaje.append("Ahora mismo no puedes buscar ningún objeto.");
						} else if(accionJugador.equals(ListaComandos.COGER)){
							mensaje.append("En este momento no puedes coger ningún objeto del lugar.");
						} else if(accionJugador.equals(ListaComandos.SOLTAR)){
							mensaje.append("No puedes soltar ningún objeto en este momento.");
						} else if(accionJugador.equals(ListaComandos.ALMACENAR)){
							mensaje.append("En estos momentos no puedes almacenar ningún objeto a un contendor.");
						} else if(accionJugador.equals(ListaComandos.SACAR)){
							mensaje.append("En este momento no puedes sacar ningún objeto de un contendor.");
						} else if(accionJugador.equals(ListaComandos.DESTRUIR)) {
							mensaje.append("No puedes destruir ningún objeto ahora mismo.");
						} else if(accionJugador.equals(ListaComandos.INVENTARIO)) {
							mensaje.append("En este momento no puedes ver tu inventario.");
						} else if(accionJugador.equals(ListaComandos.HABLAR)) {
							if(listaComandosActivados.contains(ListaComandos.HABLAR.name())) {
								mensaje.append("En este momento no puedes hablar con ningún personaje.");
							} else {
								mensaje.append("Ya estás hablando con un personaje.");
							}
						} else if(accionJugador.equals(ListaComandos.ADIOS)) {
							mensaje.append("En este momento no te puedes despedir de ningún personaje.");
						} else if(accionJugador.equals(ListaComandos.TERMINAR)) {
							mensaje.append("En este momento no puedes salir de la partida.");
						} else if(accionJugador.equals(ListaComandos.REINICIAR)) {
							mensaje.append("En este momento no puedes reiniciar la partida.");
						} else if(accionJugador.equals(ListaComandos.GUARDAR)) {
							mensaje.append("En este momento no puedes guardar la partida.");
						} else if(accionJugador.equals(ListaComandos.TEMA)){
							mensaje.append("En este momento no puedes cambiar el tema de la interfaz.");

						} else { // Mensaje genérico para cualquier otro comando.
							mensaje.append("En este instante no puedes hacer eso.");
						}

						juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
					}
				}
			}

		} else {
			contadorEspaciosVacios++;
			if(contadorEspaciosVacios >= Aleatorio.Int(2, 4)) {
				String[] mensajeArray = {
						"¿Te sientes orgulloso de ti mismo?", 
						"¿Estás ya satisfecho?", 
						"¿Puedes escribir un comando válido?", 
				"¿Te diviertes?"};
				juego.outputTexto(mensajeArray[Aleatorio.Int(0, mensajeArray.length - 1)], Fuente.fuenteBase);
				if(contadorEspaciosVacios > Aleatorio.Int(5, 7)) {
					contadorEspaciosVacios = 0;
				}
			} else {
				juego.outputTexto("¿Perdón?", Fuente.fuenteBase);
			}
		}
	}

	// Método para obtener el comando de la lista de enum de comandos.
	private ListaComandos obtenerComandoEnum(String accion) {
		for(ListaComandos comando : ListaComandos.values()) {
			if(comando.name().equalsIgnoreCase(accion) || Arrays.asList(comando.atajo).contains(accion.toUpperCase())) {
				return comando;
			}
		}
		return null;
	}


	/*
	 * 
	 * 
	 * 
	 * 
	 * DEFINICIÓN DE TODOS LOS COMANDOS DEL JUEGO
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * 
	 * 
	 *  EMPEZAR
	 *  
	 *  
	 *
	private void comandoEmpezar() {
		if(!juego.empezarJuego && comandosActivados.contains(ListaComandos.EMPEZAR.name())) {
			juego.empezarJuego = true;
			juego.outputTexto("¡Comienza tu aventura!");

			// Inicializar lista de comandos activados.
			comandosActivados.clear();
			for(ListaComandos comando : ListaComandos.values()) {
				comandosActivados.add(comando.name());
				setComandoActivado(comando, true);
			}

		} else {
			juego.outputTexto("Ya has empezado tu aventura!");
		}
	}*/

	//////////////////////
	// 					//
	//	  MOVIMIENTO	//
	//					//
	//////////////////////

	// Método para obtener la ubicación actual del jugador.
	private Habitacion obtenerHabitacionActual() {
		return jugador.getUbicacion();
	}

	// Método para moverse entre habitaciones.
	private Habitacion obtenerHabitacionEnDireccion(Habitacion habitacionActual, Mapa.Direccion direccion) {
		return mapa.moverJugador(habitacionActual, direccion);
	}


	/*
	 * 
	 * 
	 *  LEVANTARSE
	 *  
	 *  
	 */
	private void comandoLevantarse() {
		StringBuilder mensaje = new StringBuilder();

		if(!jugador.isDePie()) {
			jugador.setDePie(true);
			// Volver a activar comandos.
			inicializarComandosActivados();

			mensaje.append("Te has puesto de pie.");

		} else {
			mensaje.append("Ya estás de pie.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  IR
	 *  
	 *  
	 */
	private void comandoIr(String arg) {
		Habitacion habitacionActual = obtenerHabitacionActual();
		StringBuilder mensaje = new StringBuilder();

		Direccion direccion = obtenerDireccion(arg);
		if(direccion == null) {
			mostrarOpcionesDeDirecciones(mensaje);
			juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
			return;
		}

		Habitacion nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, direccion);
		
		// Validación para habitaciones no disponibles.
		if(nuevaHabitacion == null || nuevaHabitacion == habitacionActual) {
			juego.outputTexto("No puedes ir en esa dirección desde aquí.", Fuente.fuenteBase);
			return;
		}

		if(!esMovimientoValido(habitacionActual, nuevaHabitacion, mensaje)) {
			juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
			return;
		}

		realizarMovimiento(habitacionActual, nuevaHabitacion, direccion, mensaje);
		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}

	/*
	 * Métodos auxiliares
	 */

	// Método para obtener la dirección donde quieres dirigirte.
	private Direccion obtenerDireccion(String arg) {
		// Si no hay argumento, comprobar el comando normalizado.
		if(arg == null || arg.isEmpty()) {
			return Direccion.obtenerAtajo(comandoNormalizado);
		}

		// Normalizar el argumento para evitar problemas con los acentos.
		String argNormalizado = NormalizarCadena.quitarAcentos(arg).toUpperCase();

		// Si hay un argumento, comprobar si es una dirección o su atajo.
		return Direccion.obtenerAtajo(argNormalizado);
	}

	// Método para mostrar el mensaje de error al introducir una dirección no válida.
	private void mostrarOpcionesDeDirecciones(StringBuilder mensaje) {
		mensaje.append("Parece que no puedes ir por ahí.");
		
		/*for(Direccion direccion : Direccion.values()) {
			mensaje.append("%c[comando]").append(direccion.name())
			.append(" (").append(String.join(", ", direccion.getAtajo())).append(")%/c, ");
		}
		mensaje.setLength(mensaje.length() - 2); // Eliminar la última coma.
		mensaje.append(".");*/
	}

	// Método para validar si el movimiento es válido.
	private boolean esMovimientoValido(Habitacion habitacionActual, Habitacion nuevaHabitacion, StringBuilder mensaje) {
		Habitacion.Tipo tipoActual = habitacionActual.getTipo();
		Habitacion.Tipo tipoNuevo = nuevaHabitacion.getTipo();

		if(!tipoActual.equals(Habitacion.Tipo.INTERIOR) && tipoNuevo.equals(Habitacion.Tipo.INTERIOR)) {
			mensaje.append("Para acceder a un interior utiliza el comando %c[comando]ENTRAR <UBICACIÓN>%/c.");
			return false;
		}
		if(tipoActual.equals(Habitacion.Tipo.INTERIOR) && tipoNuevo.equals(Habitacion.Tipo.EXTERIOR)) {
			mensaje.append("Para salir al exterior utiliza el comando %c[comando]SALIR <UBICACIÓN>%/c.");
			return false;
		}
		return true;
	}

	// Método para realizar el movimiento en caso de ser una dirección válida.
	private void realizarMovimiento(Habitacion habitacionActual, Habitacion nuevaHabitacion, Direccion direccion, StringBuilder mensaje) {
		jugador.setUbicacion(nuevaHabitacion);

		mensaje.append("%icon[walk] ");
		mensaje.append("Te mueves en dirección ")
		.append(direccion.name().toLowerCase())
		.append(".\n").append(nuevaHabitacion.getNombre());

		if(!nuevaHabitacion.isVisitada()) {
			mensaje.append("\n").append(nuevaHabitacion.getDescripcion());
			nuevaHabitacion.setVisitada(true);
		}

		juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
		ultimaDireccionUsada = direccion; // Guardar la última dirección usada.
	}


	/*
	 * 
	 * 
	 *  VOLVER
	 *  
	 *  
	 */
	private void comandoVolver() {
		// Regresa a la dirección opuesta.
		Habitacion habitacionActual = obtenerHabitacionActual();
		Habitacion nuevaHabitacion;
		StringBuilder mensaje = new StringBuilder();

		if(ultimaDireccionUsada != null) {
			nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, ultimaDireccionUsada.opuesta());

			if(nuevaHabitacion != null && nuevaHabitacion != habitacionActual) {
				jugador.setUbicacion(nuevaHabitacion);

				// Mostrar el mensaje de manera dinámica según si vas o vuelves de la habitación.
				boolean esDireccionVertical = ultimaDireccionUsada.equals(Direccion.ARRIBA) || ultimaDireccionUsada.equals(Direccion.ABAJO);

				// Verificar si vienes de un "interior" o "exterior"
				boolean esInterior = habitacionActual.getTipo() == Habitacion.Tipo.INTERIOR;
				boolean esExterior = habitacionActual.getTipo() == Habitacion.Tipo.EXTERIOR;

				// Alternar el mensaje según el valor de alternarMensaje
				if(alternarMensajeComandoVolver) {
					mensaje.append("%icon[walk-back] ");
					if(esInterior && nuevaHabitacion.getTipo() == Habitacion.Tipo.EXTERIOR) {
						// Regresas del interior al exterior
						mensaje.append("Regresas al exterior");
					} else if(esExterior && nuevaHabitacion.getTipo() == Habitacion.Tipo.INTERIOR) {
						// Vuelves del exterior al interior
						mensaje.append("Vuelves al interior");
					} else if(esDireccionVertical) {
						mensaje.append("Regresas hacia ")
						.append(ultimaDireccionUsada.opuesta().toString().toLowerCase());
					} else {
						mensaje.append("Regresas en dirección ")
						.append(ultimaDireccionUsada.opuesta().toString().toLowerCase());
					}
				} else {
					mensaje.append("%icon[walk] ");
					if(esInterior && nuevaHabitacion.getTipo() == Habitacion.Tipo.EXTERIOR) {
						// Regresas del interior al exterior
						mensaje.append("Regresas al exterior");
					} else if(esExterior && nuevaHabitacion.getTipo() == Habitacion.Tipo.INTERIOR) {
						// Vuelves del exterior al interior
						mensaje.append("Vuelves al interior");
					} else if(esDireccionVertical) {
						mensaje.append("Vuelves hacia ")
						.append(ultimaDireccionUsada.opuesta().toString().toLowerCase());
					} else {
						mensaje.append("Vuelves en dirección ")
						.append(ultimaDireccionUsada.opuesta().toString().toLowerCase());
					}
				}

				mensaje.append(".\n").append(nuevaHabitacion.getNombre());

				// Mostrar descripción de la habitación solamente cuando es la primera vez que la visitas.
				if(!nuevaHabitacion.isVisitada()) {
					mensaje.append("\n").append(nuevaHabitacion.getDescripcion());
				}

				// Alternar el valor de alternarMensaje para la siguiente ejecución
				alternarMensajeComandoVolver = !alternarMensajeComandoVolver;

				// Actualizar última dirección usada
				ultimaDireccionUsada = ultimaDireccionUsada.opuesta();
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());

				// Marcar la habitación como visitada
				nuevaHabitacion.setVisitada(true);
			}

		} else {
			mensaje.append("No puedes retroceder sin explorar nuevas ubicaciones previamente.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	/*
	 * 
	 * 
	 *  ENTRAR
	 *  
	 *  Te permite entrar a una habitación de tipo 'interior'.
	 *  
	 *  
	 */
	private void comandoEntrar(String arg) {    
		Habitacion habitacionActual = jugador.getUbicacion();
		StringBuilder mensaje = new StringBuilder();

		if(arg != null && habitacionActual != null) {
			// Normalizamos el nombre de la habitación actual y la proporcionada por el jugador
			StringBuilder habitacionActualNormalizada = new StringBuilder(NormalizarCadena.quitarAcentos(habitacionActual.getNombre()).toUpperCase());
			StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());

			// Verificamos si el jugador intenta entrar a la misma ubicación donde ya está.
			if(argNormalizado.toString().equalsIgnoreCase(habitacionActualNormalizada.toString())) {
				mensaje.append("Ya te encuentras en ").append(habitacionActual.getNombre()).append(".");
				juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
				return;
			}

			Habitacion nuevaHabitacion = null;
			Direccion nuevaDireccion = null;

			// Intentamos encontrar la habitación conectada
			for(Direccion direccion : Direccion.values()) {
				nuevaDireccion = direccion;
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, nuevaDireccion);

				// Si encontramos la habitación y es de tipo INTERIOR, procedemos
				if(nuevaHabitacion != null) {
					StringBuilder nombreHabitacionNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(nuevaHabitacion.getNombre()).toUpperCase());

					// Si el nombre normalizado coincide con la habitación que el jugador quiere entrar
					if(argNormalizado.toString().equalsIgnoreCase(nombreHabitacionNormalizado.toString())) {
						// Si la habitación es de tipo EXTERIOR, mostramos el mensaje de error.
						if(nuevaHabitacion.getTipo() == Tipo.EXTERIOR) {
							if(habitacionActual.getTipo() == Tipo.EXTERIOR) {
								mensaje.append("Si quieres ir por ese camino, mejor usa el comando %c[comando]IR <DIRECCIÓN>%/c.");
							} else if(habitacionActual.getTipo() == Tipo.INTERIOR) {
								mensaje.append("Si quieres salir al exterior, utiliza el comando %c[comando]SALIR <UBICACIÓN>%/c.");           
							}

							juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
							return; // Salir para evitar que siga procesando.

						}
						// TODO: manejar esta mierda.
						/* else if(nuevaHabitacion.getTipo() == Tipo.SUPERIOR){
	                    	mensaje.append("Si quieres subir, usa el comando %c[comando]IR ARRIBA%/c.");
	                    	juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	                        return; // Salir para evitar que siga procesando.

	                    } else if(nuevaHabitacion.getTipo() == Tipo.INFERIOR){
	                    	mensaje.append("Si quieres bajar, usa el comando %c[comando]IR ABAJO%/c.");
	                    	juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	                        return; // Salir para evitar que siga procesando.
	                    }*/

						// Si la habitación es INTERIOR, procedemos a cambiar de habitación.
						jugador.setUbicacion(nuevaHabitacion);
						mensaje.append("%icon[entry-door] Entras en ").append(nuevaHabitacion.getNombre()).append(".");

						if(!nuevaHabitacion.isVisitada()) {
							mensaje.append("\n").append(nuevaHabitacion.getDescripcion());
							nuevaHabitacion.setVisitada(true);
						}

						juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
						ultimaDireccionUsada = nuevaDireccion; // Guardar última dirección para el comando 'SALIR'.
						juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
						return;
					}
				}
			}

			// Si no encontramos la habitación que coincide con el nombre proporcionado
			mensaje.append("No es posible entrar a esa ubicación.");

		} else {
			mensaje.append("¿Dónde quieres entrar?");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}



	/*
	 * 
	 * 
	 *  SALIR
	 *  
	 *  Te permite salir al exterior desde una habitación de tipo interior.
	 *  
	 *  
	 */
	private void comandoSalir(String arg) {
		Habitacion habitacionActual = jugador.getUbicacion();
		StringBuilder mensaje = new StringBuilder();

		if(arg != null && habitacionActual != null) {
			Habitacion nuevaHabitacion = null;
			Direccion nuevaDireccion = null;

			for(Direccion direccion : Direccion.values()) {
				nuevaDireccion = direccion;
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, nuevaDireccion);
				if(nuevaDireccion != null && nuevaHabitacion != null && nuevaHabitacion != habitacionActual
						&& nuevaHabitacion.getTipo().equals(Tipo.EXTERIOR)) {
					break;
				}
			}

			if(nuevaHabitacion != null && habitacionActual.getTipo().equals(Habitacion.Tipo.INTERIOR)) {
				StringBuilder nombreNuevaHabitacion = new StringBuilder(NormalizarCadena.quitarAcentos(nuevaHabitacion.getNombre()).toUpperCase());
				StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());

				if(argNormalizado.toString().equalsIgnoreCase(nombreNuevaHabitacion.toString())) {
					jugador.setUbicacion(nuevaHabitacion);
					mensaje.append("%icon[exit-door] Sales a ").append(nuevaHabitacion.getNombre()).append(".");

					if(!nuevaHabitacion.isVisitada()) {
						mensaje.append("\n" + nuevaHabitacion.getDescripcion());
						nuevaHabitacion.setVisitada(true);
					}

					juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
					ultimaDireccionUsada = nuevaDireccion; // Guardar última dirección.

				} else {
					mensaje.append("No reconozco el lugar '").append(arg).append("'.");
				}

			} else {
				if(habitacionActual.getTipo().equals(Habitacion.Tipo.EXTERIOR)) {
					mensaje.append("Ya te encuentras en el exterior.");

				} else {
					mensaje.append("No puedes salir. No hay ninguna salida al exterior desde aquí.");
				}
			}

		} else {
			mensaje.append("¿Adónde quieres salir?");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	//////////////////////
	// 					//
	//	  EXPLORACIÓN	//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  LUGAR
	 *  
	 *  Muestra la descripción del lugar.
	 *  
	 *  
	 */
	private void comandoLugar() {
		Habitacion habitacionActual = jugador.getUbicacion();

		juego.outputTexto(habitacionActual.getDescripcion(), Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  EXPLORAR
	 *  
	 *  
	 */
	private void comandoExplorar() {
		// Muestra todas las conexiones (salidas) que tiene la habitación actual.
		Habitacion habitacionActual = jugador.getUbicacion();
		StringBuilder mensaje = new StringBuilder();

		if(habitacionActual != null) {
			Map<Direccion, Habitacion> salidas = habitacionActual.getSalidas();

			if(!salidas.isEmpty()) {
				if(salidas.size() == 1) {
					mensaje.append("Exploras el lugar y encuentras una única ruta:");
				} else {
					mensaje.append("Exploras el lugar y encuentras varias rutas:");
				}

				for(Map.Entry<Direccion, Habitacion> entry : salidas.entrySet()) {
					Direccion direccion = entry.getKey();
					Habitacion habitacionConectada = entry.getValue();

					// Determinar el icono según la dirección
					String icono = switch(direccion) {
					case ESTE -> "%icon[arrow-right]";
					case OESTE -> "%icon[arrow-left]";
					case NORTE -> "%icon[arrow-up]";
					case SUR -> "%icon[arrow-down]";
					case NOROESTE -> "%icon[arrow-up-left]";
					case NORESTE -> "%icon[arrow-up-right]";
					case SUDOESTE -> "%icon[arrow-down-left]";
					case SUDESTE -> "%icon[arrow-down-right]";
					case ARRIBA -> "%icon[upstairs]";
					case ABAJO -> "%icon[downstairs]";
					default -> "";
					};

					mensaje.append("\n")
					.append(icono)
					.append(" ").append(direccion).append(": ")
					.append(habitacionConectada.getNombre());

					// Verificar el tipo de habitación conectada
					if(habitacionActual.getTipo() == Habitacion.Tipo.INTERIOR) {
						switch(habitacionConectada.getTipo()) {
						case EXTERIOR -> mensaje.append(" (Exterior)");
						case INTERIOR -> mensaje.append("");
						case SUPERIOR -> mensaje.append("");
						case INFERIOR -> mensaje.append("");
						}
					}

					// Verificar el tipo de habitación conectada
					if(habitacionActual.getTipo() == Habitacion.Tipo.EXTERIOR && habitacionConectada.getTipo() == Habitacion.Tipo.INTERIOR) {
						switch(habitacionConectada.getTipo()) {
						case EXTERIOR -> mensaje.append("");
						case INTERIOR -> mensaje.append(" (Interior)");
						case SUPERIOR -> mensaje.append("");
						case INFERIOR -> mensaje.append("");
						}
					}
				}

			} else {
				mensaje.append("No encuentras ninguna ruta.");
			}
		} else {
			mensaje.append("No puedes explorar en este momento.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

	}

	/*
	 * 
	 * 
	 *  MIRAR
	 *  
	 *  
	 */
	private void comandoMirar() {
		// Comprueba si hay Pnjs o enemigos en la habitación actual.
		StringBuilder mensaje = new StringBuilder();
		Habitacion habitacionActual = obtenerHabitacionActual();
		List<Personaje> pnjsEnHabitacion = habitacionActual.getPnjs();
		List<Enemigo> enemigosEnHabitacion = habitacionActual.getEnemigos();

		if(pnjsEnHabitacion != null && !pnjsEnHabitacion.isEmpty() || enemigosEnHabitacion != null && !enemigosEnHabitacion.isEmpty()) {
			boolean hayPnj = false;

			// Caso PNJ.
			if(pnjsEnHabitacion != null && !pnjsEnHabitacion.isEmpty()) {
				hayPnj = true;
				mensaje.append("%icon[look-at] Miras a tu alrededor y ves a ").append((pnjsEnHabitacion.size() > 1 ? "varios %c[pnj]personajes%/c cerca:" : "un personaje cerca:"));

				for(Personaje pnj : pnjsEnHabitacion) {
					mensaje.append("\n\t").append(pnj.getNombre()).append(" (").append(pnj.getTipo()).append(")");
				}
			}

			// Caso Enemigo.
			if(enemigosEnHabitacion != null && !enemigosEnHabitacion.isEmpty()) {
				if(hayPnj) {
					mensaje.append("\n\n%icon[look-at] También ves a ").append((enemigosEnHabitacion.size() > 1 ? "múltiples %c[enemigo]enemigos%/c en la cercanía:" : "un enemigo en la cercanía:"));

				} else {
					mensaje.append("%icon[look-at] Observas el lugar y ves a ").append((enemigosEnHabitacion.size() > 1 ? "múltiples %c[enemigo]enemigos%/c en la cercanía:" : "un enemigo en la cercanía:"));
				}

				for(Enemigo enemigo : enemigosEnHabitacion) {
					mensaje.append("\n\t").append(enemigo.getNombre()).append(" (").append(enemigo.getTipo().getTipoLegible()).append(")");
				}
			}

		} else {
			mensaje.append("No ves a nadie en este lugar. El lugar parece tranquilo... por ahora.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

	}

	/*
	 * 
	 * 
	 *  BUSCAR
	 *  
	 *  
	 */
	private void comandoBuscar(String arg) {
		// Comprueba si hay objetos en la habitación actual.
		Habitacion habitacionActual = obtenerHabitacionActual();
		List<Objeto> objetosHabitacion = habitacionActual.getObjetos();

		if(objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
			StringBuilder mensaje = new StringBuilder("Buscas objetos y encuentras:");

			for(Objeto objeto : objetosHabitacion) {
				mensaje.append("\n").append(objeto.getIcono()).append(" ").append(objeto.getNombre()).append(": ").append(objeto.getDescripcion());
			}

			juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

		} else {
			juego.outputTexto("No encuentras ningún objeto en este lugar.", Fuente.fuenteBase);
		}

	}

	/*
	 * 
	 * 
	 *  COGER
	 *  
	 *  
	 */
	private void comandoCoger(String arg) {
		StringBuilder mensaje = new StringBuilder();
		Habitacion habitacionActual = obtenerHabitacionActual();
		List<Objeto> objetosHabitacion = habitacionActual.getObjetos();
		List<Objeto> inventarioJugador = jugador.getInventario();
		Iterator<Objeto> iterador = objetosHabitacion.iterator();
		boolean encontrado = false;

		while(iterador.hasNext()) {
			Objeto objeto = iterador.next();
			// Verificar que la lista de objetos de la habitación no sea null.
			if(objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
				// Comprobar si el jugador ya tiene un objeto contenedor en su inventario.
				boolean tieneContenedor = false;
				for(Objeto objetoJugador : inventarioJugador) {
					if(objetoJugador instanceof Objeto_Contenedor) {
						tieneContenedor = true;
						break;
					}
				}

				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase());

				if(arg != null) {
					StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());
					// Comprobar si el objeto está en la habitación.
					if(inventarioJugador.size() < jugador.getMaxObjetosInventario() && 
							argNormalizado.toString().equalsIgnoreCase(objetoNormalizado.toString())) {
						// Si el jugador ya tiene un objeto contenedor, no puede coger más objetos contenedor.
						if(objeto instanceof Objeto_Contenedor && tieneContenedor) {
							mensaje.append("%icon[halt] No has cogido '")
							.append(objeto.getNombre())
							.append("' porque ya posees un objeto contenedor.");

						} else if(objeto instanceof Objeto_Cerradura) {
							// Comprobar que el objeto que quiere coger no sea un objeto con cerradura.
							mensaje.append("%icon[weight] No puedes coger el objeto '")
							.append(objeto.getNombre())
							.append("'. Es demasiado pesado.");

						} else {
							jugador.agregarObjetoAlInventario(objeto);
							mensaje.append("%icon[card-play] Has cogido ").append(objeto.getNombre()).append(".");
							// Eliminar el objeto de la habitación.
							iterador.remove();
						}
						encontrado = true;
						break;

					} else if(arg != null && argNormalizado.toString().equalsIgnoreCase("todo")) {
						// Si el jugador ya tiene un objeto contenedor, no puede coger más objetos.
						if(objeto instanceof Objeto_Contenedor && tieneContenedor) {
							mensaje.append("%icon[halt] No puedes coger '")
							.append(objeto.getNombre()).append("' si ya posees otro objeto contenedor.");

						} else if(objeto instanceof Objeto_Cerradura) {
							// Comprobar que el objeto que quiere coger no sea un objeto con cerradura.
							mensaje.append("%icon[weight] No puedes coger el objeto '").append(objeto.getNombre()).append("'. Es demasiado pesado.\n");

						} else {
							jugador.agregarObjetoAlInventario(objeto);
							mensaje.append("%icon[card-play] Has cogido ").append(objeto.getNombre()).append(".\n");
							// Eliminar el objeto de la habitación.
							iterador.remove();
						}
						encontrado = true;
					}

				}

			} else {
				mensaje.append("No hay objetos en este sitio.");
				break;
			}
		}

		// Mensajes adicionales según el resultado de la acción
		if(!encontrado && arg != null) {
			mensaje.append("El objeto '" + arg + "' no se encuentra en este lugar.");
		} else if(inventarioJugador.size() >= jugador.getMaxObjetosInventario()) {
			mensaje.append("\nInventario lleno. Deshazte de algún objeto para poder guardar otro.");
		} else if(arg == null && objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
			mensaje.append("¿Qué objeto quieres coger?");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}



	//////////////////////
	// 					//
	//	  INVENTARIO	//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  ALMACENAR
	 *  
	 *  
	 */
	private void comandoAlmacenar(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar que el inventario del jugador no esté vacío.
		if(arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Dividir el 'arg' en dos partes: objeto y contenedor.
			String[] partes = arg.split(" EN ", 2);
			if(partes.length == 2) {
				String nombreObjeto = partes[0].trim();
				String nombreContenedor = partes[1].trim();

				// Normalizar las cadenas de los nombres para compararlas sin acentos y en mayúsculas.
				String nombreObjetoNormalizado = NormalizarCadena.quitarAcentos(nombreObjeto).toUpperCase();
				String nombreContenedorNormalizado = NormalizarCadena.quitarAcentos(nombreContenedor).toUpperCase();

				// Buscar el objeto en el inventario del jugador.
				Objeto objetoAGuardar = null;
				for(Objeto objeto : inventarioJugador) {
					String objetoNombreNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase();
					if(objetoNombreNormalizado.equals(nombreObjetoNormalizado)) {
						objetoAGuardar = objeto;
						break;
					}
				}

				if(objetoAGuardar != null) {
					// Buscar el contenedor en el inventario del jugador.
					Objeto_Contenedor contenedor = null;
					for(Objeto objeto : inventarioJugador) {
						String objetoContenedorNombreNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase();
						if(objeto instanceof Objeto_Contenedor && objetoContenedorNombreNormalizado.equals(nombreContenedorNormalizado)) {
							contenedor = (Objeto_Contenedor) objeto;
							break;
						}
					}

					if(contenedor != null) {
						// Verificar si el objeto a almacenar es el mismo que el contenedor.
						if(objetoAGuardar.equals(contenedor)) {
							mensaje.append("No puedes almacenar un objeto dentro de sí mismo.");
						} else {
							// Comprobar que el contenedor tiene espacio suficiente para almacenar más objetos.
							if(contenedor.getObjetosContenidos().size() < contenedor.getMaxObjetosContenidos()) {
								// Almacenar el objeto en el contenedor.
								contenedor.getObjetosContenidos().add(objetoAGuardar);
								// Eliminar el objeto del inventario.
								inventarioJugador.remove(objetoAGuardar);
								mensaje.append("'").append(objetoAGuardar.getNombre())
								.append("' almacenado en '").append(contenedor.getNombre()).append("'.");
							} else {
								mensaje.append("El contenedor '").append(contenedor.getNombre()).append("' está lleno.");
							}
						}
					} else {
						mensaje.append("El contenedor '").append(nombreContenedor).append("' no está en tu inventario.");
					}
				} else {
					mensaje.append("El objeto '").append(nombreObjeto).append("' no está en tu inventario.");
				}
			} else {
				mensaje.append("Para almacenar un objeto en un contenedor usa el comando 'ALMACENAR <OBJETO> EN <CONTENEDOR>'.");
			}
		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres almacenar y dónde?");
			} else {
				mensaje.append("Tu inventario está vacío.");
			}
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	/*
	 * 
	 * 
	 *  SACAR
	 *  
	 *  
	 */
	private void comandoSacar(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar que el inventario del jugador no esté vacío.
		if(arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Dividir el 'arg' en dos partes: objeto y contenedor.
			String[] partes = arg.split(" DE ", 2);
			if(partes.length == 2 && !partes[0].endsWith(" ")) {
				String nombreObjeto = partes[0].trim();
				String nombreContenedor = partes[1].trim();

				// Normalizar las cadenas de los nombres para compararlas sin acentos y en mayúsculas.
				String nombreObjetoNormalizado = NormalizarCadena.quitarAcentos(nombreObjeto).toUpperCase();
				String nombreContenedorNormalizado = NormalizarCadena.quitarAcentos(nombreContenedor).toUpperCase();

				// Buscar el contenedor en el inventario del jugador.
				Objeto_Contenedor contenedor = null;
				for(Objeto objeto : inventarioJugador) {
					String objetoContenedorNombreNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase();
					if(objeto instanceof Objeto_Contenedor && objetoContenedorNombreNormalizado.equals(nombreContenedorNormalizado)) {
						contenedor = (Objeto_Contenedor) objeto;
						break;
					}
				}

				if(contenedor != null) {

					if(nombreObjetoNormalizado.equals("TODO")) {
						Iterator<Objeto> iterador = contenedor.getObjetosContenidos().iterator();
						while(iterador.hasNext()) {
							Objeto objeto = iterador.next();
							if(inventarioJugador.size() < jugador.getMaxObjetosInventario()) {
								// Añadir objeto al inventario.
								inventarioJugador.add(objeto);
								// Eliminar objeto del contenedor.
								iterador.remove();
								mensaje.append("Has sacado '")
								.append(objeto.getNombre())
								.append("' de '")
								.append(contenedor.getNombre())
								.append("'.\n");
							} else {
								mensaje.append("Tu inventario está lleno. No puedes sacar más objetos.");
								break;
							}
						}
					} else {
						// Buscar el objeto dentro del contenedor.
						Objeto objetoASacar = null;
						for(Objeto obj : contenedor.getObjetosContenidos()) {
							String objetoNombreNormalizado = NormalizarCadena.quitarAcentos(obj.getNombre()).toUpperCase();
							if(objetoNombreNormalizado.equals(nombreObjetoNormalizado)) {
								objetoASacar = obj;
								break;
							}
						}

						if(objetoASacar != null) {
							// Comprobar que el inventario del jugador tiene espacio suficiente para agregar el objeto sacado.
							if(inventarioJugador.size() < jugador.getMaxObjetosInventario()) {
								// Agregar el objeto al inventario del jugador.
								inventarioJugador.add(objetoASacar);
								// Eliminar el objeto del contenedor.
								contenedor.getObjetosContenidos().remove(objetoASacar);
								mensaje.append("Has sacado '")
								.append(objetoASacar.getNombre())
								.append("' de '")
								.append(contenedor.getNombre())
								.append("'.");
							} else {
								mensaje.append("No tienes espacio suficiente en tu inventario para sacar '")
								.append(objetoASacar.getNombre())
								.append("'.");
							}
						} else {
							mensaje.append("El objeto '")
							.append(nombreObjeto)
							.append("' no está dentro de '")
							.append(contenedor.getNombre())
							.append("'.");
						}
					}
				} else {
					mensaje.append("El contenedor '")
					.append(nombreContenedor)
					.append("' no está en tu inventario.");
				}
			} else {
				mensaje.append("Para sacar un objeto de un contenedor usa el comando 'SACAR <OBJETO> DE <CONTENEDOR>'.");
			}
		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres sacar y de dónde?");
			} else {
				mensaje.append("Tu inventario está vacío.");
			}
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	/*
	 * 
	 * 
	 *  SOLTAR
	 *  
	 *  
	 */
	private void comandoSoltar(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();
		Habitacion habitacionActual = obtenerHabitacionActual();
		List<Objeto> objetosHabitacion = habitacionActual.getObjetos();

		// Comprobar que tenga objetos en el inventario.
		if(arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			boolean encontrado = false;

			for(Objeto objeto : inventarioJugador) {
				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
				StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg));
				// Comprobar si el objeto está en el inventario del jugador.
				if(arg != null && argNormalizado.toString().equalsIgnoreCase(objetoNormalizado.toString())) {
					// Quitar objeto del inventario del jugador.
					inventarioJugador.remove(objeto);
					mensaje.append("Has tirado '")
					.append(objeto.getNombre())
					.append("' al suelo.");
					// Añadir el objeto al inventario de la habitación (al suelo).
					objetosHabitacion.add(objeto);
					encontrado = true;
					break;

				} else if(arg != null && argNormalizado.toString().equalsIgnoreCase("todo")) {
					// Agregar los objetos del jugador a la habitación.
					objetosHabitacion.addAll(inventarioJugador);
					// Eliminar todos los objetos del inventario del jugador.
					jugador.getInventario().clear();
					juego.outputTexto("Has soltado todos tus objetos al suelo.", Fuente.fuenteBase);
					return;
				}
			}

			if(!encontrado && arg != null) {
				mensaje.append("El objeto '")
				.append(arg)
				.append("' no se encuentra en tu inventario.");
			}

		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres soltar?");
			} else {
				mensaje.append("Tu inventario está vacío. No hay nada que soltar.");
			}
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  ABRIR
	 *  
	 *  TODO: Terminar y arreglar este comando.
	 *  
	 *  - Llaves unicas para cada contenedor.
	 *  - O tener una llave generica que pueda abrir cualquier contendor.
	 *  - Tipos de llaves dependiendo de la calidad de los objetos del contenedor.
	 *  - Abrir cofres, puertas, etc.
	 *  - Al abrir cofres poder coger los objetos.
	 *  
	 */
	private void comandoAbrir(String arg) {
		StringBuilder mensaje = new StringBuilder();
		Habitacion habitacionActual = obtenerHabitacionActual();
		List<Objeto> objetosHabitacion = habitacionActual.getObjetos();
		List<Objeto> inventarioJugador = jugador.getInventario();

		boolean hayObjetoCerradura = false;

		if(arg != null) {
			// Comprobar que haya objetos con cerradura en la habitacion.
			for(Objeto objeto : objetosHabitacion) {
				if(objeto instanceof Objeto_Cerradura) {
					hayObjetoCerradura = true;
					Objeto_Cerradura objetoCerradura = (Objeto_Cerradura) objeto;
					String nombreNormalizado = NormalizarCadena.quitarAcentos(objetoCerradura.getNombre().toUpperCase());
					String argNormalizado = NormalizarCadena.quitarAcentos(arg.toUpperCase());

					if(nombreNormalizado.equals(argNormalizado)) {
						// Verificar si el jugador tiene la llave correcta en su inventario.
						boolean llaveCorrecta = false;
						for(Objeto objetoInventario : inventarioJugador) {
							if(objetoInventario != null && objetoInventario instanceof Objeto_Llave) {
								Objeto_Llave llave = (Objeto_Llave) objetoInventario;
								if(llave != null) {
									llaveCorrecta = true;
									break;
								}
							}
						}

						if(llaveCorrecta) {
							boolean tieneObjetos = !objetoCerradura.getObjetosContenidos().isEmpty();
							if(tieneObjetos) {
								mensaje.append("El '").append(objetoCerradura.getNombre()).append("' contiene:");
								for(int i = 0; i < objetoCerradura.getObjetosContenidos().size(); i++) {
									Objeto objetoContenido = objetoCerradura.getObjetosContenidos().get(i);
									if(i == objetoCerradura.getObjetosContenidos().size() - 1) {
										mensaje.append("\n└ ").append(objetoContenido.getNombre());
									} else {
										mensaje.append("\n├ ").append(objetoContenido.getNombre());
									}
								}
							} else {
								mensaje.append("'").append(objetoCerradura.getNombre()).append("' está vacío.\n");
							}
						} else {
							mensaje.append("No tienes ninguna llave para abrir '").append(objetoCerradura.getNombre()).append("'.");
						}
					}
				}
			}

			if(!hayObjetoCerradura) {
				mensaje.append("No hay ningún cofre en este lugar.");
			}
		} else {
			mensaje.append("¿Qué quieres abrir?");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  DESTRUIR
	 *  
	 *  
	 */
	private void comandoDestruir(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		if(arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			Iterator<Objeto> iterator = inventarioJugador.iterator();
			boolean objetoEncontrado = false;
			String argNormalizado = NormalizarCadena.quitarAcentos(arg).toUpperCase();

			while(iterator.hasNext()) {
				Objeto objeto = iterator.next();
				String nombreObjetoNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase();

				if(argNormalizado.equals(nombreObjetoNormalizado)) {
					// Si es un objeto de misión, no se puede destruir
					if((objeto instanceof Objeto_Comun && ((Objeto_Comun) objeto).isObjetoDeMision()) 
							|| (objeto instanceof Objeto_Arma && ((Objeto_Arma) objeto).isObjetoDeMision())) {
						mensaje.append("No puedes destruir objetos de misión.");
					} else {
						// Eliminar el objeto.
						iterator.remove();
						mensaje.append("Has destruido el objeto '")
						.append(objeto.getNombre())
						.append("'.");
					}
					objetoEncontrado = true;
					break;
				}
			}

			if(!objetoEncontrado) {
				mensaje.append("No tienes el objeto '")
				.append(arg)
				.append("' en tu inventario.");
			}
		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres destruir?");
			} else {
				mensaje.append("No llevas ningún objeto para destruir.");
			}
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	/*
	 * 
	 * 
	 *  INVENTARIO
	 *  
	 *  
	 */
	private void comandoInventario() {
		// Comprueba si hay objetos en la habitación actual.
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar si tienes objetos en el inventario.
		if(inventarioJugador != null && !inventarioJugador.isEmpty()) {
			mensaje.append("Estás portando estos objetos:");
			// Mostrar información según tipo de objeto.
			for(Objeto objeto : inventarioJugador) {
				mensaje.append("\n").append(objeto.getIcono()).append(" ");
				// OBJETO ARMA
				if(objeto instanceof Objeto_Arma) {
					Objeto_Arma objetoArma = (Objeto_Arma) objeto;
					mensaje.append(objetoArma.getNombre()).append(": ").append(objetoArma.getDescripcion())
					.append(objetoArma.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO COMÚN
				if(objeto instanceof Objeto_Comun) {
					Objeto_Comun objetoComun = (Objeto_Comun) objeto;
					mensaje.append(objetoComun.getNombre()).append(": ").append(objetoComun.getDescripcion())
					.append(objetoComun.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO CONTENEDOR
				if(objeto instanceof Objeto_Contenedor) {
					Objeto_Contenedor objetoContenedor = (Objeto_Contenedor) objeto;
					mensaje.append(objetoContenedor.getNombre()).append(": ").append(objetoContenedor.getDescripcion());

					if(objetoContenedor.getObjetosContenidos() != null && !objetoContenedor.getObjetosContenidos().isEmpty()) {
						int tamanoLista = objetoContenedor.getObjetosContenidos().size(); 

						for(int i = 0; i < tamanoLista; i++) {
							Objeto objetoContenido = objetoContenedor.getObjetosContenidos().get(i);
							mensaje.append("\n\t").append(objetoContenido.getIcono()).append(" ").append(objetoContenido.getNombre());
						}
					} else {
						mensaje.append("\n\t(Contenido vacío)");
					}

				}

				// OBJETO LLAVE
				if(objeto instanceof Objeto_Llave) {
					Objeto_Llave objetoLlave = (Objeto_Llave) objeto;
					mensaje.append(objetoLlave.getNombre()).append(": ").append(objetoLlave.getDescripcion());
				}

				// OBJETO DINERO
				if(objeto instanceof Objeto_Dinero) {
					Objeto_Dinero objetoDinero = (Objeto_Dinero) objeto;
					mensaje.append(objetoDinero.getNombre()).append(": ").append(objetoDinero.getDescripcion());
				}
			}
			juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

		} else {
			juego.outputTexto("No llevas ningún objeto.", Fuente.fuenteBase);
		}
	}



	//////////////////////
	// 					//
	//	  	JUGADOR		//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  ESTADO
	 *  
	 *  Muestra el estado de salud del jugador:
	 *  
	 *  
	 */
	private void comandoEstado() {
		StringBuilder mensaje = new StringBuilder();
		int vidas = jugador.getVidas();

		// Determinar estado según los puntos de vida del jugador.
		if(vidas >= 5) {
			mensaje.append("%icon[mighty-force] Rebosas de energía y te sientes invencible.");
		} else if(vidas >= 4) {
			mensaje.append("%icon[strong] Te sientes en buena forma, listo para lo que venga.");
		} else if(vidas == 3) {
			mensaje.append("%icon[arm-bandage] Tienes algunos rasguños, pero puedes seguir adelante.");
		} else if(vidas == 2) {
			mensaje.append("%icon[knee-bandage] Empiezas a sentirte agotado, y las heridas dificultan tus movimientos.");
		} else if(vidas == 1) {
			mensaje.append("%icon[arm-sling] Tu vida pende de un hilo. Cada paso podría ser el último si no encuentras ayuda.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}



	//////////////////////
	// 					//
	//		MISION		//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  MISION
	 *  
	 *  
	 */
	private void comandoMision() {
		juego.outputTexto("Misión actual: " + juego.misionActiva.getNombre() + "\nObjetivo: " + juego.misionActiva.getObjetivo(), Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  DIARIO
	 *  
	 *  
	 */
	private void comandoDiario() {
		// Muestra las misiones completadas.
		StringBuilder mensaje = new StringBuilder();
		List<Mision> misionesCompletadas = jugador.getDiario();

		if(misionesCompletadas != null) {
			mensaje.append("Diario de misiones:\n");

			// Mostrar la misión activa la primera de todas.
			mensaje.append("\t").append(juego.misionActiva.getNombre()).append(" (En curso)\n");

			for(Mision mision : misionesCompletadas) {
				if(mision.isCompletada() && !mision.isActivada()) {
					mensaje.append("\t").append(mision.getNombre()).append(" (Completada)\n");
				}
			}

		} else {
			mensaje.append("Todavía no has completado ninguna misión.");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

	}


	//////////////////////
	// 					//
	//		SOCIAL		//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 * HABLAR
	 * 
	 * 
	 * 
	 */
	private void comandoHablar(String arg) {
		Habitacion habitacionActual = jugador.getUbicacion();
		StringBuilder mensaje = new StringBuilder();

		// Comprobar si el argumento no es nulo ni vacío.
		if(arg != null && !arg.isEmpty()) {
			// Dividir el argumento por "CON".
			String[] partes = arg.split("CON", 2);

			if(partes.length == 2) {
				String nombrePersonaje = partes[1].trim();  // Nombre del personaje a quien hablar.
				String nombrePersonajeNormalizado = NormalizarCadena.quitarAcentos(nombrePersonaje).toLowerCase();
				Personaje personaje = null;
				boolean hayPnj = false;

				// Buscar el personaje en la habitación.
				for(Personaje pnj : habitacionActual.getPnjs()) {
					if(NormalizarCadena.quitarAcentos(pnj.getNombre()).equalsIgnoreCase(nombrePersonajeNormalizado)) {
						hayPnj = true;
						personaje = pnj;
						ultimoPersonajeHablado = pnj;
						break;
					}
				}

				// Buscar si el nombre coincide con un enemigo
				boolean esEnemigo = false;
				for(Enemigo enemigo : habitacionActual.getEnemigos()) {
					if(NormalizarCadena.quitarAcentos(enemigo.getNombre()).equalsIgnoreCase(nombrePersonajeNormalizado)) {
						esEnemigo = true;
						break;
					}
				}

				// Verificar si se ha encontrado un personaje (no enemigo) y tiene conversación disponible.
				if(hayPnj && personaje != null && !esEnemigo && personaje.obtenerConversacion() != null) {
					// Entrar en modo conversación.
					desactivarComandosExcepto(
							ListaComandos.RUMORES,
							ListaComandos.INTIMIDAR,
							ListaComandos.PERSUADIR,
							ListaComandos.HALAGAR,
							ListaComandos.SOBORNAR,
							ListaComandos.TRASFONDO,
							ListaComandos.COMERCIAR,
							ListaComandos.ADIOS
							);
					// Mostrar mensajes de la conversación.
					mensaje.append("Entablas una conversación con ").append(personaje.getNombre()).append(" (").append(personaje.getTipo()).append(")").append(". Para salir de la conversación usa el comando 'ADIÓS'.\n\n");
					mensaje.append(personaje.getNombre()).append(" (").append(personaje.getTipo()).append(")").append(":\n");
					mensaje.append("%icon[chat-bubble] ").append(personaje.saludoNormal()).append(" ");
					mensaje.append(personaje.obtenerConversacion());

				} else if(!hayPnj && !esEnemigo) { // No hay nadie.
					mensaje.append("No se ha encontrado a '").append(nombrePersonaje).append("'.");

				} else if(esEnemigo) {
					// Distinguir tipo de enemigo.
					for(Enemigo enemigo : habitacionActual.getEnemigos()) {
						// Comprobar si el nombre coincide con el enemigo que se intenta hablar.
						if(NormalizarCadena.quitarAcentos(enemigo.getNombre()).equalsIgnoreCase(nombrePersonajeNormalizado)) {
							// Mostrar mensaje dependiendo del tipo de enemigo.
							switch(enemigo.getTipo()) {
							case MALEANTE:
								mensaje.append("No puedes hablar con ese maleante, parece más interesado en vaciar tus bolsillos que en una charla amistosa.");
								break;
							case CRIATURA:
								mensaje.append("No puedes hablar con esa criatura, lo único que parece interesarle es añadir tu cara a su menú del día.");
								break;
							case MERCENARIO:
								mensaje.append("No puedes hablar con ese mercenario, parece de los que golpean primero y preguntan después.");
								break;
							case BANDIDO:
								mensaje.append("No puedes hablar con ese bandido, parece que prefiere que la cuchilla hable por él. ¡Definitivamente no es el tipo de conversación que buscas!");
								break;
							case BESTIA:
								mensaje.append("No puedes hablar con esa bestia, sus gruñidos y colmillos son lo más cercano a un saludo que recibirás. ¡Será mejor que mantengas las distancias!");
								break;
							case DEMONIO:
								mensaje.append("No puedes hablar con ese demonio, parece que prefiere negociar en fuego y azufre. ¡Quizás no sea el mejor momento para una charla profunda!");
								break;
							case NO_MUERTO:
								mensaje.append("No puedes hablar con ese no muerto, su concepto de conversación es bastante... macabro. ¡Un mal momento para filosofar sobre la vida!");
								break;
							default:
								mensaje.append("No puedes hablar con este enemigo, pero seguro que tiene otras formas 'interesantes' de interactuar contigo.");
								break;
							}
							break; // Salir del bucle tras encontrar al enemigo objetivo.
						}
					}


				} else if(personaje != null && personaje.obtenerConversacion() == null) {
					mensaje.append("'").append(personaje.getNombre()).append("' no tiene nada que decirte.");
				}

			} else {
				mensaje.append("Para hablar con un personaje, usa el comando 'HABLAR CON <NOMBRE DEL PERSONAJE>'.");
			}

		} else {
			mensaje.append("¿Con quién quieres hablar?");
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}


	/*
	 * 
	 * 
	 *  ADIOS
	 *  
	 *  Termina la conversación con un personaje.
	 *  
	 *  
	 */
	private void comandoAdios() {
		StringBuilder mensaje = new StringBuilder();

		if(ultimoPersonajeHablado != null) {
			mensaje.append(ultimoPersonajeHablado.getNombre()).append(" (").append(ultimoPersonajeHablado.getTipo()).append("):\n");
			mensaje.append("%icon[hand] ").append(ultimoPersonajeHablado.despedidaNormal());
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);

		// Volver a activar los comandos del juego.
		inicializarComandosActivados();
		// Desactivar comandos sociales excepto HABLAR.
		desactivarComandosSociales();
	}



	//////////////////////
	// 					//
	//		SISTEMA		//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  TERMINAR
	 *  
	 *  Salir del juego.
	 *  
	 *  
	 */
	private void comandoTerminar() {
		SwingUtilities.invokeLater(() -> {
			juego.outputTexto("¡Hasta la próxima, aventurero!", Fuente.fuenteBase);
			// Agregar un temporizador para dar tiempo a leer el mensaje antes de salir.
			Timer timer = new Timer(1500, (ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			timer.setRepeats(false); // Hacer que el temporizador solo se ejecute una vez
			timer.start();
		});
	}

	/*
	 * 
	 * 
	 *  REINICIAR
	 *  
	 *  
	 */
	private void comandoReiniciar() {

		// Reiniciar comandos.
		Juego.ultimoComandoUsado = "";

		// Reiniciar objetos.
		Juego.listaObjetos = new ListaObjetos();

		// Reiniciar habitaciones.
		Juego.mapa = new Mapa();
		Juego.ubicacionInicial = Juego.mapa.obtenerHabitacionInicial();

		// Reiniciar atributos del jugador.
		jugador.setNombre(jugador.getNombrePorDefecto());
		jugador.setUbicacion(Juego.ubicacionInicial);
		jugador.setPuntos(0);
		jugador.setInventario(new ArrayList<>());
		jugador.setMaxObjetosInventario(6);
		jugador.setVidas(4);
		jugador.setInmortal(false);
		jugador.diario.clear();
		jugador.setDePie(false);

		// Reiniciar misiones.
		Juego.misiones = new Misiones();
		Juego.misiones.reiniciarMisiones();
		juego.misionActiva = Juego.misiones.ejecutarMisiones();

		// Reiniciar enemigos.
		Juego.listaEnemigos = new ListaEnemigos();

		// Reiniciar pnjs.

		// Reiniciar labels y textarea.
		juego.actualizarLabelPuntos();
		juego.actualizarLabelUbicacion();
		juego.outputTexto.setText("");
		juego.inputTexto.setText("");

		juego.outputTexto("El juego se ha reiniciado.", Fuente.fuenteBase);

	}


	/*
	 * 
	 * 
	 *  GUARDAR
	 *  
	 *  
	 */
	private void comandoGuardar() {

		juego.outputTexto("%icon[disc] La partida se ha guardado correctamente.", Fuente.fuenteBase);

	}


	/*
	 * 
	 * 
	 *  AYUDA
	 *  
	 *  
	 */
	private void comandoAyuda() {
		String mensaje = "Lista de comandos:\n"
				+ "- DIRECCIÓN:\n"
				+ "  ├ IR <DIRECCION>: Te mueves en la dirección indicada.\n"
				+ "  └ VOLVER: Retrocedes a la ubicación anterior.\n"
				+ "- EXPLORACIÓN:\n"
				+ "  ├ LUGAR: Muestra la descripción de tu ubicación actual.\n"
				+ "  ├ EXPLORAR: Exploras el lugar en busca de caminos a seguir.\n"
				+ "  ├ MIRAR: Observas el alrededor para detectar personajes o enemigos.\n"
				+ "  ├ BUSCAR: Encuentra objetos en tu ubicación.\n"
				+ "  ├ COGER <OBJETO>: Coges un objeto de la ubicación actual y lo añades a tu inventario.\n"
				+ "  ├ SOLTAR <OBJETO>: Tiras un objeto de tu inventario al suelo de la ubicación actual.\n"
				+ "  ├ ALMACENAR <OBJETO> <CONTENEDOR>: Almacenas un objeto de tu inventario dentro de un contenedor.\n"
				+ "  ├ SACAR <OBJETO> <CONTENEDOR>: Sacas un objeto de un contenedor y lo devuelves al inventario.\n"
				+ "  ├ DESTRUIR <OBJETO>: Destruye un objeto de tu inventario permanentemente.\n"
				+ "  ├ INVENTARIO: Muestra los objetos que estás portando.\n"
				+ "  ├ MISION: Muestra la misión actual.\n"
				+ "  ├ DIARIO: Muestra las misiones que has completado.\n"
				+ "  └ LEVANTARSE: Te levantas del suelo si estás tumbado.\n"
				+ "- JUEGO:\n"
				+ "  ├ TERMINAR: Terminas la partida.\n"
				+ "  ├ REINICIAR: Reinicias el juego.\n"
				+ "  ├ GUARDAR: Guardas el progreso del juego.\n"
				+ "  ├ TEMA <TEMA>: Cambia el esquema de color de la interfaz.\n"
				+ "  ├ CREDITOS: Muestra información del juego y del creador.\n"
				+ "  └ VERSION: Muestra la versión actual del juego.";

		juego.outputTexto(mensaje, Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  CREDITOS
	 *  
	 *  
	 */
	private void comandoCreditos() {
		String mensaje = "MORGATH © 2024\n"
				+ "Desarrollado por: ANDREU GARRIGA CENDÁN\n"
				+ "Programado en: Java\n"
				+ "Fuente utilizada: Flexi IBM VGA True (www.1001fonts.com)";

		juego.outputTexto(mensaje, Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  TOC-TOC
	 *  
	 *  
	 */
	private void comandoToc(String arg) {
		String mensaje = "";
		String[] respuesta = {"¿Si?", "Adelante", "¿Quién es?", "¿Penny?"}; 

		if(arg == null) {
			mensaje = "Mmm...";

		} else if(arg.equalsIgnoreCase("TOC")) {
			mensaje = respuesta[Aleatorio.Int(0, respuesta.length - 1)];

		} else {
			mensaje = "¿En serio? Toc " + arg + "?";
		}

		juego.outputTexto(mensaje, Fuente.fuenteBase);

		System.out.println(arg);

	}

	/*
	 * 
	 * 
	 *  VERSION
	 *  
	 *  
	 */
	private void comandoVersion() {
		juego.outputTexto("Estás jugando MORGATH (v.1.0.6).", Fuente.fuenteBase);
	}

	/*
	 * 
	 * 
	 *  TEMA
	 *  
	 *  
	 */
	private void comandoTema(String arg) {
		StringBuffer mensaje = new StringBuffer();

		if(arg == null) {
			mensaje.append("Para cambiar el tema debes usar el comando %c[comando]TEMA <TEMA>%/c.\n")
			.append("Los temas disponibles son:\n")
			.append("\tTEMA 1: OSCURO\n")
			.append("\tTEMA 2: CLARO\n")
			.append("\tTEMA 3: AGUA\n")
			.append("\tTEMA 4: BRONCE\n")
			.append("\tTEMA 5: BOSQUE\n")
			.append("\tTEMA 6: LAVA\n")
			.append("\tTEMA 7: HIELO\n")
			.append("\tTEMA 8: VINTAGE\n")
			.append("\tTEMA 9: CLÁSICO");

		} else {
			boolean temaValido = true;

			switch(arg) {
			case "OSCURO", "1":
				Config.temaActual = Config.TEMA_1;
			break;
			case "CLARO", "2":
				Config.temaActual = Config.TEMA_2;
			break;
			case "AGUA", "3":
				Config.temaActual = Config.TEMA_3;
			break;
			case "BRONCE", "4":
				Config.temaActual = Config.TEMA_4;
			break;
			case "BOSQUE", "5":
				Config.temaActual = Config.TEMA_5;
			break;
			case "LAVA", "6":
				Config.temaActual = Config.TEMA_6;
			break;
			case "HIELO", "7":
				Config.temaActual = Config.TEMA_7;
			break;
			case "VINTAGE", "8":
				Config.temaActual = Config.TEMA_8;
			break;
			case "CLASICO", "9":
				Config.temaActual = Config.TEMA_9;
			break;
			default:
				mensaje.append("No reconozco el tema '" + arg + "'.");
				temaValido = false;
				break;
			}

			if(temaValido) {
				mensaje.append("%icon[paint-roller] Se ha cambiado el tema a ");
				if(arg.equalsIgnoreCase("oscuro") || arg.equalsIgnoreCase("1")) mensaje.append("%c[comando]TEMA 1: OSCURO%/c.");
				else if(arg.equalsIgnoreCase("claro") || arg.equalsIgnoreCase("2")) mensaje.append("%c[comando]TEMA 2: CLARO%/c.");
				else if(arg.equalsIgnoreCase("agua") || arg.equalsIgnoreCase("3")) mensaje.append("%c[comando]TEMA 3: AGUA%/c.");
				else if(arg.equalsIgnoreCase("bronce") || arg.equalsIgnoreCase("4")) mensaje.append("%c[comando]TEMA 4: BRONCE%/c.");
				else if(arg.equalsIgnoreCase("bosque") || arg.equalsIgnoreCase("5")) mensaje.append("%c[comando]TEMA 5: BOSQUE%/c.");
				else if(arg.equalsIgnoreCase("lava") || arg.equalsIgnoreCase("6")) mensaje.append("%c[comando]TEMA 6: LAVA%/c.");
				else if(arg.equalsIgnoreCase("hielo") || arg.equalsIgnoreCase("7")) mensaje.append("%c[comando]TEMA 7: HIELO%/c.");
				else if(arg.equalsIgnoreCase("vintage") || arg.equalsIgnoreCase("8")) mensaje.append("%c[comando]TEMA 8: VINTAGE%/c.");
				else if(arg.equalsIgnoreCase("clasico") || arg.equalsIgnoreCase("9")) mensaje.append("%c[comando]TEMA 9: CLÁSICO%/c.");
				actualizarTemaInterfaz();
			}
		}

		juego.outputTexto(mensaje.toString(), Fuente.fuenteBase);
	}

	public void actualizarTemaInterfaz() {
		Tema tema = Config.temaActual;

		juego.panelPrincipal.setBackground(tema.getColorPrincipal());
		juego.panelSuperior.setBackground(tema.getColorPrincipal());
		juego.panelCentral.setBackground(tema.getColorPrincipal());
		juego.outputTexto.setBackground(tema.getColorPrincipal());
		juego.panelInferior.setBackground(tema.getColorPrincipal());
		juego.inputTexto.setBackground(tema.getColorPrincipal());
		juego.inputTexto.setForeground(tema.getColorSecundario());	
		juego.outputTexto.setForeground(tema.getColorSecundario());
		juego.labelCursor.setForeground(tema.getColorSecundario());
		juego.labelUbicacion.setForeground(tema.getColorEnfasis());
		juego.labelPuntuacion.setForeground(tema.getColorEnfasis());

		juego.barraScrollPersonalizada.setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createDecreaseButton(int orientation) {
				return new JButton() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 7651203160930268408L;

					@Override
					public Dimension getPreferredSize() {
						return new Dimension(0, 0);
					}
				};
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return new JButton() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 8708726999982587006L;

					@Override
					public Dimension getPreferredSize() {
						return new Dimension(0, 0);
					}
				};
			}

			@Override
			protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
				g.setColor(tema.getColorPrincipal());  // Cambiar el color de la barra de desplazamiento
				g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
				g.setColor(tema.getColorSecundario());  // Cambiar el color del pulgar de la barra de desplazamiento
				g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
			}
		});

	}

}

