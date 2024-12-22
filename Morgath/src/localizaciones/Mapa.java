package localizaciones;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utilidades.NormalizarCadena;

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
	 *  DEFINICIÓN DE DIRECCIONES Y SUS ATAJOS PARA REALIZAR LAS CONEXIONES ENTRE HABITACIONES Y PERMITIR EL MOVIMIENTO.
	 *  
	 *  
	 *  
	 *  
	 */

	public enum Direccion {
	    NORTE("NORTE", "N", "RECTO", "RE"),
	    SUR("SUR", "S", "ATRÁS", "AT"),
	    ESTE("ESTE", "E", "DERECHA", "DE"),
	    OESTE("OESTE", "O", "IZQUIERDA", "IZ"),
	    NORESTE("NORESTE", "NE"),
	    NOROESTE("NOROESTE", "NO"),
	    SUDESTE("SUDESTE", "SURESTE", "SE"),
	    SUDOESTE("SUDOESTE", "SUROESTE", "SO"),
	    ARRIBA("ARRIBA", "AR"),
	    ABAJO("ABAJO", "AB");

	    private final String[] atajo;

	    // Constructor.
	    Direccion(String... atajo) {
	    	// Normalizar cada atajo para quitar los acentos.
	        this.atajo = Arrays.stream(atajo)
	        		.map(NormalizarCadena::quitarAcentos) // Normalizar cada atajo.
	        		.toArray(String[]::new);
	    }

	    public String[] getAtajo() {
	        return atajo;
	    }

	    public static Direccion obtenerAtajo(String atajo) {
	        // Normalizar la cadena de entrada para comparar sin acentos.
	        String atajoNormalizado = NormalizarCadena.quitarAcentos(atajo).toUpperCase();

	        for(Direccion direccion : values()) {
	            // Comparar el atajo normalizado con los atajos almacenados normalizados.
	            if(direccion.name().equalsIgnoreCase(atajoNormalizado) || 
	                Arrays.asList(direccion.atajo).stream().anyMatch(atajoStr -> atajoStr.equalsIgnoreCase(atajoNormalizado))) {
	                return direccion;
	            }
	        }

	        return null; // No se encontró el atajo.
	    }

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
		habitacion_02 = Habitaciones.h_02(); // Sur de la casa
		habitacion_02_01 = Habitaciones.h_02_01(); // Casa
		habitacion_02_02 = Habitaciones.h_02_02(); // Casa, ático
		habitacion_02_03 = Habitaciones.h_02_03(); // Casa, sótano



		/*
		 * 
		 * CREAR LAS CONEXIONES ENTRE LAS HABITACIONES
		 * 
		 */

		conectar(habitacion_00, habitacion_01, Direccion.ESTE); // Callejón sin salida -> E -> Sendero
		conectar(habitacion_01, habitacion_02, Direccion.NORTE); // Sendero -> N -> Sur de la casa
		conectar(habitacion_02, habitacion_02_01, Direccion.NORTE); // Sur de la casa -> N -> Casa
		conectar(habitacion_02_01, habitacion_02_02, Direccion.ARRIBA); // Casa -> AR -> Ático
		conectar(habitacion_02_01, habitacion_02_03, Direccion.ABAJO); // Casa -> AB -> Sótano

	}

	// Método para obtener la habitación inicial del mapa.
	public Habitacion obtenerHabitacionInicial() {
		return obtenerHabitacionPorId(0);
	}

	// Método para obtener una habitación del mapa por su ID.
	public Habitacion obtenerHabitacionPorId(int id) {
		for(Habitacion habitacion : habitaciones.keySet()) {
			if(habitacion.getId() == id) {
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
		if(direcciones != null && direcciones.containsKey(direccion)) {
			return direcciones.get(direccion);
		} else {
			return habitacionActual;
		}
	}

}