package personajes;

import java.util.ArrayList;

import localizaciones.Habitacion;

public class NPC extends Personaje {

	private String nombre;
	private String conversacion;
	private boolean inmortal;
	
	public NPC(Habitacion ubicacion, String nombre, String conversacion, boolean inmortal) {
		super(ubicacion);
		this.ubicacion = ubicacion;
		this.inventario = new ArrayList<>();
		this.nombre = nombre;
		this.conversacion = conversacion;
		this.inmortal = inmortal;
	}
	
	// Getters i setters.
	
	
}
