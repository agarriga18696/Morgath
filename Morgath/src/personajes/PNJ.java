package personajes;

import java.util.ArrayList;
import java.util.Arrays;

import objetos.Objeto;


public class PNJ extends Personaje implements Conversar {

	private String conversacion;
	
	public PNJ(String nombre, String conversacion, int vidas, Objeto... objeto) {
		super(nombre, vidas);
		
		this.nombre = nombre;
		this.conversacion = conversacion;
		this.inventario = new ArrayList<>(Arrays.asList(objeto));
	}

	
	@Override
	public String obtenerConversacion() {
		return this.conversacion;
	}

	@Override
	public void establecerConversacion(String nuevaConversacion) {
		this.conversacion = nuevaConversacion;
	}

}
