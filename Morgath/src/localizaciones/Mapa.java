package localizaciones;

import java.util.HashMap;
import java.util.Map;

public class Mapa {

	Map<Habitacion, Map<Direccion, Habitacion>> mapa;

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
		Habitacion habitacion1 = new Habitacion("Casa", "Habitación 1", "Te encuentras dentro de una casa, en frente hay una puerta.");
		Habitacion habitacion2 = new Habitacion("Casa", "Habitación 2", "Hay un pequeño cofre, y a la derecha hay una puerta.");

		mapa.put(habitacion1, Map.of(Direccion.NORTE, habitacion2, Direccion.SUR, null, Direccion.OESTE, null, Direccion.ESTE, null));
		mapa.put(habitacion2, Map.of(Direccion.NORTE, null, Direccion.SUR, habitacion1, Direccion.OESTE, null, Direccion.ESTE, null));

	}

}