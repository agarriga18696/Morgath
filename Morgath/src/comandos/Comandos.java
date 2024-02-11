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
import objetos.Objeto_Comun;
import objetos.Objeto_Contenedor;
import objetos.Objeto_Dinero;
import personajes.Jugador;
import personajes.ListaEnemigos;
import personajes.PNJ;
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
	private PNJ ultimoPersonajeHablado;

	private int contadorEspaciosVacios = 0;

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
		LEVANTARSE("INCORPORARSE"),
		IR("AVANZAR", "DIRIGIRSE", "CAMINAR"),
		VOLVER("ATRAS"),
		ENTRAR,
		SALIR,

		// EXPLORACIÓN
		LUGAR("SITIO", "HABITACION"),
		EXPLORAR,
		MIRAR,
		BUSCAR,
		COGER,
		SOLTAR("TIRAR"),

		// INVENTARIO
		GUARDAR("METER", "INTRODUCIR", "INSERTAR"),
		SACAR("QUITAR"),
		DESTRUIR,
		INVENTARIO("I"),

		// MISIONES
		MISION("M", "OBJETIVO", "COMETIDO"),
		DIARIO("D", "MISIONES"),

		// SOCIAL
		HABLAR,
		PREGUNTAR,
		INTIMIDAR,
		PERSUADIR,
		HALAGAR,
		SOBORNAR,
		TRASFONDO,
		COMERCIAR,
		ADIOS,

		// JUEGO
		TERMINAR,
		REINICIAR,
		AYUDA("A"),
		CREDITOS("CREADOR", "JUEGO"),
		TOC,
		VERSION,
		TEMA;

		private final String[] atajo;

		// Constructor.
		ListaComandos(String... atajo){
			this.atajo = atajo;
		}

		// Getter.
		public String[] getAtajo() {
			return atajo;
		}

		// Método para obtener el atajo del comando.
		public static ListaComandos obtenerAtajo(String atajo) {
			for(ListaComandos comando : values()) {
				if(comando.name().equalsIgnoreCase(atajo) || Arrays.asList(comando.atajo).contains(atajo.toUpperCase())) {
					return comando;
				}
			}

			throw new IllegalArgumentException("Error, atajo " + atajo + " no encontrado.");
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
		setComandoActivado(ListaComandos.MISION, true);
		setComandoActivado(ListaComandos.DIARIO, true);
		setComandoActivado(ListaComandos.INVENTARIO, true);
		setComandoActivado(ListaComandos.TERMINAR, true);
		setComandoActivado(ListaComandos.REINICIAR, true);
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
		for (ListaComandos comandoLista : ListaComandos.values()) {
			setComandoActivado(comandoLista, false);
		}

		// Activa solo los comandos especificados.
		for (ListaComandos comandoJugador : comandos) {
			setComandoActivado(comandoJugador, true);
		}
	}

	// Desactivar comandos sociales (excepto HABLAR).
	private void desactivarComandosSociales() {
		setComandoActivado(ListaComandos.PREGUNTAR, false);
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

		if (activado) {
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
		String comandoNormalizado = NormalizarCadena.quitarAcentos(comando).toUpperCase();

		// Verificar que el comando no esté vacío después de quitar los espacios.
		if (!(comandoNormalizado.isEmpty() || comandoNormalizado.isBlank()) && comandoNormalizado != null) {
			String[] partesComando = comandoNormalizado.split("\\s+");
			String[] partesComandoDetallado = comandoNormalizado.split("\\s+", 2);

			String accion = partesComando[0];
			String argumento = partesComando.length > 1 ? partesComando[1] : null;
			String modificador = partesComando.length > 2 ? partesComando[2] : null;

			String argumentoDetallado = partesComandoDetallado.length > 1 ? partesComandoDetallado[1] : null;

			// Comprobar que el comando exista en la lista enum.
			ListaComandos comandoEnum = obtenerComandoEnum(accion);
			if (comandoEnum != null && esComandoActivado(comandoEnum)) {
				switch (ListaComandos.obtenerAtajo(accion)) {
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
				case GUARDAR:
					comandoGuardar(argumentoDetallado);
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
					comandoHablar(argumento);
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
					juego.outputTexto("No conozco el comando '" + comandoNormalizado + "'.");
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
					juego.outputTexto("No conozco el comando '" + comandoNormalizado + "'.");

				} else if(!jugador.isDePie()) {
					juego.outputTexto("No puedes hacer eso mientras estás en el suelo.");

				} else {
					if (comandoEnum != null && !esComandoActivado(comandoEnum)) {
						StringBuilder mensaje = new StringBuilder();
						ListaComandos accionJugador = ListaComandos.obtenerAtajo(accion);
						if(accionJugador.equals(ListaComandos.LEVANTARSE)) {
							mensaje.append(jugador.isDePie() ? "Ya estás de pie." : "Ahora no puedes levantarte.");
						} else if (accionJugador.equals(ListaComandos.IR)) {
							mensaje.append("En este momento no puedes ir a ningún sitio.");
						} else if(accionJugador.equals(ListaComandos.VOLVER)){
							mensaje.append("En estos momentos no puedes volver a ningún lugar.");
						} else if(accionJugador.equals(ListaComandos.ENTRAR)) {
							mensaje.append("Ahora mismo no puedes entrar a ninguna habitación");
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
						} else if(accionJugador.equals(ListaComandos.GUARDAR)){
							mensaje.append("En estos momentos no puedes guardar ningún objeto a un contendor.");
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
						} else if(accionJugador.equals(ListaComandos.TEMA)){
							mensaje.append("En este momento no puedes cambiar el tema de la interfaz.");
						} else {
							mensaje.append("En este momento no puedes usar ese comando");
						}

						juego.outputTexto(mensaje.toString());
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
				juego.outputTexto(mensajeArray[Aleatorio.Int(0, mensajeArray.length - 1)]);
				if(contadorEspaciosVacios > Aleatorio.Int(5, 7)) {
					contadorEspaciosVacios = 0;
				}
			} else {
				juego.outputTexto("¿Perdón?");
			}
		}
	}

	// Método para obtener el comando de la lista de enum de comandos.
	private ListaComandos obtenerComandoEnum(String accion) {
		for (ListaComandos comando : ListaComandos.values()) {
			if (comando.name().equalsIgnoreCase(accion) || Arrays.asList(comando.atajo).contains(accion.toUpperCase())) {
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

		juego.outputTexto(mensaje.toString());
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
		Habitacion nuevaHabitacion = null;
		StringBuilder mensaje = new StringBuilder();
		boolean teMueves = false, teMuevesVerticalmente = false;
		Direccion direccionPermitida = null, argDireccion = null;

		// Comprobar que el argumento coincide con una dirección válida.
		for(Direccion direccion : Direccion.values()) {
			if(arg != null && !arg.isEmpty() && arg.equalsIgnoreCase(direccion.name())) {
				argDireccion = direccion;
				break;
			}
		}

		// Comprobar que la dirección introducida existe.
		if(argDireccion != null && Direccion.obtenerAtajo(argDireccion.name()) != null) {

			nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, argDireccion);
			// Verificar que sea de tipo EXTERIOR.
			if(!habitacionActual.getTipo().equals(Habitacion.Tipo.INTERIOR) && nuevaHabitacion.getTipo().equals(Habitacion.Tipo.EXTERIOR)) {
				teMueves = true;
				direccionPermitida = argDireccion;

			} else if(habitacionActual.getTipo().equals(Habitacion.Tipo.INTERIOR) &&
					(nuevaHabitacion.getTipo().equals(Habitacion.Tipo.SUPERIOR) ||
							nuevaHabitacion.getTipo().equals(Habitacion.Tipo.INFERIOR))) {
				teMueves = true;
				teMuevesVerticalmente = true;
				direccionPermitida = argDireccion;

			} else if((habitacionActual.getTipo().equals(Habitacion.Tipo.SUPERIOR) || 
					habitacionActual.getTipo().equals(Habitacion.Tipo.INFERIOR)) && 
					nuevaHabitacion.getTipo().equals(Habitacion.Tipo.INTERIOR)) {
				teMueves = true;
				teMuevesVerticalmente = true;
				direccionPermitida = argDireccion;

			} else {
				if(nuevaHabitacion.getTipo().equals(Habitacion.Tipo.INTERIOR)) {
					juego.outputTexto("Para acceder a un interior utiliza el comando 'ENTRAR [UBICACIÓN]'.");
					return;

				} else if(nuevaHabitacion.getTipo().equals(Habitacion.Tipo.EXTERIOR)) {
					juego.outputTexto("Para salir al exterior utiliza el comando 'SALIR'.");
					return;
				}
			}

			// Actualizar la ubicación del jugador y mostrar mensaje.
			if(teMueves && nuevaHabitacion != null && nuevaHabitacion != habitacionActual) {
				jugador.setUbicacion(nuevaHabitacion);
				if(!teMuevesVerticalmente) {
					mensaje.append("Te mueves en dirección " + direccionPermitida.name().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				} else {
					mensaje.append("Te diriges hacia " + direccionPermitida.name().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				}

				// Mostrar descripción de la habitación solamente cuando es la primera vez que la visitas.
				if(!nuevaHabitacion.isVisitada()) {
					mensaje.append("\n" + nuevaHabitacion.getDescripcion());
				}

				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
				// Guardar dirección para el comando 'VOLVER'.
				ultimaDireccionUsada = direccionPermitida;
				// Marcar la habitación como visitada.
				nuevaHabitacion.setVisitada(true);

			} else if(!teMueves) {
				mensaje.append("No reconozco la dirección '" + arg + "'.");
			} else {
				mensaje.append("No puedes ir en esa dirección desde aquí.");
			}

		} else {
			mensaje.append("¿En qué dirección quieres ir?");
		}

		juego.outputTexto(mensaje.toString());

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
				if(ultimaDireccionUsada.equals(Direccion.ARRIBA) || ultimaDireccionUsada.equals(Direccion.ABAJO)) {
					mensaje.append("Regresas hacia " + ultimaDireccionUsada.opuesta().toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				} else {
					mensaje.append("Regresas en dirección " + ultimaDireccionUsada.opuesta().toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				}
				// Mostrar descripción de la habitación solamente cuando es la primera vez que la visitas.
				if(!nuevaHabitacion.isVisitada()) {
					mensaje.append("\n" + nuevaHabitacion.getDescripcion());
				}
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
				// Actualizar ultima direccion usadao.
				ultimaDireccionUsada = ultimaDireccionUsada.opuesta();
				// Marcar la habitación como visitada.
				nuevaHabitacion.setVisitada(true);

			} else {
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, ultimaDireccionUsada);
				jugador.setUbicacion(nuevaHabitacion);
				if(ultimaDireccionUsada.equals(Direccion.ARRIBA) || ultimaDireccionUsada.equals(Direccion.ABAJO)) {
					mensaje.append("Vuelves hacia " + ultimaDireccionUsada.toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				} else {
					mensaje.append("Vuelves en dirección " + ultimaDireccionUsada.toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre());
				}
				// Mostrar descripción de la habitación solamente cuando es la primera vez que la visitas.
				if(!nuevaHabitacion.isVisitada()) {
					mensaje.append("\n" + nuevaHabitacion.getDescripcion());
				}
				// Actualizar ultima direccion usadao.
				ultimaDireccionUsada = ultimaDireccionUsada.opuesta();
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
				// Marcar la habitación como visitada.
				nuevaHabitacion.setVisitada(true);
			}

		} else {
			mensaje.append("No puedes retroceder sin explorar nuevas ubicaciones previamente.");
		}

		juego.outputTexto(mensaje.toString());

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

		if (arg != null && habitacionActual != null) {
			Habitacion nuevaHabitacion = null;
			Direccion nuevaDireccion = null;

			for(Direccion direccion : Direccion.values()) {
				nuevaDireccion = direccion;
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, nuevaDireccion);
				if(nuevaDireccion != null && nuevaHabitacion != null && nuevaHabitacion != habitacionActual
						&& nuevaHabitacion.getTipo().equals(Tipo.INTERIOR)) {
					break;
				}
			}

			if(nuevaHabitacion != null) {
				StringBuilder nombreNuevaHabitacion = new StringBuilder(NormalizarCadena.quitarAcentos(nuevaHabitacion.getNombre()).toUpperCase());
				StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());

				if(argNormalizado.toString().equalsIgnoreCase(nombreNuevaHabitacion.toString()) && nuevaHabitacion.getTipo().equals(Tipo.INTERIOR)) {
					// Comparar los nombres normalizados
					jugador.setUbicacion(nuevaHabitacion);
					mensaje.append("Entras en " + nuevaHabitacion.getNombre() + ".\n");

					if(!nuevaHabitacion.isVisitada()) {
						mensaje.append("\n" + nuevaHabitacion.getDescripcion());
						nuevaHabitacion.setVisitada(true);
					}

					juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
					ultimaDireccionUsada = nuevaDireccion; // Guardar última dirección para el comando 'SALIR'.

				} else {
					Direccion nuevaDireccion2 = null;
					Habitacion nuevaHabitacion2 = null;
					List<Habitacion> habitacionesDisponibles = new ArrayList<>();
					boolean existeHabitacion = false;

					// Recorremos las direcciones para encontrar las habitaciones disponibles
					for(Direccion direccion : Direccion.values()) {
						nuevaDireccion2 = direccion;
						nuevaHabitacion2 = obtenerHabitacionEnDireccion(habitacionActual, nuevaDireccion2);
						if(nuevaDireccion2 != null && nuevaHabitacion2 != null && nuevaHabitacion2 != habitacionActual
								&& !nuevaHabitacion2.getTipo().equals(Tipo.INTERIOR) 
								&& !nuevaHabitacion2.getTipo().equals(Tipo.EXTERIOR)) {
							existeHabitacion = true;
							habitacionesDisponibles.add(nuevaHabitacion2);
						}
					}

					if(existeHabitacion) {

						for(Habitacion habitacion : habitacionesDisponibles) {
							String nombreHabitacionActualNormalizado = NormalizarCadena.quitarAcentos(habitacion.getNombre()).toUpperCase();

							if(argNormalizado.equals(nombreHabitacionActualNormalizado)) {
								if(habitacion.getTipo().equals(Tipo.SUPERIOR)) {
									mensaje.append("Para subir a '" + arg + "' usa el comando 'IR ARRIBA'.");
								} else if(habitacion.getTipo().equals(Tipo.INFERIOR)) {
									mensaje.append("Para bajar a '" + arg + "' usa el comando 'IR ABAJO'.");
								}
								break;
							}
						}
					} else {
						mensaje.append("No reconozco la habitación '" + arg + "'.");
					}
				}

			} else {
				mensaje.append("La habitación " + arg + " no se encuentra en este lugar.");
			}

		} else {
			mensaje.append("¿Dónde quieres entrar?");
		}

		juego.outputTexto(mensaje.toString());
	}


	/*
	 * 
	 * 
	 *  SALIR
	 *  
	 *  Te permite salir al exterior desde una habitación de tipo 'interior'.
	 *  
	 *  
	 */
	private void comandoSalir(String arg) {
		Habitacion habitacionActual = jugador.getUbicacion();
		StringBuilder mensaje = new StringBuilder();

		if (arg != null && habitacionActual != null) {
			Habitacion nuevaHabitacion = null;
			Direccion nuevaDireccion = null;

			for (Direccion direccion : Direccion.values()) {
				nuevaDireccion = direccion;
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, nuevaDireccion);
				if (nuevaDireccion != null && nuevaHabitacion != null && nuevaHabitacion != habitacionActual
						&& nuevaHabitacion.getTipo().equals(Tipo.EXTERIOR)) {
					break;
				}
			}

			if (nuevaHabitacion != null && habitacionActual.getTipo().equals(Habitacion.Tipo.INTERIOR)) {
				StringBuilder nombreNuevaHabitacion = new StringBuilder(NormalizarCadena.quitarAcentos(nuevaHabitacion.getNombre()).toUpperCase());
				StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());

				if (argNormalizado.toString().equalsIgnoreCase(nombreNuevaHabitacion.toString())) {
					jugador.setUbicacion(nuevaHabitacion);
					mensaje.append("Sales a " + nuevaHabitacion.getNombre() + ".\n");

					if (!nuevaHabitacion.isVisitada()) {
						mensaje.append("\n" + nuevaHabitacion.getDescripcion());
						nuevaHabitacion.setVisitada(true);
					}

					juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
					ultimaDireccionUsada = nuevaDireccion; // Guardar última dirección.

				} else {
					mensaje.append("No reconozco el lugar '" + arg + "'.");
				}

			} else {
				if(habitacionActual.getTipo().equals(Habitacion.Tipo.EXTERIOR)) {
					mensaje.append("No puedes salir cuando ya te encuentras en el exterior.");

				} else {
					mensaje.append("No puedes salir. No hay ninguna salida al exterior desde aquí.");
				}
			}

		} else {
			mensaje.append("¿Adónde quieres salir?");
		}

		juego.outputTexto(mensaje.toString());
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

		juego.outputTexto(habitacionActual.getDescripcion());
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

					mensaje.append("\n" + direccion + ": " + habitacionConectada.getNombre());
				}

			} else {
				mensaje.append("No encuentras ninguna ruta.");
			}
		} else {
			mensaje.append("No puedes explorar en este momento.");
		}

		juego.outputTexto(mensaje.toString());

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
		List<PNJ> pnjsEnHabitacion = habitacionActual.getPnjs();
		List<Enemigo> enemigosEnHabitacion = habitacionActual.getEnemigos();

		if (pnjsEnHabitacion != null && !pnjsEnHabitacion.isEmpty() || enemigosEnHabitacion != null && !enemigosEnHabitacion.isEmpty()) {
			boolean hayPnj = false;

			// Caso PNJ.
			if(pnjsEnHabitacion != null && !pnjsEnHabitacion.isEmpty()) {
				hayPnj = true;
				mensaje.append("Has visto " + (pnjsEnHabitacion.size() > 1 ? "varios personajes:" : "un personaje:"));
				for (PNJ pnj : pnjsEnHabitacion) {
					mensaje.append("\n- ").append(pnj.getNombre());
				}
			}

			// Caso Enemigo.
			if(enemigosEnHabitacion != null && !enemigosEnHabitacion.isEmpty()) {
				if(hayPnj) mensaje.append("\n");
				mensaje.append("Has encontrado " + (enemigosEnHabitacion.size() > 1 ? "varios enemigos:" : "un enemigo:"));
				for (Enemigo enemigo : enemigosEnHabitacion) {
					mensaje.append("\n- ").append(enemigo.getNombre());
				}
			}

		} else {
			juego.outputTexto("No ves a nadie en este lugar.");
		}

		juego.outputTexto(mensaje.toString());

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

		if (objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
			StringBuilder mensaje = new StringBuilder("Objetos del lugar:");

			for (Objeto objeto : objetosHabitacion) {
				mensaje.append("\n- ").append(objeto.getNombre()).append(": ").append(objeto.getDescripcion());
			}

			juego.outputTexto(mensaje.toString());

		} else {
			juego.outputTexto("No ves ningún objeto en este lugar.");
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
			if (objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
				// Comprobar si el jugador ya tiene un objeto contenedor en su inventario.
				boolean tieneContenedor = false;
				for (Objeto objetoJugador : inventarioJugador) {
					if (objetoJugador instanceof Objeto_Contenedor) {
						tieneContenedor = true;
						break;
					}
				}

				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase());

				if(arg != null) {
					StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg).toUpperCase());
					// Comprobar si el objeto está en la habitación.
					if (inventarioJugador.size() < jugador.getMaxObjetosInventario() && 
							argNormalizado.toString().equalsIgnoreCase(objetoNormalizado.toString())) {
						// Si el jugador ya tiene un objeto contenedor, no puede coger más objetos
						if (objeto instanceof Objeto_Contenedor && tieneContenedor) {
							mensaje.append("No has cogido '" + objeto.getNombre() + "' porque ya posees un objeto contenedor.");
						} else {
							jugador.agregarObjetoAlInventario(objeto);
							mensaje.append("Has cogido " + objeto.getNombre() + ".");
							// Eliminar el objeto de la habitación.
							iterador.remove();
						}
						encontrado = true;
						break;

					} else if (arg != null && argNormalizado.toString().equalsIgnoreCase("todo")) {
						// Si el jugador ya tiene un objeto contenedor, no puede coger más objetos
						if (objeto instanceof Objeto_Contenedor && tieneContenedor) {
							mensaje.append("No puedes coger '" + objeto.getNombre() + "' si ya posees otro objeto contenedor.");
						} else {
							jugador.agregarObjetoAlInventario(objeto);
							mensaje.append("Has cogido " + objeto.getNombre() + ".\n");
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
		if (!encontrado && arg != null) {
			mensaje.append("El objeto '" + arg + "' no se encuentra en este lugar.");
		} else if (inventarioJugador.size() >= jugador.getMaxObjetosInventario()) {
			mensaje.append("\nInventario lleno. Deshazte de algún objeto para poder guardar otro.");
		} else if (arg == null && objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
			mensaje.append("¿Qué objeto quieres coger?");
		}

		juego.outputTexto(mensaje.toString());
	}



	//////////////////////
	// 					//
	//	  INVENTARIO	//
	//					//
	//////////////////////

	/*
	 * 
	 * 
	 *  GUARDAR
	 *  
	 *  
	 */
	private void comandoGuardar(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar que el inventario del jugador no esté vacío.
		if (arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Dividir el 'arg' en dos partes: objeto y contenedor.
			String[] partes = arg.split(" EN ", 2);
			if (partes.length == 2) {
				String nombreObjeto = partes[0].trim();
				String nombreContenedor = partes[1].trim();

				// Buscar el objeto en el inventario del jugador.
				Objeto objetoAGuardar = null;
				for (Objeto objeto : inventarioJugador) {
					if (objeto.getNombre().equalsIgnoreCase(nombreObjeto)) {
						objetoAGuardar = objeto;
						break;
					}
				}

				if (objetoAGuardar != null) {
					// Buscar el contenedor en el inventario del jugador.
					Objeto_Contenedor contenedor = null;
					for (Objeto objeto : inventarioJugador) {
						if (objeto instanceof Objeto_Contenedor && objeto.getNombre().equalsIgnoreCase(nombreContenedor)) {
							contenedor = (Objeto_Contenedor) objeto;
							break;
						}
					}

					if (contenedor != null) {
						// Verificar si el objeto a guardar es el mismo que el contenedor.
						if (objetoAGuardar.equals(contenedor)) {
							mensaje.append("No puedes guardar un objeto dentro de sí mismo.");
						} else {
							// Comprobar que el contenedor tiene espacio suficiente para guardar más objetos.
							if (contenedor.getObjetosContenidos().size() < contenedor.getMaxObjetosContenidos()) {
								// Guardar el objeto en el contenedor.
								contenedor.getObjetosContenidos().add(objetoAGuardar);
								// Eliminar el objeto del inventario.
								inventarioJugador.remove(objetoAGuardar);
								mensaje.append("'" + objetoAGuardar.getNombre() + "' guardado en '" + contenedor.getNombre() + "'.");
							} else {
								mensaje.append("El contenedor '" + contenedor.getNombre() + "' está lleno.");
							}
						}
					} else {
						mensaje.append("El contenedor '" + nombreContenedor + "' no está en tu inventario.");
					}
				} else {
					mensaje.append("El objeto '" + nombreObjeto + "' no está en tu inventario.");
				}
			} else {
				mensaje.append("Para guardar un objeto en un contenedor usa el comando 'GUARDAR [OBJETO] EN [CONTENEDOR]'.");
			}
		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres guardar y dónde?");
			} else {
				mensaje.append("Tu inventario está vacío.");
			}
		}

		juego.outputTexto(mensaje.toString());
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
		if (arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Dividir el 'arg' en dos partes: objeto y contenedor.
			String[] partes = arg.split(" DE ", 2);
			if (partes.length == 2 && !partes[0].endsWith(" ")) {
				String nombreObjeto = partes[0].trim();
				String nombreContenedor = partes[1].trim();

				// Buscar el contenedor en el inventario del jugador.
				Objeto_Contenedor contenedor = null;
				for (Objeto objeto : inventarioJugador) {
					if (objeto instanceof Objeto_Contenedor && objeto.getNombre().equalsIgnoreCase(nombreContenedor)) {
						contenedor = (Objeto_Contenedor) objeto;
						break;
					}
				}

				if (contenedor != null) {
					// Buscar el objeto dentro del contenedor.
					Objeto objetoASacar = null;
					for (Objeto obj : contenedor.getObjetosContenidos()) {
						if (obj.getNombre().equalsIgnoreCase(nombreObjeto)) {
							objetoASacar = obj;
							break;
						}
					}

					if (objetoASacar != null) {
						// Comprobar que el inventario del jugador tiene espacio suficiente para agregar el objeto sacado.
						if (inventarioJugador.size() < jugador.getMaxObjetosInventario()) {
							// Agregar el objeto al inventario del jugador.
							inventarioJugador.add(objetoASacar);
							// Eliminar el objeto del contenedor.
							contenedor.getObjetosContenidos().remove(objetoASacar);
							mensaje.append("Has sacado '" + objetoASacar.getNombre() + "' de '" + contenedor.getNombre() + "'.");
						} else {
							mensaje.append("No tienes espacio suficiente en tu inventario para sacar '" + objetoASacar.getNombre() + "'.");
						}
					} else {
						mensaje.append("El objeto '" + nombreObjeto + "' no está dentro de '" + contenedor.getNombre() + "'.");
					}
				} else {
					mensaje.append("El contenedor '" + nombreContenedor + "' no está en tu inventario.");
				}
			} else {
				mensaje.append("Para sacar un objeto de un contenedor usa el comando 'SACAR [OBJETO] DE [CONTENEDOR]'.");
			}
		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres sacar y de dónde?");
			} else {
				mensaje.append("Tu inventario está vacío.");
			}
		}

		juego.outputTexto(mensaje.toString());
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
				// Normalizar nombre del objeto.
				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
				StringBuilder argNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(arg));
				// Comprobar si el objeto está en el inventario del jugador.
				if(arg != null && argNormalizado.toString().equalsIgnoreCase(objetoNormalizado.toString())) {
					// Quitar objeto de tu inventario.
					inventarioJugador.remove(objeto);
					mensaje.append("Has tirado '" + objeto.getNombre() + "' al suelo.");
					// Añadir objeto al suelo de la habitación (inventario).
					objetosHabitacion.add(objeto);
					encontrado = true;
					break;
				}
			}

			if (!encontrado && arg != null) {
				mensaje.append("El objeto '" + arg + "' no se encuentra en tu inventario.");
			}

		} else {
			if(arg == null) {
				mensaje.append("¿Qué objeto quieres soltar?");
			} else {
				mensaje.append("Tu inventario está vacío. No hay nada que soltar.");
			}
		}

		juego.outputTexto(mensaje.toString());
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

	    if (arg != null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
	        Iterator<Objeto> iterator = inventarioJugador.iterator();
	        boolean objetoEncontrado = false;
	        String argNormalizado = NormalizarCadena.quitarAcentos(arg).toUpperCase(); // Normalizar el argumento

	        while (iterator.hasNext()) {
	            Objeto objeto = iterator.next();
	            String nombreObjetoNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre()).toUpperCase(); // Normalizar el nombre del objeto

	            if (argNormalizado.equals(nombreObjetoNormalizado)) { // Comparar los nombres normalizados
	                // Si es un objeto de misión, no se puede destruir
	                if ((objeto instanceof Objeto_Comun && ((Objeto_Comun) objeto).isObjetoDeMision()) 
	                		|| (objeto instanceof Objeto_Arma && ((Objeto_Arma) objeto).isObjetoDeMision())) {
	                    mensaje.append("No puedes destruir objetos de misión.");
	                } else {
	                    iterator.remove(); // Eliminar el objeto de manera segura
	                    mensaje.append("Has destruido el objeto '" + objeto.getNombre() + "'.");
	                }
	                objetoEncontrado = true;
	                break;
	            }
	        }

	        if (!objetoEncontrado) {
	            mensaje.append("No tienes el objeto '" + arg + "' en tu inventario.");
	        }
	    } else {
	        if(arg == null) {
	        	mensaje.append("¿Qué objeto quieres destruir?");
	        } else {
	        	mensaje.append("No llevas ningún objeto para destruir.");
	        }
	    }

	    juego.outputTexto(mensaje.toString());
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
		if (inventarioJugador != null && !inventarioJugador.isEmpty()) {
			mensaje.append("Estás portando estos objetos:");
			// Mostrar información según tipo de objeto.
			for (Objeto objeto : inventarioJugador) {
				// OBJETO ARMA
				if (objeto instanceof Objeto_Arma) {
					Objeto_Arma objetoArma = (Objeto_Arma) objeto;
					mensaje.append("\n- ").append(objetoArma.getNombre()).append(": ").append(objetoArma.getDescripcion())
					.append(objetoArma.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO COMÚN
				if (objeto instanceof Objeto_Comun) {
					Objeto_Comun objetoComun = (Objeto_Comun) objeto;
					mensaje.append("\n- ").append(objetoComun.getNombre()).append(": ").append(objetoComun.getDescripcion())
					.append(objetoComun.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO CONTENEDOR
				if (objeto instanceof Objeto_Contenedor) {
					Objeto_Contenedor objetoContenedor = (Objeto_Contenedor) objeto;
					mensaje.append("\n- ").append(objetoContenedor.getNombre()).append(": ").append(objetoContenedor.getDescripcion());

					if(objetoContenedor.getObjetosContenidos() != null && !objetoContenedor.getObjetosContenidos().isEmpty()) {
						int tamanoLista = objetoContenedor.getObjetosContenidos().size(); 

						for(int i = 0; i < tamanoLista; i++) {
							Objeto objetoContenido = objetoContenedor.getObjetosContenidos().get(i);

							if(tamanoLista > 1) {
								if(i == (tamanoLista - 1)) {
									mensaje.append("\n   └ ").append(objetoContenido.getNombre());
								} else {
									mensaje.append("\n   ├ ").append(objetoContenido.getNombre());
								}
							} else {
								mensaje.append("\n   └ ").append(objetoContenido.getNombre());
							}
						}
					} else {
						mensaje.append("\n  - Contenido: (vacío)");
					}

				}

				// OBJETO DINERO
				if (objeto instanceof Objeto_Dinero) {
					Objeto_Dinero objetoDinero = (Objeto_Dinero) objeto;
					mensaje.append("\n- ").append(objetoDinero.getNombre()).append(": ").append(objetoDinero.getDescripcion());
				}
			}
			juego.outputTexto(mensaje.toString());

		} else {
			juego.outputTexto("No llevas ningún objeto.");
		}
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
		juego.outputTexto("\nMisión actual: " + juego.misionActiva.getNombre() + "\nObjetivo: " + juego.misionActiva.getObjetivo());
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

		if(misionesCompletadas != null && !misionesCompletadas.isEmpty()) {
			mensaje.append("Diario de misiones:\n");
			// Mostrar la misión activa la primera de todas.
			mensaje.append("- ").append(juego.misionActiva.getNombre()).append(" (En curso)\n");
			for(Mision mision : misionesCompletadas) {
				if(mision.isCompletada() && !mision.isActivada()) {
					mensaje.append("- ").append(mision.getNombre()).append(" (Completada)\n");
				}
			}
		} else {
			mensaje.append("Todavía no has completado ninguna misión.");
		}

		juego.outputTexto(mensaje.toString());

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
		PNJ personaje = null;

		if (arg != null && !arg.isEmpty()) {
			String argNormalizado = NormalizarCadena.quitarAcentos(arg);
			boolean hayPnj = false;

			// Buscar al personaje en la habitación.
			for (PNJ pnj : habitacionActual.getPnjs()) {
				if (pnj.getNombre().equalsIgnoreCase(argNormalizado)) {
					hayPnj = true;
					personaje = pnj;
					ultimoPersonajeHablado = pnj;
					break;
				}
			}

			// Buscar si el nombre coincide con un enemigo
			boolean esEnemigo = false;
			boolean enemigoEncontrado = false;
			for (Enemigo enemigo : habitacionActual.getEnemigos()) {
				if (NormalizarCadena.quitarAcentos(enemigo.getNombre()).equalsIgnoreCase(argNormalizado)) {
					esEnemigo = true;
					enemigoEncontrado = true;
					break;
				}
			}

			// Comprobar si existe un personaje con una conversación disponible.
			if (hayPnj && personaje != null && !esEnemigo && personaje.obtenerConversacion() != null) {
				desactivarComandosExcepto(
						ListaComandos.PREGUNTAR,
						ListaComandos.INTIMIDAR,
						ListaComandos.PERSUADIR,
						ListaComandos.HALAGAR,
						ListaComandos.SOBORNAR,
						ListaComandos.TRASFONDO,
						ListaComandos.COMERCIAR,
						ListaComandos.ADIOS
						);
				mensaje.append(personaje.saludoNormal());
				mensaje.append(personaje.obtenerConversacion());

			} else {
				if (!hayPnj && !enemigoEncontrado) {
					mensaje.append("No se ha encontrado a '" + arg + "'.");

				} else if (!hayPnj && esEnemigo && enemigoEncontrado) {
					mensaje.append("No puedes hablar con un enemigo.");

				} else if(!hayPnj) {
					mensaje.append("'" + personaje.getNombre() + "' no tiene nada que decirte.");
					return;
				}
			}
		} else {
			mensaje.append("¿Con quién quieres hablar?");
		}

		juego.outputTexto(mensaje.toString());
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
		if(ultimoPersonajeHablado != null) {
			juego.outputTexto(ultimoPersonajeHablado.getNombre() + ": " + "¡Hasta pronto!");
		}

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
			juego.outputTexto("¡Hasta la próxima, aventurero!");
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
		jugador.setNombre(null);
		jugador.setUbicacion(Juego.ubicacionInicial);
		jugador.setPuntos(0);
		jugador.setInventario(null);
		jugador.setMaxObjetosInventario(6);
		jugador.setVidas(4);
		jugador.setInmortal(false);
		jugador.diario.clear();

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

		juego.outputTexto("El juego se ha reiniciado.");

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
				+ "  ├ IR [DIRECCION]: Te mueves en la dirección indicada.\n"
				+ "  └ VOLVER: Retrocedes a la ubicación anterior.\n"
				+ "- EXPLORACIÓN:\n"
				+ "  ├ LUGAR: Muestra la descripción de tu ubicación actual.\n"
				+ "  ├ EXPLORAR: Exploras el lugar en busca de caminos a seguir.\n"
				+ "  ├ MIRAR: Observas el alrededor para detectar personajes o enemigos.\n"
				+ "  ├ BUSCAR: Encuentra objetos en tu ubicación.\n"
				+ "  ├ COGER [OBJETO]: Coges un objeto de la habitación actual y lo añades a tu inventario.\n"
				+ "  ├ SOLTAR [OBJETO]: Tiras un objeto de tu inventario al suelo de la habitación actual.\n"
				+ "  ├ GUARDAR [OBJETO] [CONTENEDOR]: Guardas un objeto de tu inventario dentro de un contenedor.\n"
				+ "  ├ SACAR [OBJETO] [CONTENEDOR]: Sacas un objeto de un contenedor y lo devuelves al inventario.\n"
				+ "  ├ DESTRUIR [OBJETO]: Destruye un objeto de tu inventario permanentemente.\n"
				+ "  ├ INVENTARIO: Muestra los objetos que estás portando.\n"
				+ "  ├ MISION: Muestra la misión actual.\n"
				+ "  ├ DIARIO: Muestra las misiones que has completado.\n"
				+ "  └ LEVANTARSE: Te levantas del suelo si estás tumbado.\n"
				+ "- JUEGO:\n"
				+ "  ├ TERMINAR: Terminas la partida.\n"
				+ "  ├ REINICIAR: Reinicias el juego.\n"
				+ "  ├ TEMA [TEMA]: Cambia el esquema de color de la interfaz.\n"
				+ "  ├ CREDITOS: Muestra información del juego y del creador.\n"
				+ "  └ VERSION: Muestra la versión actual del juego.";

		juego.outputTexto(mensaje);
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

		juego.outputTexto(mensaje);
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
			mensaje = "¿En serio? ¿Toc " + arg + "?";
		}

		juego.outputTexto(mensaje);

	}

	/*
	 * 
	 * 
	 *  VERSION
	 *  
	 *  
	 */
	private void comandoVersion() {
		juego.outputTexto("Estás jugando MORGATH (v.1.0.6).");
	}

	/*
	 * 
	 * 
	 *  TEMA
	 *  
	 *  
	 */
	private void comandoTema(String arg) {
		String mensaje = "";

		if(arg == null) {
			mensaje = "Para cambiar el tema debes usar el comando 'TEMA [tema]', por ejemplo 'TEMA 2'.";
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
			case "FANTASIA", "5":
				Config.temaActual = Config.TEMA_5;
			break;
			case "OLIVA", "6":
				Config.temaActual = Config.TEMA_6;
			break;
			case "ORO", "7":
				Config.temaActual = Config.TEMA_7;
			break;
			case "TINTO", "8":
				Config.temaActual = Config.TEMA_8;
			break;
			case "HIELO", "9":
				Config.temaActual = Config.TEMA_9;
			break;
			case "CLASICO", "10":
				Config.temaActual = Config.TEMA_10;
			break;
			default:
				mensaje = "No reconozco el tema '" + arg + "'.\n"
						+ "Los temas disponibles son:\n"
						+ "- TEMA 1: OSCURO\n"
						+ "- TEMA 2: CLARO\n"
						+ "- TEMA 3: AGUA\n"
						+ "- TEMA 4: BRONCE\n"
						+ "- TEMA 5: FANTASÍA\n"
						+ "- TEMA 6: OLIVA\n"
						+ "- TEMA 7: ORO\n"
						+ "- TEMA 8: TINTO\n"
						+ "- TEMA 9: HIELO\n"
						+ "- TEMA 10: CLÁSICO\n";
				temaValido = false;
				break;
			}

			if(temaValido) {
				if(arg.equalsIgnoreCase("oscuro") || arg.equalsIgnoreCase("1")) mensaje = "Se ha cambiado el tema a 'TEMA 1: OSCURO'.";
				else if(arg.equalsIgnoreCase("claro") || arg.equalsIgnoreCase("2")) mensaje = "Se ha cambiado el tema a 'TEMA 2: CLARO'.";
				else if(arg.equalsIgnoreCase("agua") || arg.equalsIgnoreCase("3")) mensaje = "Se ha cambiado el tema a 'TEMA 3: AGUA'.";
				else if(arg.equalsIgnoreCase("bronce") || arg.equalsIgnoreCase("4")) mensaje = "Se ha cambiado el tema a 'TEMA 4: BRONCE'.";
				else if(arg.equalsIgnoreCase("fantasia") || arg.equalsIgnoreCase("5")) mensaje = "Se ha cambiado el tema a 'TEMA 5: FANTASÍA'.";
				else if(arg.equalsIgnoreCase("oliva") || arg.equalsIgnoreCase("6")) mensaje = "Se ha cambiado el tema a 'TEMA 6: OLIVA'.";
				else if(arg.equalsIgnoreCase("oro") || arg.equalsIgnoreCase("7")) mensaje = "Se ha cambiado el tema a 'TEMA 7: ORO'.";
				else if(arg.equalsIgnoreCase("tinto") || arg.equalsIgnoreCase("8")) mensaje = "Se ha cambiado el tema a 'TEMA 8: TINTO'.";
				else if(arg.equalsIgnoreCase("hielo") || arg.equalsIgnoreCase("9")) mensaje = "Se ha cambiado el tema a 'TEMA 9: HIELO";
				else if(arg.equalsIgnoreCase("clasico") || arg.equalsIgnoreCase("10")) mensaje = "Se ha cambiado el tema a 'TEMA 10: CLÁSICO'.";
				actualizarTemaInterfaz();
			}
		}

		juego.outputTexto(mensaje);
	}

	public void actualizarTemaInterfaz() {
		Tema tema = Config.temaActual;

		juego.panelPrincipal.setBackground(tema.colorPrincipal);
		juego.panelSuperior.setBackground(tema.colorPrincipal);
		juego.panelCentral.setBackground(tema.colorPrincipal);
		juego.outputTexto.setBackground(tema.colorPrincipal);
		juego.panelInferior.setBackground(tema.colorPrincipal);
		juego.inputTexto.setBackground(tema.colorPrincipal);
		juego.inputTexto.setForeground(tema.colorSecundario);	
		juego.outputTexto.setForeground(tema.colorSecundario);
		juego.labelCursor.setForeground(tema.colorSecundario);
		juego.labelUbicacion.setForeground(tema.colorEnfasis);
		juego.labelPuntuacion.setForeground(tema.colorEnfasis);

		juego.labelObjeto.setForeground(tema.colorEnfasis);

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
				g.setColor(tema.colorPrincipal);  // Cambiar el color de la barra de desplazamiento
				g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
				g.setColor(tema.colorSecundario);  // Cambiar el color del pulgar de la barra de desplazamiento
				g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
			}
		});

	}	

}

