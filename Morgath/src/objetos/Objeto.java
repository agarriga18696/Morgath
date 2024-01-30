package objetos;

public abstract class Objeto {
	
	private String nombre;
	private String rareza;
	private String descripcion;
	private int valor;
	private double peso;
	private boolean consumible;
	private boolean objetoDeMision;
	
	public Objeto(String nombre, String rareza, String descripcion, int valor, double peso, boolean consumible, boolean objetoDeMision) {
		
		this.nombre = nombre;
		this.rareza = rareza;
		this.descripcion = descripcion;
		this.valor = valor;
		this.peso = peso;
		this.consumible = consumible;
		this.objetoDeMision = objetoDeMision;
		
	}
	
	protected enum Rareza {
		
		COMUN, POCO_COMUN, RARO, MUY_RARO, EPICO, MITICO, LEGENDARIO
		
	}

}
