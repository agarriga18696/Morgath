package comandos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import configuracion.Config;
import configuracion.Tema;
import juego.Juego;
import juego.Jugador;
import localizaciones.Habitacion;
import localizaciones.Mapa;
import objetos.Objeto;
import utilidades.Aleatorio;

public class Comandos {

	// Atributos.
	private List<String> comandos = new ArrayList<>();
	private Jugador jugador;
	private Juego juego;
	private Mapa mapa;

	// Constructor.
	public Comandos(Jugador jugador, Juego juego, Mapa mapa) {

		this.jugador = jugador;
		this.juego = juego;
		this.mapa = mapa;

		inicializarComandos();

	}

	// Método para inicializar los comandos.
	private void inicializarComandos() {

		// Añadir todos los comandos de la lista enum a la lista de comandos.
		for(ListaComandos comando : ListaComandos.values()){
			comandos.add(comando.name());
			comandos.addAll(Arrays.asList(comando.getAtajo()));
		}

	}

	// Método para obtener la lista de comandos.
	public List<String> getComandos() {
		return comandos;
	}

	// Lista de comandos.
	public enum ListaComandos {

		NORTE("N"),
		SUR("S"),
		ESTE("E"),
		OESTE("O"),
		MIRAR,
		COGER,
		SOLTAR,
		INVENTARIO("I"),
		MISION("M"),
		TERMINAR("T"),
		AYUDA("A"),
		CREDITOS,
		TOC,
		JUEGO,
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

	// Método para validar los comandos.
	public void ejecutarComando(String comando) {
		// Eliminar espacios en blanco alrededor del comando.
		comando = comando.trim();

		// Verificar que el comando no esté vacío después de quitar los espacios.
		if (!comando.isEmpty() || !comando.isBlank()) {
			String[] partesComando = comando.split("\\s+");
			String verbo = partesComando[0];
			String argumento = partesComando.length > 1 ? partesComando[1] : null;

			// Comprobar que el comando exista en la lista enum.
			if (comandos.contains(verbo.toUpperCase())) {
				switch (ListaComandos.obtenerAtajo(verbo)) {
				case NORTE:
					comandoNorte();
					break;
				case SUR:
					comandoSur();
					break;
				case ESTE:
					comandoEste();
					break;
				case OESTE:
					comandoOeste();
					break;
				case MIRAR:
					comandoMirar();
					break;
				case COGER:
					comandoCoger(argumento);
					break;
				case SOLTAR:
					comandoSoltar(argumento);
					break;
				case INVENTARIO:
					comandoInventario();
					break;
				case MISION:
					comandoMision();
					break;
				case TERMINAR:
					comandoTerminar();
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
				case JUEGO:
					comandoJuego();
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
		}
	}

	// MOVIMIENTO
	// En la clase Juego
	private Habitacion obtenerHabitacionActualDelJugador() {
		return jugador.getUbicacion();
	}

	private Habitacion obtenerHabitacionEnDireccion(Habitacion habitacionActual, Mapa.Direccion direccion) {
		return mapa.moverse(habitacionActual, direccion);
	}

	// NORTE
	private void comandoNorte() {
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		Habitacion nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Mapa.Direccion.NORTE);

		// Actualizar la ubicación del jugador y mostrar mensaje
		if (nuevaHabitacion != habitacionActual) {
			jugador.setUbicacion(nuevaHabitacion);
			juego.outputTexto("Te mueves en dirección NORTE\n" + nuevaHabitacion.getNombre() + 
					"\n" + nuevaHabitacion.getDescripcion());
			juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
		} else {
			juego.outputTexto("No puedes ir al NORTE desde aquí.");
		}
	}

	// SUR
	private void comandoSur() {
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		Habitacion nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Mapa.Direccion.SUR);

		// Actualizar la ubicación del jugador y mostrar mensaje
		if (nuevaHabitacion != habitacionActual) {
			jugador.setUbicacion(nuevaHabitacion);
			juego.outputTexto("Te mueves en dirección SUR.\n" + nuevaHabitacion.getNombre() + 
					"\n" + nuevaHabitacion.getDescripcion());
			juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
		} else {
			juego.outputTexto("No puedes ir al SUR desde aquí.");
		}
	}

	// ESTE
	private void comandoEste() {
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		Habitacion nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Mapa.Direccion.ESTE);

		// Actualizar la ubicación del jugador y mostrar mensaje
		if (nuevaHabitacion != habitacionActual) {
			jugador.setUbicacion(nuevaHabitacion);
			juego.outputTexto("Te mueves en dirección ESTE.\n" + nuevaHabitacion.getNombre() + 
					"\n" + nuevaHabitacion.getDescripcion());
			juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
		} else {
			juego.outputTexto("No puedes ir al ESTE desde aquí.");
		}
	}

	// OESTE
	private void comandoOeste() {
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		Habitacion nuevaHabitacion = obtenerHabitacionEnDireccion(habitacionActual, Mapa.Direccion.OESTE);

		// Actualizar la ubicación del jugador y mostrar mensaje
		if (nuevaHabitacion != habitacionActual) {
			jugador.setUbicacion(nuevaHabitacion);
			juego.outputTexto("Te mueves en dirección OESTE.\n" + nuevaHabitacion.getNombre() + 
					"\n" + nuevaHabitacion.getDescripcion());
			juego.labelUbicacion.setText("UBICACIÓN: " + nuevaHabitacion.getNombre());
		} else {
			juego.outputTexto("No puedes ir al OESTE desde aquí.");
		}

	}

	// MIRAR
	private void comandoMirar() {
		// Comprueba si hay objetos en la habitación actual.
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		List<Objeto> objetosEnHabitacion = habitacionActual.getObjetos();

		if (objetosEnHabitacion != null && !objetosEnHabitacion.isEmpty()) {
			StringBuilder mensaje = new StringBuilder("Objetos en la habitación:");

			for (Objeto objeto : objetosEnHabitacion) {
				mensaje.append("\n- ").append(objeto.getNombre()).append(": ").append(objeto.getDescripcion());
			}

			juego.outputTexto(mensaje.toString());

		} else {
			juego.outputTexto("No hay objetos en la habitación.");
		}

	}

	// COGER
	private void comandoCoger(String arg) {
		StringBuilder mensaje = new StringBuilder();
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		List<Objeto> objetosEnHabitacion = habitacionActual.getObjetos();

		// Comprobar que la lista de objetos de la habitación no sea null.
		if (objetosEnHabitacion != null && !objetosEnHabitacion.isEmpty()) {
			boolean encontrado = false;

			// Comprobar si el objeto está en la habitación.
			for (Objeto objeto : objetosEnHabitacion) {
				if (arg != null && arg.equalsIgnoreCase(objeto.getNombre())) {
					// Añadir objeto al inventario del jugador.
					jugador.agregarObjetoAlInventario(objeto);
					mensaje.append("Has cogido " + objeto.getNombre() + ".");
					// Eliminar objeto de la habitación (cogido).
					objetosEnHabitacion.remove(objeto);
					encontrado = true;
					break;
				}
			}

			if (!encontrado) {
				mensaje.append("El objeto '" + arg + "' no se encuentra en esta habitación.");
			}
		} else {
			mensaje.append("No hay objetos en esta habitación.");
		}

		juego.outputTexto(mensaje.toString());
	}

	// SOLTAR
	private void comandoSoltar(String arg) {
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();
		Habitacion habitacionActual = obtenerHabitacionActualDelJugador();
		List<Objeto> objetosEnHabitacion = habitacionActual.getObjetos();

		// Comprobar que el inventario no sea null.
		if(inventarioJugador != null && !inventarioJugador.isEmpty()) {
			boolean encontrado = false;

			for(Objeto objeto : inventarioJugador) {
				// Comprobar si el objeto está en el inventario del jugador.
				if(arg != null && arg.equalsIgnoreCase(objeto.getNombre())) {
					// Quitar objeto de tu inventario.
					inventarioJugador.remove(objeto);
					mensaje.append("Has tirado '" + objeto.getNombre() + "' al suelo.");
					// Añadir objeto al suelo de la habitación (inventario).
					objetosEnHabitacion.add(objeto);
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

	// INVENTARIO
	private void comandoInventario() {
		// Comprueba si hay objetos en la habitación actual.
		StringBuilder mensaje = new StringBuilder();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Comprobar si tienes objetos en el inventario.
		if (inventarioJugador != null && !inventarioJugador.isEmpty()) {
			mensaje.append("Estás portando estos objetos:");
			for (Objeto objeto : inventarioJugador) {
				mensaje.append("\n- ").append(objeto.getNombre()).append(": ").append(objeto.getDescripcion());
			}
			juego.outputTexto(mensaje.toString());

		} else {
			juego.outputTexto("Tu inventario está vacío.");
		}

	}

	// MISION
	private void comandoMision() {
		juego.outputTexto("\nMisión actual: " + juego.misionActiva.getNombre() + "\nObjetivo: " + juego.misionActiva.getObjetivo());
	}

	// TERMINAR
	private void comandoTerminar() {
		SwingUtilities.invokeLater(() -> {
			juego.outputTexto("¡Hasta la próxima, aventurero!");
			// Agregar un temporizador para dar tiempo a leer el mensaje antes de salir.
			Timer timer = new Timer(2000, (ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			timer.setRepeats(false); // Hacer que el temporizador solo se ejecute una vez
			timer.start();
		});
	}

	// AYUDA
	private void comandoAyuda() {
		String mensaje = "LISTA DE COMANDOS:\n"
				+ "- NORTE: Te mueves en dirección norte.\n"
				+ "- SUR: Te mueves en dirección sur.\n"
				+ "- ESTE: Te mueves en dirección este.\n"
				+ "- OESTE: Te mueves en dirección oeste.\n"
				+ "- MISION: Muestra la misión actual.\n"
				+ "- TERMINAR: Terminar la partida y salir del juego.\n"
				+ "- TEMA [tema]: Puedes alternar el color de la interfaz.\n"
				+ "- CREDITOS: Muestra información sobre el creador del juego.\n"
				+ "- JUEGO: Muestra la versión del juego.";

		juego.outputTexto(mensaje);
	}

	// CREDITOS
	private void comandoCreditos() {
		String mensaje = "(C) 2024 MORGATH I\n"
				+ "Desarrollado por: ANDREU GARRIGA CENDÁN\n"
				+ "Programado en: Java\n"
				+ "Fuente utilizada: Flexi IBM VGA True (www.1001fonts.com)";

		juego.outputTexto(mensaje);
	}

	// TOC-TOC
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

	// JUEGO
	private void comandoJuego() {
		juego.outputTexto("Estás jugando MORGATH I (v.1.0.3).");
	}

	// TEMA
	private void comandoTema(String arg) {
		String mensaje = "";

		if(arg == null) {
			mensaje = "Para cambiar el tema debes usar el comando 'TEMA [tema]', por ejemplo 'TEMA 2'.";
		} else {
			boolean temaValido = true;
			switch(arg.toUpperCase()) {
			case "OSCURO", "1":
				Config.temaActual = Config.TEMA_1;
				break;
			case "CLARO", "2":
				Config.temaActual = Config.TEMA_2;
				break;
			case "AQUA", "3":
				Config.temaActual = Config.TEMA_3;
				break;
			case "VINTAGE", "4":
				Config.temaActual = Config.TEMA_4;
				break;
			case "MATRIX", "5":
				Config.temaActual = Config.TEMA_5;
				break;
			case "CLASICO", "6":
				Config.temaActual = Config.TEMA_6;
				break;
			case "ORO", "7":
				Config.temaActual = Config.TEMA_7;
				break;
			case "FANTASIA", "8":
				Config.temaActual = Config.TEMA_8;
				break;
			default:
				mensaje = "No reconozco el tema '" + arg + "'.\n"
						+ "Los temas disponibles son:\n"
						+ "- TEMA 1: OSCURO\n"
						+ "- TEMA 2: CLARO\n"
						+ "- TEMA 3: AQUA\n"
						+ "- TEMA 4: VINTAGE\n"
						+ "- TEMA 5: MATRIX\n"
						+ "- TEMA 6: CLASICO\n"
						+ "- TEMA 7: ORO\n"
						+ "- TEMA 8: FANTASIA\n";
				temaValido = false;
				break;
			}

			if(temaValido) {
				mensaje = "Se ha cambiado el tema a 'TEMA " + arg + "'.";
				actualizarInterfazSegunTema();
			}
		}

		juego.outputTexto(mensaje);
	}

	public void actualizarInterfazSegunTema() {
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
	}

}
