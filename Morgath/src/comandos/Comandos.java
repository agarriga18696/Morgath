package comandos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
import localizaciones.ConectorHabitaciones;
import localizaciones.ConectorHabitaciones.Direccion;
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
	private List<String> comandos = new ArrayList<>();
	private List<String> comandosActivados = new ArrayList<>();
	private Jugador jugador;
	private Juego juego;
	private ConectorHabitaciones mapa;
	private Direccion ultimaDireccionUsada;

	private int contadorEspaciosVacios = 0;

	// Constructor.
	public Comandos(Jugador jugador, Juego juego, ConectorHabitaciones mapa) {
		this.jugador = jugador;
		this.juego = juego;
		this.mapa = mapa;

		inicializarComandos();
	}

	// Método para obtener la lista de comandos.
	public List<String> getComandos() {
		return comandos;
	}

	// Método para obtener la lista de comandos activados.
	public List<String> getComandosActivados() {
		return comandosActivados;
	}

	// Lista de comandos.
	public enum ListaComandos {

		EMPEZAR,
		IR(""),
		VOLVER("ATRAS"),
		MIRAR,
		BUSCAR,
		COGER,
		SOLTAR("TIRAR"),
		GUARDAR,
		SACAR,
		DESTRUIR,
		INVENTARIO("I"),
		MISION("M"),
		DIARIO("D"),
		EXPLORAR,
		TERMINAR,
		REINICIAR,
		AYUDA("A"),
		CREDITOS,
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
			comandos.add(comando.name());
			comandos.addAll(Arrays.asList(comando.getAtajo()));
		}
		// Agregar comandos iniciales a la lista de comandos activados.
		setComandoActivado(ListaComandos.EMPEZAR, true);
		setComandoActivado(ListaComandos.TERMINAR, true);
		setComandoActivado(ListaComandos.AYUDA, true);
		setComandoActivado(ListaComandos.TEMA, true);
	}

	// Activar o desactivar un comando.
	public void setComandoActivado(ListaComandos comando, boolean activado) {
		String nombreComando = comando.name();

		if (activado) {
			comandosActivados.add(nombreComando);
		} else {
			comandosActivados.remove(nombreComando);
		}
	}

	// Verificar si un comando está activado.
	public boolean esComandoActivado(ListaComandos comando) {
		return comandosActivados.contains(comando.name());
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

	public void ejecutarComando(String c) {
		// Normalizar comando.
		c = c.trim().toUpperCase();
		String comando = NormalizarCadena.quitarAcentos(c);

		// Verificar que el comando no esté vacío después de quitar los espacios.
		if (!(comando.isEmpty() || comando.isBlank()) && comando != null) {
			String[] partesComando = comando.split("\\s+");
			String accion = partesComando[0];
			String argumento = partesComando.length > 1 ? partesComando[1] : null;
			String argumento_2 = partesComando.length > 2 ? partesComando[2] : null;

			// Comprobar que el comando exista en la lista enum.
			ListaComandos comandoEnum = obtenerComandoEnum(accion);
			if (comandoEnum != null && esComandoActivado(comandoEnum)) {
				switch (ListaComandos.obtenerAtajo(accion)) {
				case EMPEZAR:
					comandoEmpezar();
					break;
				case IR:
					comandoIr(argumento);
					break;
				case VOLVER:
					comandoVolver();
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
					comandoCoger(argumento);
					break;
				case SOLTAR:
					comandoSoltar(argumento);
					break;	
				case GUARDAR:
					comandoGuardar(argumento, argumento_2);
					break;
				case SACAR:
					comandoSacar(argumento, argumento_2);
					break;
				case DESTRUIR:
					comandoDestruir(argumento);
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
					juego.outputTexto("No conozco el comando '" + comando + "'.");
					break;
				}

			} else {
				juego.outputTexto("No conozco el comando '" + comando + "'.");
			}

		} else {
			contadorEspaciosVacios++;
			System.out.println(contadorEspaciosVacios);
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
	 */
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
	}



	/*
	 * 
	 * 
	 *  MÉTODOS DE DIRECCIÓN Y MOVIMIENTO DEL PERSONAJE
	 *  
	 *  
	 */

	// Método para obtener la ubicación actual del jugador.
	private Habitacion obtenerHabitacionActual() {
		return jugador.getUbicacion();
	}

	// Método para moverse entre habitaciones.
	private Habitacion obtenerHabitacionEnDireccion(Habitacion habitacionActual, ConectorHabitaciones.Direccion direccion) {
		return mapa.moverJugador(habitacionActual, direccion);
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
		boolean teMueves = false;
		Direccion direccionPermitida = null;

		if(arg != null && !arg.isEmpty()) {
			switch(Direccion.obtenerAtajo(arg)) {
			case NORTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.NORTE);
				teMueves = true;
				direccionPermitida = Direccion.NORTE;
				break;
			case NORESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.NORESTE);
				teMueves = true;
				direccionPermitida = Direccion.NORESTE;
				break;
			case NOROESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.NOROESTE);
				teMueves = true;
				direccionPermitida = Direccion.NOROESTE;
				break;
			case SUR:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.SUR);
				teMueves = true;
				direccionPermitida = Direccion.SUR;
				break;
			case SUDESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.SUDESTE);
				teMueves = true;
				direccionPermitida = Direccion.SUDESTE;
				break;
			case SUDOESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.SUDOESTE);
				teMueves = true;
				direccionPermitida = Direccion.SUDOESTE;
				break;
			case ESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.ESTE);
				teMueves = true;
				direccionPermitida = Direccion.ESTE;
				break;
			case OESTE:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.OESTE);
				teMueves = true;
				direccionPermitida = Direccion.OESTE;
				break;
			case ARRIBA:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.ARRIBA);
				teMueves = true;
				direccionPermitida = Direccion.ARRIBA;
				break;
			case ABAJO:
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Direccion.ABAJO);
				teMueves = true;
				direccionPermitida = Direccion.ABAJO;
				break;
			default:
				break;
			}


			// Actualizar la ubicación del jugador y mostrar mensaje.
			if(teMueves && nuevaHabitacion != null && nuevaHabitacion != habitacionActual) {
				jugador.setUbicacion(nuevaHabitacion);
				mensaje.append("Te mueves en dirección " + direccionPermitida.name().toLowerCase() + ".\n" + nuevaHabitacion.getNombre() + 
						"\n" + nuevaHabitacion.getDescripcion());
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
				// Guardar dirección para el comando 'VOLVER'.
				ultimaDireccionUsada = direccionPermitida;
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
				mensaje.append("Regresas en dirección " + ultimaDireccionUsada.opuesta().toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre() + 
						"\n" + nuevaHabitacion.getDescripcion());
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());

			} else {
				nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, ultimaDireccionUsada);
				jugador.setUbicacion(nuevaHabitacion);
				mensaje.append("Vuelves en dirección " + ultimaDireccionUsada.toString().toLowerCase() + ".\n" + nuevaHabitacion.getNombre() + 
						"\n" + nuevaHabitacion.getDescripcion());
				juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
			}

		} else {
			mensaje.append("No puedes retroceder sin explorar nuevas ubicaciones previamente.");
		}

		juego.outputTexto(mensaje.toString());

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
					mensaje.append("Exploras el lugar y encuentras una única salida:");
				} else {
					mensaje.append("Exploras el lugar y encuentras varias salidas:");
				}

				for(Map.Entry<Direccion, Habitacion> entry : salidas.entrySet()) {
					Direccion direccion = entry.getKey();
					Habitacion habitacionConectada = entry.getValue();

					mensaje.append("\n" + direccion + ": " + habitacionConectada.getNombre());
				}

			} else {
				mensaje.append("No encuentras ninguna salida.");
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
		boolean encontrado = false;

		// Comprobar que la lista de objetos de la habitación no sea null.
		if (objetosHabitacion != null && !objetosHabitacion.isEmpty()) {

			// Coger solo un objeto.
			for (Objeto objeto : objetosHabitacion) {
				// Normalizar nombre del objeto.
				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
				// Comprobar si el objeto está en la habitación.
				if (inventarioJugador.size() < jugador.getMaxObjetosInventario() && arg != null && arg.equalsIgnoreCase(objetoNormalizado.toString())) {
					// Comprobar que el jugador no tenga ya 1 objeto contenedor (solo puedes tener 1).
					/*for(Objeto objetoJugador : inventarioJugador) {
						if(objeto instanceof Objeto_Contenedor && objetoJugador instanceof Objeto_Contenedor) {
							mensaje.append("No has cogido '" + objeto.getNombre() + "' por que ya posees un objeto contenedor.");
							juego.outputTexto(mensaje.toString());
							return;
						}
					}*/
					// Añadir objeto al inventario del jugador.
					jugador.agregarObjetoAlInventario(objeto);
					mensaje.append("Has cogido " + objeto.getNombre() + ".");
					// Eliminar el objeto de la habitación.
					objetosHabitacion.remove(objeto);
					encontrado = true;
					break;
				}
			}

			// Coger todos los objetos.
			if(arg != null && arg.equalsIgnoreCase("todo")) {
				for(Objeto objeto : objetosHabitacion) {
					if(inventarioJugador.size() < jugador.getMaxObjetosInventario()) {
						// Comprobar que el jugador no tenga ya 1 objeto contenedor (solo puedes tener 1).
						/*for(Objeto objetoJugador : inventarioJugador) {
							if(objeto instanceof Objeto_Contenedor && objetoJugador instanceof Objeto_Contenedor) {
								mensaje.append("No puedes coger '" + objeto.getNombre() + "' si ya posees otro objeto contenedor.");
								juego.outputTexto(mensaje.toString());
								return;
							}
						}*/

						jugador.agregarObjetoAlInventario(objeto);
						mensaje.append("Has cogido " + objeto.getNombre() + ".\n");
						encontrado = true;
					}
				}
				// Eliminar todos los objetos de la habitación.
				objetosHabitacion.clear();;
			}

			if(inventarioJugador.size() >= jugador.getMaxObjetosInventario()) {
				mensaje.append("\nInventario lleno. Deshazte de algún objeto para poder guardar otro.");
			}

			if(arg == null && objetosHabitacion != null && !objetosHabitacion.isEmpty()) {
				mensaje.append("¿Qué objeto quieres coger?");
			}

			if (!encontrado && arg != null) {
				mensaje.append("El objeto '" + arg + "' no se encuentra en este lugar.");
			}
		} else {
			mensaje.append("No hay objetos en este sitio.");
		}

		juego.outputTexto(mensaje.toString());
	}

	/*
	 * 
	 * 
	 *  GUARDAR
	 *  
	 *  
	 */
	private void comandoGuardar(String... args) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar que el jugador tiene objetos en el inventario.
		if (inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Comprobar que los argumentos no sean nulos.
			if (args[0] != null && args[1] != null) {
				// Buscar el objeto a guardar en el inventario del jugador.
				Objeto objetoAGuardar = null;
				for (Objeto objeto : inventarioJugador) {
					StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
					if (args[0].equalsIgnoreCase(objetoNormalizado.toString())) {
						objetoAGuardar = objeto;
						break;
					}
				}

				// Comprobar si se encontró el objeto a guardar.
				if (objetoAGuardar != null) {
					// Buscar el contenedor en el inventario del jugador.
					for (Objeto objetoContenedor : inventarioJugador) {
						if (objetoContenedor instanceof Objeto_Contenedor &&
								objetoContenedor.getNombre().equalsIgnoreCase(args[1])) {
							Objeto_Contenedor contenedor = (Objeto_Contenedor) objetoContenedor;

							// Comprobar si el objeto a guardar es el mismo que el contenedor.
							if (objetoAGuardar.equals(contenedor)) {
								mensaje.append("No puedes guardar un objeto dentro de sí mismo.");
								juego.outputTexto(mensaje.toString());
								return;
							}

							// Comprobar si el contenedor tiene espacio suficiente.
							if (contenedor.getObjetosContenidos().size() < contenedor.getMaxObjetosContenidos()) {
								// Comprobar si el objeto a guardar es un contenedor.
								if (!(objetoAGuardar instanceof Objeto_Contenedor) && args[1] != null) {
									switch(contenedor.getCapacidad()) {
									case BAJA:
										if(objetoAGuardar instanceof Objeto_Arma || objetoAGuardar instanceof Objeto_Comun) {
											String[] mensajeAleatorio = {
													"¿No te das cuenta que '" + objetoAGuardar.getNombre() + "' no cabe en '"
															+ contenedor.getNombre() + "'? No eres Sally Bobbins.",
															"Eso no cabe ahí...",
															"¿En serio creíste que '" + objetoAGuardar.getNombre() + "' cabía en '"
																	+ contenedor.getNombre() + "'?"};

											mensaje.append(mensajeAleatorio[Aleatorio.Int(0, mensajeAleatorio.length - 1)]);
										} else {
											// Guardar objeto en el contenedor.
											contenedor.getObjetosContenidos().add(objetoAGuardar);
											// Eliminar el objeto del inventario, ya que realmente lo duplicas.
											inventarioJugador.remove(objetoAGuardar);
											mensaje.append("'" + objetoAGuardar.getNombre() + "' guardado en '"
													+ contenedor.getNombre() + "'.");
										}
										break;
									case MEDIA:
										if(objetoAGuardar instanceof Objeto_Arma) {
											mensaje.append("'" + objetoAGuardar.getNombre() + "' no cabe en '"
													+ contenedor.getNombre() + "', no es tan grande como pensabas.");
										} else {
											// Guardar objeto en el contenedor.
											contenedor.getObjetosContenidos().add(objetoAGuardar);
											// Eliminar el objeto del inventario, ya que realmente lo duplicas.
											inventarioJugador.remove(objetoAGuardar);
											mensaje.append("'" + objetoAGuardar.getNombre() + "' guardado en '"
													+ contenedor.getNombre() + "'.");
										}
										break;
									case ALTA:
										// Guardar objeto en el contenedor.
										contenedor.getObjetosContenidos().add(objetoAGuardar);
										// Eliminar el objeto del inventario, ya que realmente lo duplicas.
										inventarioJugador.remove(objetoAGuardar);
										mensaje.append("'" + objetoAGuardar.getNombre() + "' guardado en '"
												+ contenedor.getNombre() + "'.");
										break;
									default:
										break;
									}

									juego.outputTexto(mensaje.toString());
									return;
								} else {
									mensaje.append("No puedes guardar un contenedor dentro de otro, ¿esperabas "
											+ "crear un agujero negro o algo así?");
									juego.outputTexto(mensaje.toString());
									return;
								}
							} else {
								mensaje.append("El contenedor '" + contenedor.getNombre() + "' está lleno.");
								juego.outputTexto(mensaje.toString());
								return;
							}
						}
					}

					// Comprobar que el arg[1] (contenedor) no sea un objeto-no-contenedor.
					boolean objetoNoContenedor = false;

					for(Objeto objeto : inventarioJugador) {
						if(!(objeto instanceof Objeto_Contenedor) && objeto.getNombre().equalsIgnoreCase(args[1])) {
							objetoNoContenedor = true;
						}
					}

					if(objetoNoContenedor) {
						mensaje.append("¿Cómo pretendes guardar '" + args[0] + "' en '" + args[1] + "'?");
					}

					// Comprobar que el contenedor seleccionado esté en tu inventario o exista en el juego.
					boolean contenedorEnInventario = false;
					boolean contenedorEnJuego = false;

					for(Objeto objetoContenedor : inventarioJugador) {
						if (objetoContenedor instanceof Objeto_Contenedor && objetoContenedor.getNombre().equalsIgnoreCase(args[1])) {
							contenedorEnInventario = true;
							break;
						}
					}

					for(Objeto objetoContenedor: ListaObjetos.listaTodosLosObjetos) {
						if (objetoContenedor instanceof Objeto_Contenedor && objetoContenedor.getNombre().equalsIgnoreCase(args[1])) {
							contenedorEnJuego = true;
							break;
						}
					}

					if (!contenedorEnInventario && contenedorEnJuego && !objetoNoContenedor) {
						mensaje.append("No dispones del contenedor '" + args[1] + "'.");
					} else if (!contenedorEnJuego && !objetoNoContenedor) {
						mensaje.append("No reconozco el contenedor '" + args[1] + "'.");
					}

				} else {

					// Comprobar que el objeto exista en la lista de todos los objetos del juego.
					boolean objetoEnJuego = false;
					for (Objeto objeto : ListaObjetos.listaTodosLosObjetos) {
						if (objeto.getNombre().equalsIgnoreCase(args[0])) {
							objetoEnJuego = true;
							break;
						}
					}

					if(!objetoEnJuego) {
						mensaje.append("No reconozco el objeto '" + args[0] + "'.");
					} else {
						mensaje.append("¿Dónde quieres guardar el objeto '" + args[0] + "'?");
					}		
				}

			} else if(args[0] != null && args[1] == null) {

				// Comprobar que el objeto exista en la lista de todos los objetos del juego.
				boolean objetoEnJuego = false;
				for (Objeto objeto : ListaObjetos.listaTodosLosObjetos) {
					if (objeto.getNombre().equalsIgnoreCase(args[0])) {
						objetoEnJuego = true;
						break;
					}
				}

				if(!objetoEnJuego) {
					mensaje.append("El objeto '" + args[0] + "' no se encuentra en este mundo.");
				} else {
					mensaje.append("¿Dónde quieres almacenar el objeto '" + args[0] + "'?");
				}

			} else {
				mensaje.append("¿Qué objeto quieres guardar y dónde?");
			}
		} else {
			mensaje.append("No llevas ningún objeto para guardar.");
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
	private void comandoSacar(String... args) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar que el jugador tiene objetos en el inventario.
		if (inventarioJugador != null && !inventarioJugador.isEmpty()) {
			// Comprobar que los argumentos no sean nulos.
			if (args[0] != null) {
				// Normalizar el nombre del objeto a sacar.
				String nombreObjeto = NormalizarCadena.quitarAcentos(args[0]);

				// Comprobar si el objeto a sacar se encuentra dentro de un contenedor en el inventario del jugador.
				Objeto_Contenedor objetoContenedor = null;
				for (Objeto objeto : inventarioJugador) {
					String objetoNormalizado = NormalizarCadena.quitarAcentos(objeto.getNombre());
					if (objeto instanceof Objeto_Contenedor && args[1].equalsIgnoreCase(objetoNormalizado)) {
						Objeto_Contenedor contenedor = (Objeto_Contenedor) objeto;
						// Utilizar el nombre normalizado para la comparación.
						if (contenedor.getObjetosContenidos().stream().anyMatch(obj -> NormalizarCadena.quitarAcentos(obj.getNombre()).equalsIgnoreCase(nombreObjeto))) {
							objetoContenedor = contenedor;
							break;
						}
					}
				}

				if (objetoContenedor != null) {
					Objeto objetoASacar = null;
					for (Objeto obj : objetoContenedor.getObjetosContenidos()) {
						if (NormalizarCadena.quitarAcentos(obj.getNombre()).equalsIgnoreCase(nombreObjeto)) {
							objetoASacar = obj;
							break;
						}
					}

					// Comprobar si encontraste el objeto a sacar.
					if (objetoASacar != null) {
						// Comprobar si tienes espacio en el inventario para guardar el objeto sacado.
						if (inventarioJugador.size() < jugador.getMaxObjetosInventario()) {
							// Añadir objeto al inventario.
							inventarioJugador.add(objetoASacar);
							// Eliminar objeto de dentro del contenedor.
							objetoContenedor.getObjetosContenidos().remove(objetoASacar);
							mensaje.append("Has sacado '" + objetoASacar.getNombre() + "' de '" + objetoContenedor.getNombre() + "'.");
						} else {
							mensaje.append("No tienes espacio suficiente en tu inventario para sacar '" + objetoASacar.getNombre() + "'.");
						}
					} else {
						mensaje.append("'" + args[0] + "' no está dentro de '" + objetoContenedor.getNombre() + "'.");
					}
				} else {
					// Comprobar que el objeto a sacar exista en el inventario del jugador.
					Objeto objetoASacar = null;
					for (Objeto obj : inventarioJugador) {
						if (NormalizarCadena.quitarAcentos(obj.getNombre()).equalsIgnoreCase(nombreObjeto)) {
							objetoASacar = obj;
							break;
						}
					}

					if (objetoASacar != null) {
						// Si el objeto a sacar no es un contenedor, mostrar mensaje de error.
						if (objetoASacar instanceof Objeto_Contenedor) {
							mensaje.append("No puedes sacar un contenedor de dentro de otro, no quieras creerte más inteligente que yo.");
						}else {
							// Comprobar que el objeto exista en la lista de todos los objetos del juego.
							boolean objetoEnJuego = false;
							for (Objeto objeto : ListaObjetos.listaTodosLosObjetos) {
								if (objeto.getNombre().equalsIgnoreCase(args[1])) {
									objetoEnJuego = true;
									break;
								}
							}

							if(!objetoEnJuego) {
								mensaje.append("No reconozco el objeto '" + args[1] + "'.");
							} else {
								mensaje.append("¿Dónde quieres guardar '" + args[0] + "'?");
							}
						}
					} else {
						if(args[0] != null && args[1] == null) {
							mensaje.append("'" + args[0] + "' no se encuentra en tu inventario.");
						} else {
							mensaje.append("'" + args[0] + "' no se encuentra en '" + args[1] + "'.");
						}
					}
				}
			} else {
				mensaje.append("¿Qué objeto quieres sacar?");
			}
		} else {
			mensaje.append("Tu inventario está vacío.");
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
		if(inventarioJugador != null && !inventarioJugador.isEmpty()) {
			boolean encontrado = false;

			for(Objeto objeto : inventarioJugador) {
				// Normalizar nombre del objeto.
				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
				// Comprobar si el objeto está en el inventario del jugador.
				if(arg != null && arg.equalsIgnoreCase(objetoNormalizado.toString())) {
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
			mensaje.append("Tu inventario está vacío. No hay nada que soltar.");
		}

		if(arg == null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			mensaje.append("¿Qué objeto quieres soltar?");
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
		// Destruye un objeto de tu inventario para siempre.
		// No se podrán destruir objetos de misión.
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		if(inventarioJugador != null && !inventarioJugador.isEmpty()) {
			for(Objeto objeto : inventarioJugador) {
				// Normalizar nombre del objeto.
				StringBuilder objetoNormalizado = new StringBuilder(NormalizarCadena.quitarAcentos(objeto.getNombre()));
				if(arg != null && arg.equalsIgnoreCase(objetoNormalizado.toString())){
					// Si es un objeto de misión denegar su destrucción.
					if(objeto.isObjetoDeMision()) {
						mensaje.append("No puedes destruir objetos de misión.");
						break;

					} else {
						inventarioJugador.remove(objeto);
						mensaje.append("Has destruido el objeto '" + objeto.getNombre() + "'.");
						break;
					}
				}
			}

		} else {
			mensaje.append("No portas ningún objeto.");
		}

		if(arg == null && inventarioJugador != null && !inventarioJugador.isEmpty()) {
			mensaje.append("¿Qué objeto quieres destruir?");
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
					mensaje.append("\n  - ").append(objetoArma.getNombre()).append(": ").append(objetoArma.getDescripcion())
					.append(objetoArma.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO COMÚN
				if (objeto instanceof Objeto_Comun) {
					Objeto_Comun objetoComun = (Objeto_Comun) objeto;
					mensaje.append("\n  - ").append(objetoComun.getNombre()).append(": ").append(objetoComun.getDescripcion())
					.append(objetoComun.isObjetoDeMision() ? " (Objeto de misión)" : "");
				}

				// OBJETO CONTENEDOR
				if (objeto instanceof Objeto_Contenedor) {
					Objeto_Contenedor objetoContenedor = (Objeto_Contenedor) objeto;
					mensaje.append("\n  - ").append(objetoContenedor.getNombre()).append(": ").append(objetoContenedor.getDescripcion())
					.append(objetoContenedor.isObjetoDeMision() ? " (Objeto de misión)" : "");;

					if(objetoContenedor.getObjetosContenidos() != null && !objetoContenedor.getObjetosContenidos().isEmpty()) {
						mensaje.append("\n    - Contenido: ");
						for(Objeto objetoContenido : objetoContenedor.getObjetosContenidos()) {
							mensaje.append("\n      - ").append(objetoContenido.getNombre());
						}
					} else {
						mensaje.append("\n    - Contenido: (vacío)");
					}

				}

				// OBJETO DINERO
				if (objeto instanceof Objeto_Dinero) {
					Objeto_Dinero objetoDinero = (Objeto_Dinero) objeto;
					mensaje.append("\n  - ").append(objetoDinero.getNombre()).append(": ").append(objetoDinero.getDescripcion());
				}
			}
			juego.outputTexto(mensaje.toString());

		} else {
			juego.outputTexto("No llevas ningún objeto.");
		}
	}

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

	/*
	 * 
	 * 
	 *  TERMINAR
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
		Juego.habitaciones = new ConectorHabitaciones();
		Juego.ubicacionInicial = Juego.habitaciones.obtenerHabitacionInicial();

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
		String mensaje = "LISTA DE COMANDOS:\n"
				+ "- DIRECCIÓN\n"
				+ "  - IR [DIRECCION]: Te mueves en la dirección indicada.\n"
				+ "  - VOLVER: Retrocedes a la ubicación anterior.\n"
				+ "- EXPLORACIÓN\n"
				+ "  - EXPLORAR: Exploras el lugar en busca de caminos a seguir.\n"
				+ "  - MIRAR: Observas el alrededor para detectar personajes o enemigos.\n"
				+ "  - BUSCAR: Encuentra objetos en tu ubicación.\n"
				+ "  - COGER [OBJETO]: Coges un objeto de la habitación actual y lo añades a tu inventario.\n"
				+ "  - SOLTAR [OBJETO]: Tiras un objeto de tu inventario al suelo de la habitación actual.\n"
				+ "  - GUARDAR [OBJETO][CONTENEDOR]: Guardas un objeto de tu inventario dentro de un contenedor.\n"
				+ "  - SACAR [OBJETO][CONTENEDOR]: Sacas un objeto de un contenedor y lo devuelves al inventario.\n"
				+ "  - DESTRUIR [OBJETO]: Destruye un objeto de tu inventario permanentemente.\n"
				+ "  - INVENTARIO: Muestra los objetos que estás portando.\n"
				+ "  - MISION: Muestra la misión actual.\n"
				+ "  - DIARIO: Muestra las misiones que has completado.\n"
				+ "- JUEGO\n"
				+ "  - TERMINAR: Terminas la partida.\n"
				+ "  - REINICIAR: Reinicias el juego.\n"
				+ "  - TEMA [TEMA]: Cambia el esquema de color de la interfaz.\n"
				+ "  - CREDITOS: Muestra información del juego y del creador.\n"
				+ "  - VERSION: Muestra la versión actual del juego.";

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
			case "MENTA", "6":
				Config.temaActual = Config.TEMA_6;
			break;
			case "ORO", "7":
				Config.temaActual = Config.TEMA_7;
			break;
			case "MATRIX", "8":
				Config.temaActual = Config.TEMA_8;
			break;
			case "CLASICO", "9":
				Config.temaActual = Config.TEMA_9;
			break;
			default:
				mensaje = "No reconozco el tema '" + arg + "'.\n"
						+ "Los temas disponibles son:\n"
						+ "- TEMA 1: OSCURO\n"
						+ "- TEMA 2: CLARO\n"
						+ "- TEMA 3: AGUA\n"
						+ "- TEMA 4: BRONCE\n"
						+ "- TEMA 5: FANTASÍA\n"
						+ "- TEMA 6: MENTA\n"
						+ "- TEMA 7: ORO\n"
						+ "- TEMA 8: MATRIX\n"
						+ "- TEMA 9: CLÁSICO\n";
				temaValido = false;
				break;
			}

			if(temaValido) {
				if(arg.equalsIgnoreCase("oscuro") || arg.equalsIgnoreCase("1")) mensaje = "Se ha cambiado el tema a 'TEMA 1: OSCURO'.";
				else if(arg.equalsIgnoreCase("claro") || arg.equalsIgnoreCase("2")) mensaje = "Se ha cambiado el tema a 'TEMA 2: CLARO'.";
				else if(arg.equalsIgnoreCase("agua") || arg.equalsIgnoreCase("3")) mensaje = "Se ha cambiado el tema a 'TEMA 3: AGUA'.";
				else if(arg.equalsIgnoreCase("bronce") || arg.equalsIgnoreCase("4")) mensaje = "Se ha cambiado el tema a 'TEMA 4: BRONCE'.";
				else if(arg.equalsIgnoreCase("fantasia") || arg.equalsIgnoreCase("5")) mensaje = "Se ha cambiado el tema a 'TEMA 5: FANTASÍA'.";
				else if(arg.equalsIgnoreCase("menta") || arg.equalsIgnoreCase("6")) mensaje = "Se ha cambiado el tema a 'TEMA 6: MENTA'.";
				else if(arg.equalsIgnoreCase("oro") || arg.equalsIgnoreCase("7")) mensaje = "Se ha cambiado el tema a 'TEMA 7: ORO'.";
				else if(arg.equalsIgnoreCase("matrix") || arg.equalsIgnoreCase("8")) mensaje = "Se ha cambiado el tema a 'TEMA 8: MATRIX'.";
				else if(arg.equalsIgnoreCase("clasico") || arg.equalsIgnoreCase("9")) mensaje = "Se ha cambiado el tema a 'TEMA 9: CLÁSICO'.";
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

