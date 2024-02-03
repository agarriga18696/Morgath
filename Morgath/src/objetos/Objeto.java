package objetos;

public class Objeto {
	
	private String nombre;
	private String descripcion;
	private boolean objetoDeMision;
	
	public Objeto(String nombre, String descripcion, boolean objetoDeMision) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.objetoDeMision = objetoDeMision;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean isObjetoDeMision() {
		return objetoDeMision;
	}

}
