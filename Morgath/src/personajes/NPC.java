package personajes;

import java.util.ArrayList;

import localizaciones.Habitacion;

public class NPC extends Personaje {

	private String conversacion;
	
	public NPC(String nombre, Habitacion ubicacion, String conversacion, int vidas) {
		super(nombre, ubicacion, vidas);
		this.inventario = new ArrayList<>();
		this.nombre = nombre;
		this.conversacion = conversacion;
	}
	
	// Getters i setters.
	
	
}
