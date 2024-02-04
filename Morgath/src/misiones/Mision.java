package misiones;

import java.util.List;

import juego.Juego;
import localizaciones.Habitacion;
import objetos.Objeto;
import personajes.Jugador;

public class Mision {

	private String nombre;
	private String objetivo;
	private String mensaje;
	private int recompensa;
	private boolean activada;
	private boolean completada;
	private boolean mensajeMostrado;

	public Mision(String nombre, String objetivo, String mensaje, int recompensa) {
		this.nombre = nombre;
		this.objetivo = objetivo;
		this.mensaje = mensaje;
		this.recompensa = recompensa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getRecompensa() {
		return recompensa;
	}

	public void setRecompensa(int recompensa) {
		this.recompensa = recompensa;
	}

	public boolean isActivada() {
		return activada;
	}

	public void setActivada(boolean activada) {
		this.activada = activada;
	}

	public boolean isCompletada() {
		return completada;
	}

	public void setCompletada(boolean completada) {
		this.completada = completada;
	}

	public boolean isMensajeMostrado() {
		return mensajeMostrado;
	}

	public void setMensajeMostrado(boolean mensajeMostrado) {
		this.mensajeMostrado = mensajeMostrado;
	}

	// Método para finalizar una mision.
	public void finalizarMision() {
		this.setCompletada(true);
		this.setActivada(false);
	}

	// Método para verificar las condiciones específicas de finalización de la misión.
	public boolean verificarCondicionesEspecificas(Jugador jugador) {
		switch (nombre.toUpperCase()) {
		case "1, INICIACIÓN":
			System.out.println("Intenta verificar mision 1");
			return condicionesMision1();
		case "2, DESPERTAR":
			System.out.println("Intenta verificar mision 2");
			return condicionesMision2(jugador);
			// Resto de misiones.
		default:
			return false;
		}
	}

	public boolean condicionesMision1() {
	    if (Juego.ultimoComandoUsado != null && Juego.ultimoComandoUsado.equalsIgnoreCase("empezar")) {
	        // Resto del código
	    	System.out.println("Completada mision 1");
	        return true;
	    }
	    return false;
	}

	private boolean condicionesMision2(Jugador jugador) {
		// Condición de finalización: coger espada de la habitación "Casa: entrada".
		// - Objeto: espada
		// - Habitación: Casa: entrada

		String nombreHabitacionMision = "Casa: entrada";
		String nombreObjetoMision = "Espada";

		// Obtener la ubicación actual del jugador y su inventario.
		Habitacion ubicacionJugador = jugador.getUbicacion();
		List<Objeto> inventarioJugador = jugador.getInventario();

		// Verificar que el jugador está en la habitación "Casa: entrada".
		if (ubicacionJugador != null && ubicacionJugador.getNombre().equalsIgnoreCase("casa: entrada")) {
			// Verificar que el jugador tiene la espada específica en su inventario.
			for (Objeto objetoJugador : inventarioJugador) {
				if (objetoJugador != null && objetoJugador.getNombre().equalsIgnoreCase("espada")) {
					return true;
				}
			}
		}

		return false;
	}


}
