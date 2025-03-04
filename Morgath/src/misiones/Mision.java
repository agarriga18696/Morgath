package misiones;

import java.util.List;

import comandos.Comandos;
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
		return nombre.toUpperCase();
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
		switch(nombre.toUpperCase()) {
		case "1, DESPERTAR":
			return condicionesMision_1_0();
		case "1-1, DESPERTAR":
			return condicionesMision_1_1(jugador);
		case "2, PRUEBA":
			return condicionesMision_2_0(jugador);
			// Resto de misiones.
		default:
			return false;
		}
	}


	///////////////////////////////////////////////
	//											 //
	//	CONDICIONES PARA FINALIZAR LAS MISIONES  //
	//											 //
	///////////////////////////////////////////////

	/*
	 * 
	 * 
	 *  CONDICIÓN MISIÓN 0
	 *  
	 *  
	 */

	public boolean condicionesMision_1_0() {
		// Condición: Ponerte en pie con el comando 'LEVANTARSE'.

		if(Juego.ultimoComandoUsado != null && !Juego.ultimoComandoUsado.trim().isEmpty()) {
			try {
				Comandos.ListaComandos comandoUsado = Comandos.ListaComandos.obtenerAtajo(Juego.ultimoComandoUsado);
				if(comandoUsado == Comandos.ListaComandos.LEVANTARSE) {
					return true;
				}

			} catch(IllegalArgumentException e) {
				// Comando no encontrado.
				e.printStackTrace();
			}
		}

		return false;
	}



	/*
	 * 
	 * 
	 *  CONDICIÓN MISIÓN 1: DESPERTAR
	 *  
	 *  
	 */

	private boolean condicionesMision_1_1(Jugador jugador) {
		// TODO: Condición: Coger la lámpara de la habitación 'Callejón sin salida'.
		// TODO: - Objeto: lámpara -> Habitación: Callejón sin salida

		String objetoMision[] = {"bolsa"};
		String habitacionMision[] = {"Ático"};

		// Obtener la ubicación actual del jugador y su inventario.
		Habitacion ubicacionJugador = jugador.getUbicacion();
		List<Objeto> inventarioJugador = jugador.getInventario();
		int contadorObjetos = 0;

		// Verificar que el jugador esté en pie.
		if(jugador.isDePie()) {
			// Verificar que el jugador está en la habitación correcta.
			for(int i = 0; i < habitacionMision.length; i++) {
				if(ubicacionJugador != null && ubicacionJugador.getNombre().equalsIgnoreCase(habitacionMision[i])) {
					// Verificar que el jugador tiene objetos en su inventario.
					if(!inventarioJugador.isEmpty()) {
						for(Objeto objetoJugador : inventarioJugador) {
							for(int j = 0; j < objetoMision.length; j++) {
								// Verificar que haya encontrado todos los objetos de la misión.
								if(objetoJugador != null && objetoJugador.getNombre().equalsIgnoreCase(objetoMision[j])) {
									contadorObjetos++;			
									if(contadorObjetos == objetoMision.length) {
										return true;
									}
								}
							} // tercer for
						} // segundo for
					}
				}
			} // primer for
		}

		return false;
	}

	/*
	 * 
	 * 
	 *  CONDICIÓN MISIÓN 1-1: DESPERTAR
	 *  
	 *  
	 */

	private boolean condicionesMision_2_0(Jugador jugador) {
		// Condición: coger bolsa y espada de las habitaciones 'Ático' y 'Sótano'.
		// - Objeto: bolsa -> Habitación: Ático
		// - Objeto: espada -> Habitación: Sótano

		String objetoMision[] = {"bolsa", "espada corta"};
		String habitacionMision[] = {"Ático", "Sótano"};

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
