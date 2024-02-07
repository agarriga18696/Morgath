package personajes;

import java.util.ArrayList;
import java.util.Arrays;

import objetos.Objeto;
import utilidades.Aleatorio;


public class PNJ extends Personaje implements Conversar {

	protected String conversacion;
	protected boolean estaMolesto;
	protected boolean estaHalagado;
	
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
	public String saludoNormal() {
		String[] saludo = {
				"Saludos, viajero. ", 
				"Hola. ", 
				"¡Saludos, forastero! "};
		
		return this.nombre.toUpperCase() + ": " + saludo[Aleatorio.Int(0, saludo.length - 1)];
	}

	@Override
	public String saludoGrosero() {
		String[] saludo = {
				"Si quieres algo dáte prisa, tengo quehaceres importantes. ", 
				"(Gruñido) ¿Y tú que quieres? ", 
				"No tengo tiempo para estupideces. ",
				"No me interesa nada que puedas ofrecerme. "};
		
		return this.nombre.toUpperCase() + ": " + saludo[Aleatorio.Int(0, saludo.length - 1)];
	}

	@Override
	public String saludoAmigable() {
		String[] saludo = {
				"Ah, eres tú. Pensaba que eras uno de esos bandidos. ¿Qué se te ofrece, amigo?", 
				"Nunca olvidaré tus palabras, ¿qué necesitas, amigo?", 
				"¡Bienaventurado seas, amigo! Si necesitas ayuda, no dudes en preguntarme.",
				"¡Que los dioses te protegan! ¿Qué puedo ofrecerte?"};
		
		return this.nombre.toUpperCase() + ": " + saludo[Aleatorio.Int(0, saludo.length - 1)];
	}

}
