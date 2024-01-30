package objetos;

public class Ingrediente extends Objeto {

	private int uso;
	private String origen;
	
	public Ingrediente(String nombre, String rareza, String descripcion, int valor, double peso, boolean consumible, boolean objetoDeMision, int uso, String origen) {
		super(nombre, rareza, descripcion, valor, peso, consumible, objetoDeMision);
		
		this.uso = uso;
		this.origen = origen;
		
	}
	
	public enum Origen {
		
		CUEVA, MONTANA, PANTANO, DESIERTO, PRADERA, BOSQUE, SELVA, RIO, LAGO
		
	}

}
