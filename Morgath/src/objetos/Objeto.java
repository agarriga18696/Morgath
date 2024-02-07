package objetos;

public abstract class Objeto {
	
	private String nombre;
	private String descripcion;
	
	public Objeto(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre.toUpperCase();
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
