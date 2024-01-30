package principal;

import jugador.Jugador;

public class Juego {

	private boolean enEjecucion;
	private Jugador jugador;

	public Juego() {

		enEjecucion = true;
		jugador = new Jugador(null, null, null);

	}

	public boolean enEjecucion() {
		return enEjecucion;
	}

}
