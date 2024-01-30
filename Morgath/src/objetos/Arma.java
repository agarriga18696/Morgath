package objetos;

public class Arma extends Objeto {

	private String tipo;
	private int ataque;
	private String alcance;
	private int durabilidad;
	private boolean estaEncantada;
	private String encantamiento;

	public Arma(String nombre, String rareza, String descripcion, int valor, double peso, boolean consumible, boolean objetoDeMision, String tipo, int ataque, String alcance, int durabilidad, boolean estaEncantada, String encantamiento) {
		super(nombre, rareza, descripcion, valor, peso, consumible, objetoDeMision);

		this.tipo = tipo;
		this.ataque = ataque;
		this.alcance = alcance;
		this.durabilidad = durabilidad;
		this.estaEncantada = estaEncantada;
		this.encantamiento = encantamiento;

	}
	
	public enum Tipo {
		
		FILO, HACHA, ROMA, ASTA, DISTANCIA
		
	}
	
	public enum Alcance {
		
		MUY_CORTO, CORTO, MEDIO, LARGO, MUY_LARGO
		
	}

}
