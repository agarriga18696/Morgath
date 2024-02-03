package localizaciones;

import java.util.HashMap;
import java.util.Map;

import objetos.FabricaObjetos;
import objetos.Objeto;

public class Mapa {

	public Habitacion habitacion0;
	public Habitacion habitacion1;
	public Habitacion habitacion2;
	
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
	public void inicializarMapa() {
		habitacion0 = new Habitacion(0, "Camino", "Estás en mitad de un camino pedregoso, es de noche y hace mucho frío, será mejor que te resguardes en algún sitio.");
		habitacion1 = new Habitacion(1, "Sendero", "Estás en mitad de un camino pedregoso, es de noche y hace mucho frío, será mejor que te resguardes en algún sitio.");
		habitacion2 = new Habitacion(2, "Casa", "Estás en la entrada principal de la casa, donde la luz es tenue, el ambiente se percibe húmedo y el distintivo olor a armario antiguo impregna el aire. A primera vista, no observas nada relevante, pero al concentrarte más, notas algo que destella entre la penumbra.");
		Objeto espada = FabricaObjetos.crearEspada();
        habitacion1.agregarObjeto(espada);

		agregarConexion(habitacion0, habitacion1, null);
		agregarConexion(habitacion1, habitacion0, Mapa.Direccion.OESTE);
		agregarConexion(habitacion1, habitacion2, Mapa.Direccion.NORTE);
		agregarConexion(habitacion2, habitacion1, Mapa.Direccion.SUR);
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

	public void agregarConexion(Habitacion habitacionActual, Habitacion habitacionEnDireccion, Mapa.Direccion direccion) {
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