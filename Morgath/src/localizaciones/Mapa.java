package localizaciones;

import java.util.HashMap;
import java.util.Map;

import objetos.FabricaObjetos;
import objetos.Objeto;

public class Mapa {

	public Map<Habitacion, Map<Direccion, Habitacion>> mapa;

	public Mapa() {
		this.mapa = new HashMap<>();
		inicializarMapa();
	}

	public enum Direccion {
		NORTE,
		SUR,
		OESTE,
		ESTE
	}

	// Crear las conexiones con todas las habitaciones del juego.
	private void inicializarMapa() {
		Habitacion habitacion0 = new Habitacion(0, "Casa", "Te encuentras dentro de una casa, en frente hay una puerta que da a la cocina.");
		Objeto espada = FabricaObjetos.crearEspada();
        habitacion0.agregarObjeto(espada);
		
		Habitacion habitacion1 = new Habitacion(1, "Cocina", "Hay un pequeño arcón, y a la derecha hay una puerta.");

		agregarConexion(habitacion0, habitacion1, Mapa.Direccion.NORTE);
		agregarConexion(habitacion1, habitacion0, Mapa.Direccion.SUR);
	}

	// Método para obtener la habitación inicial del mapa
	public Habitacion obtenerHabitacionInicial() {
		// En este ejemplo, asumimos que la habitación inicial tiene el ID 0
		return obtenerHabitacionPorId(0);
	}

	// Método para obtener una habitación del mapa por su ID
	public Habitacion obtenerHabitacionPorId(int id) {
		for (Habitacion habitacion : mapa.keySet()) {
			if (habitacion.getId() == id) {
				return habitacion;
			}
		}
		
		return null; // Habitación no encontrada
	}

	private void agregarConexion(Habitacion habitacionActual, Habitacion habitacionEnDireccion, Mapa.Direccion direccion) {
		Map<Mapa.Direccion, Habitacion> direcciones = new HashMap<>();
		direcciones.put(direccion, habitacionEnDireccion);
		mapa.put(habitacionActual, direcciones);
	}

	public Habitacion moverse(Habitacion habitacionActual, Mapa.Direccion direccion) {
		Map<Mapa.Direccion, Habitacion> direcciones = mapa.get(habitacionActual);
		if (direcciones != null && direcciones.containsKey(direccion)) {
			Habitacion nuevaHabitacion = direcciones.get(direccion);
			//System.out.println("Te has movido en dirección " + direccion + " a la habitación " + nuevaHabitacion.getNombre());
			return nuevaHabitacion;
		} else {
			//System.out.println("No hay conexión en la dirección " + direccion + " desde la habitación actual.");
			return habitacionActual;
		}
	}

}