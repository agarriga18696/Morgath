package comandos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import configuracion.Config;
import juego.Juego;
import juego.Jugador;
import misiones.Misiones;
import utilidades.Aleatorio;

public class Comandos {

	// Atributos.
	private List<String> comandos = new ArrayList<>();
	private Jugador jugador;
	private Juego juego;
	private Misiones misiones;

	// Constructor.
	public Comandos(Jugador jugador, Juego juego, Misiones misiones) {

		this.jugador = jugador;
		this.juego = juego;
		this.misiones = misiones;

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
		MISION("M"),
		TERMINAR("T"),
		AYUDA("A"),
		CREDITOS,
		TOC,
		JUEGO;

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
				default:
					juego.outputTexto("No conozco el comando '" + comando + "'.");
					break;
				}

			} else {
				juego.outputTexto("No conozco el comando '" + comando + "'.");
			}
		}
	}

	// NORTE
	private void comandoNorte() {
		juego.outputTexto("Te mueves en dirección norte.");
	}

	// SUR
	private void comandoSur() {
		juego.outputTexto("Te mueves en dirección sur.");
	}

	// ESTE
	private void comandoEste() {
		juego.outputTexto("Te mueves en dirección este.");
	}

	// OESTE
	private void comandoOeste() {
		juego.outputTexto("Te mueves en dirección oeste.");
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
		juego.outputTexto("Estás jugando MORGATH I (v.1.0.3)");
	}

}
