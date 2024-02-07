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



	/*
	 * 
	 *  
	 *  
	 *  
	 *  OTROS MÉTODOS
	 *  
	 *  
	 *  
	 *  
	 */

	// Método para finalizar una mision.
	public void finalizarMision(Jugador jugador) {
		this.setCompletada(true);
		this.setActivada(false);
		// Agregar la misión al diario de misiones.
		jugador.setDiario(this);
	}



	/*
	 * 
	 * 
	 *  VERIFICAR SI LA MISIÓN HA CUMPLIDO SUS OBJETIVOS
	 *  
	 *  
	 */

	public boolean verificarCondicionesEspecificas(Jugador jugador) {
		switch (nombre.toUpperCase()) {
		case "0, INICIACIÓN":
			return condicionesMision0();
		case "1, DESPERTAR":
			return condicionesMision1(jugador);
			// Resto de misiones.
		default:
			return false;
		}
	}




	/*
	 * 
	 * 
	 * 
	 * 
	 * CONDICIONES PARA FINALIZAR LAS MISIONES
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * 
	 * 
	 *  CONDICIÓN MISIÓN 1
	 *  
	 *  
	 */

	public boolean condicionesMision0() {
		// Condición: escribir el comando 'EMPEZAR'.

		if (Juego.ultimoComandoUsado != null && Juego.ultimoComandoUsado.equalsIgnoreCase("empezar")) {
			return true;
		}

		return false;
	}



	/*
	 * 
	 * 
	 *  CONDICIÓN MISIÓN 2
	 *  
	 *  
	 */

	private boolean condicionesMision1(Jugador jugador) {
		// Condición: coger espada de la habitación "Casa: entrada".
		// - Objeto: espada -> Habitación: Casa: ático
		// - Objeto: bolsa -> Habitación: Casa: sótano

		String habitacionMision[] = {"casa, ático", "casa, sótano"};
		String objetoMision[] = {"lámpara", "espada"};

		// Obtener la ubicación actual del jugador y su inventario.
		Habitacion ubicacionJugador = jugador.getUbicacion();
		List<Objeto> inventarioJugador = jugador.getInventario();
		int contadorObjetos = 0;

		// Verificar que el jugador está en la habitación correcta.
		for(int i = 0; i < habitacionMision.length; i++) {
			if (ubicacionJugador != null && ubicacionJugador.getNombre().equalsIgnoreCase(habitacionMision[i])) {
				// Verificar que el jugador tiene la espada específica en su inventario.
				for (Objeto objetoJugador : inventarioJugador) {
					for(int j = 0; j < objetoMision.length; j++) {
						// Verificar que haya encontrado todos los objetos de la misión.
						if (objetoJugador != null && objetoJugador.getNombre().equalsIgnoreCase(objetoMision[j])) {
							contadorObjetos++;			
							if(contadorObjetos == objetoMision.length) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}


}
