package localizaciones;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Mapa {

	/*
	 * 
	 * 
	 *  DECLARACIÓN DE LAS HABITACIONES
	 *  
	 *  
	 */

	public Habitacion habitacion_00;
	public Habitacion habitacion_01;
	public Habitacion habitacion_02;
	public Habitacion habitacion_02_01;
	public Habitacion habitacion_02_02;
	public Habitacion habitacion_02_03;

	// Declarar mapa.
	public final Map<Habitacion, Map<Direccion, Habitacion>> habitaciones;

	// Constructor
	public Mapa() {
		this.habitaciones = new HashMap<>();
		inicializarHabitaciones();
	}

	// Getter.
	public Map<Habitacion, Map<Direccion, Habitacion>> getMapa(){
		return habitaciones;
	}




	/*
	 * 
	 *  
	 *  
	 *  
	 *  DEFINICIÓN DE DIRECCIONES PARA CONECTAR LAS HABITACIONES
	 *  
	 *  
	 *  
	 *  
	 */

	public enum Direccion {
		NORTE("N"),
		SUR("S"),
		ESTE("E"),
		OESTE("O"),
		NORESTE("NE"),
		NOROESTE("NO"),
		SUDESTE("SE"),
		SUDOESTE("SO"),
		ARRIBA("AR"),
		ABAJO("AB");

		private final String[] atajo;

		// Constructor.
		Direccion(String... atajo) {
			this.atajo = atajo;
		}
		
		// Getter.
		public String[] getAtajo() {
			return atajo;
		}

		// Método para obtener el atajo del comando.
		public static Direccion obtenerAtajo(String atajo) {
		    for (Direccion direccion : values()) {
		        if (direccion.name().equalsIgnoreCase(atajo) || Arrays.asList(direccion.atajo).contains(atajo.toUpperCase())) {
		            return direccion;
		        }
		    }
		    
		    return null;
		    //throw new IllegalArgumentException("Error, atajo " + atajo + " no encontrado.");
		}

		// Método para obtener la dirección opuesta (retorno).
		public Direccion opuesta() {
			switch (this) {
			case NORTE:
				return SUR;
			case SUR:
				return NORTE;
			case OESTE:
				return ESTE;
			case ESTE:
				return OESTE;
			case NORESTE:
				return NOROESTE;
			case NOROESTE:
				return NORESTE;
			case SUDESTE:
				return SUDOESTE;
			case SUDOESTE:
				return SUDESTE;
			case ARRIBA:
				return ABAJO;
			case ABAJO:
				return ARRIBA;
			default:
				throw new IllegalArgumentException("Dirección no válida :(");
			}
		}
	}




	/*
	 * 
	 *  
	 *  
	 *  
	 *  INICIALIZACIÓN DEL MAPA CON TODAS LAS HABITACIONES DEL JUEGO
	 *  
	 *  
	 *  
	 *  
	 */

	public void inicializarHabitaciones() {

		/*
		 * 
		 * CREAR LAS HABITACIONES
		 * 
		 */

		habitacion_00 = Habitaciones.h_00(); // Callejón sin salida.
		habitacion_01 = Habitaciones.h_01(); // Sendero
		habitacion_02 = Habitaciones.h_02(); // Casa, exterior
		habitacion_02_01 = Habitaciones.h_02_01(); // Casa, entrada
		habitacion_02_02 = Habitaciones.h_02_02(); // Casa, ático
		habitacion_02_03 = Habitaciones.h_02_03(); // Casa, sótano



		/*
		 * 
		 * CREAR LAS CONEXIONES ENTRE LAS HABITACIONES
		 * 
		 */

		conectar(habitacion_00, habitacion_01, Direccion.ESTE); // Callejón sin salida.
		conectar(habitacion_01, habitacion_02, Direccion.NORTE); // Sendero
		conectar(habitacion_02, habitacion_02_01, Direccion.NORTE); // Casa, exterior
		conectar(habitacion_02, habitacion_02_01, Direccion.NORTE); // Casa, entrada
		conectar(habitacion_02_01, habitacion_02_02, Direccion.ARRIBA); // Casa, ático
		conectar(habitacion_02_01, habitacion_02_03, Direccion.ABAJO); // Casa, sótano

	}

	// Método para obtener la habitación inicial del mapa.
	public Habitacion obtenerHabitacionInicial() {
		return obtenerHabitacionPorId(0);
	}

	// Método para obtener una habitación del mapa por su ID.
	public Habitacion obtenerHabitacionPorId(int id) {
		for (Habitacion habitacion : habitaciones.keySet()) {
			if (habitacion.getId() == id) {
				return habitacion;
			}
		}

		return null; // Habitación no encontrada
	}

	// Método para conectar las habitaciones entre ellas.
	public void conectar(Habitacion habitacionOrigen, Habitacion habitacionDestino, Direccion direccion) {
		habitacionOrigen.setSalidas(habitaciones.computeIfAbsent(habitacionOrigen, k -> new HashMap<>()));
		habitacionDestino.setSalidas(habitaciones.computeIfAbsent(habitacionDestino, k -> new HashMap<>()));

		habitacionOrigen.getSalidas().put(direccion, habitacionDestino);
		habitacionDestino.getSalidas().put(direccion.opuesta(), habitacionOrigen);
	}

	// Método para mover al jugador por las habitaciones.
	public Habitacion moverJugador(Habitacion habitacionActual, Direccion direccion) {
		Map<Direccion, Habitacion> direcciones = habitaciones.get(habitacionActual);
		if (direcciones != null && direcciones.containsKey(direccion)) {
			return direcciones.get(direccion);
		} else {
			return habitacionActual;
		}
	}

}