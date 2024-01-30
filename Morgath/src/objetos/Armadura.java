package objetos;

public class Armadura extends Objeto {

	private String tipo;
	private int defensa;
	private int durabilidad;
	private boolean estaEncantada;
	private String encantamiento;
	
	public Armadura(String nombre, String rareza, String descripcion, int valor, double peso, boolean consumible, boolean objetoDeMision, String tipo, int defensa, int durabilidad, boolean estaEncantada, String encantamiento) {
		super(nombre, rareza, descripcion, valor, peso, consumible, objetoDeMision);
		
		this.tipo = tipo;
		this.defensa = defensa;
		this.durabilidad = durabilidad;
		this.estaEncantada = estaEncantada;
		this.encantamiento = encantamiento;
		
	}
	
	public enum Tipo {
		
		TELA, RIGIDA
		
	}

}
