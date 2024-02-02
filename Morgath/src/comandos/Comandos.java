package comandos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import juego.Juego;
import juego.Jugador;
import misiones.Misiones;

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
		AYUDA("A");

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
		jugador.setPuntos(10);
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
			System.exit(0);
		});
	}

	// AYUDA
	private void comandoAyuda() {
		juego.outputTexto("LISTA DE COMANDOS:");
		juego.outputTexto("- NORTE: Te mueves en dirección norte.");
		juego.outputTexto("- SUR: Te mueves en dirección sur.");
		juego.outputTexto("- ESTE: Te mueves en dirección este.");
		juego.outputTexto("- OESTE: Te mueves en dirección oeste.");
		juego.outputTexto("- MISION: Muestra la misión actual.");
		juego.outputTexto("- TERMINAR: Terminar la partida y salir del juego.");
	}

}
