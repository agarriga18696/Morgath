package localizaciones;

public class Habitacion {

	// Atributos.
	private String tipo;
	private String nombre;
	private String descripcion;

	// Constructor.
	public Habitacion(String tipo, String nombre, String descripcion) {
		
		this.tipo = tipo;
		this.nombre = nombre;	
		this.descripcion = descripcion;

	}

	// Getters i setters.
	public String getTipo() {
		return tipo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

}
