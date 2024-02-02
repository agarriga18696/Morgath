package objetos;

public abstract class Objeto {
	
	private String nombre;
	private String descripcion;
	private boolean objetoDeMision;
	
	public Objeto(String nombre, String descripcion, boolean objetoDeMision) {
		
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.objetoDeMision = objetoDeMision;
		
	}

}
