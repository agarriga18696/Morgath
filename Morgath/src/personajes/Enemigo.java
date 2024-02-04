package personajes;

import localizaciones.Habitacion;

public class Enemigo extends Personaje {

	private boolean esJefe;
	private int recompensaPorMatar;
	
	public Enemigo(String nombre, Habitacion ubicacion, boolean esJefe, int recompensaPorMatar, int vidas) {
		super(nombre, ubicacion, vidas);
		
		this.esJefe = esJefe;
		this.recompensaPorMatar = recompensaPorMatar;
	}

}
