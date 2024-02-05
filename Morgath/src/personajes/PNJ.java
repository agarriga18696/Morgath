package personajes;

import java.util.ArrayList;
import java.util.Arrays;

import objetos.Objeto;


public class PNJ extends Personaje {

	private String conversacion;

	public PNJ(String nombre, String conversacion, int vidas, Objeto... objeto) {
		super(nombre, vidas);
		
		this.nombre = nombre;
		this.conversacion = conversacion;
		this.inventario = new ArrayList<>(Arrays.asList(objeto));
	}
	
	// Getters i setters.
	public String getConversacion() {
		return conversacion;
	}

	public void setConversacion(String conversacion) {
		this.conversacion = conversacion;
	}

}
