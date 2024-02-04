package localizaciones;

import java.util.HashMap;
import java.util.Map;

import objetos.FabricaObjetos;
import objetos.Objeto;

public class Mapa {

	public Habitacion habitacion_00;
	public Habitacion habitacion_01;
	public Habitacion habitacion_02;

	public Map<Habitacion, Map<Direccion, Habitacion>> mapa;

    public Mapa() {
        this.mapa = new HashMap<>();
        inicializarMapa();
    }
    
    public Map<Habitacion, Map<Direccion, Habitacion>> getMapa(){
		return mapa;
	}

	public enum Direccion {
		NORTE,
		SUR,
		OESTE,
		ESTE;

		// Método para obtener la dirección opuesta.
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
			default:
				throw new IllegalArgumentException("Dirección no válida :(");
			}
		}
	}

	// Método para inicializar el mapa.
	public void inicializarMapa() {
		// Crear habitaciones.
		habitacion_00 = new Habitacion(0, "Callejón sin salida", "Estás en mitad de un sendero sin salida, al este hay un sendero que parece ser la única ruta de escape.");
		habitacion_01 = new Habitacion(1, "Sendero", "El sendero es muy estrecho, parece desviarse al norte hacia una vieja casa de campo. ¿Estará abandonada? Tal vez sea un buen sitio para empezar a buscar.");
		habitacion_02 = new Habitacion(2, "Casa: entrada", "Estás en la entrada principal de la casa, donde la luz es tenue, el ambiente se percibe húmedo y el distintivo olor a armario antiguo impregna el aire. A primera vista, no observas nada relevante, pero al concentrarte más, notas algo que destella entre la penumbra.");
		
		// Agregar conexiones.
		conectar(habitacion_00, habitacion_01, Direccion.ESTE);
		conectar(habitacion_01, habitacion_02, Direccion.NORTE);
		
		// Agregar objetos a las habitaciones.
		Objeto espada = FabricaObjetos.crearEspada();
		Objeto lampara = FabricaObjetos.crearLampara();
		Objeto bolsaMonedas = FabricaObjetos.crearBolsaMonedas();
		
		habitacion_00.agregarObjeto(lampara);
		habitacion_02.agregarObjeto(bolsaMonedas);
		habitacion_02.agregarObjeto(espada);
		
	}

	// Método para obtener la habitación inicial del mapa.
	public Habitacion obtenerHabitacionInicial() {
		return obtenerHabitacionPorId(0);
	}

	// Método para obtener una habitación del mapa por su ID.
	public Habitacion obtenerHabitacionPorId(int id) {
		for (Habitacion habitacion : mapa.keySet()) {
			if (habitacion.getId() == id) {
				return habitacion;
			}
		}

		return null; // Habitación no encontrada
	}

	// Método para conectar las habitaciones entre ellas.
	public void conectar(Habitacion habitacionOrigen, Habitacion habitacionDestino, Direccion direccion) {
	    habitacionOrigen.setSalidas(mapa.computeIfAbsent(habitacionOrigen, k -> new HashMap<>()));
	    habitacionDestino.setSalidas(mapa.computeIfAbsent(habitacionDestino, k -> new HashMap<>()));

	    habitacionOrigen.getSalidas().put(direccion, habitacionDestino);
	    habitacionDestino.getSalidas().put(direccion.opuesta(), habitacionOrigen);
	}

	// Método para mover al jugador por las habitaciones.
	public Habitacion moverJugador(Habitacion habitacionActual, Direccion direccion) {
		Map<Direccion, Habitacion> direcciones = mapa.get(habitacionActual);
		if (direcciones != null && direcciones.containsKey(direccion)) {
			return direcciones.get(direccion);
		} else {
			return habitacionActual;
		}
	}

}