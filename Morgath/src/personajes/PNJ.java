package personajes;

import java.util.ArrayList;
import java.util.Arrays;

import objetos.Objeto;
import utilidades.Aleatorio;


public class PNJ extends Personaje implements Conversar {

	private String conversacion;
	private TipoPNJ tipo;
	
	private boolean estaMolesto;
	private boolean estaHalagado;
	
	public PNJ(String nombre, String conversacion, TipoPNJ tipo, int vidas, Objeto... objeto) {
		super(nombre, vidas);
		
		this.nombre = nombre;
		this.conversacion = conversacion;
		this.tipo = tipo;
		this.inventario = new ArrayList<>(Arrays.asList(objeto));
	}
	
	public enum TipoPNJ {
		ALDEANO,
		COMERCIANTE,
		GUARDIA,
		SACERDOTE,
		DRUIDA
	}
	
	public TipoPNJ getTipo() {
		return this.tipo;
	}

	@Override
	public String obtenerConversacion() {
		return this.conversacion;
	}
	
	/*
	 * 
	 * SALUDOS
	 * 
	 */

	@Override
	public String saludoNormal() {
		String[] texto = {
				"Saludos, viajero.", 
				"Hola.", 
				"¡Saludos, forastero!"};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}

	@Override
	public String saludoGrosero() {
		String[] texto = {
				"Si quieres algo dáte prisa, tengo quehaceres importantes.", 
				"(Gruñido) ¿Y tú que quieres?", 
				"No tengo tiempo para estupideces.",
				"No me interesa nada que puedas ofrecerme."};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}

	@Override
	public String saludoAmigable() {
		String[] texto = {
				"Ah, eres tú. Pensaba que eras uno de esos bandidos. ¿Qué se te ofrece, amigo?", 
				"Nunca olvidaré tus palabras, ¿qué necesitas, amigo?", 
				"¡Bienaventurado seas, amigo! Si necesitas ayuda, no dudes en preguntarme.",
				"¡Que los dioses te protegan! ¿Qué puedo ofrecerte?"};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}
	
	
	/*
	 * 
	 * DESPEDIDAS
	 * 
	 */
	
	@Override
	public String despedidaNormal() {
		String[] texto = {
				"¡Hasta pronto!", 
				"¡Adiós!", 
				"¡Hasta la vista, viajero!"};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}
	
	@Override
	public String despedidaAmigable() {
		String[] texto = {
				"Que tengas un buen viaje, viajero.", 
				"", 
				""};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}

	@Override
	public String despedidaGrosera() {
		String[] texto = {
				"Que tengas un buen viaje, viajero.", 
				"", 
				""};
		
		return texto[Aleatorio.Int(0, texto.length - 1)];
	}

}
