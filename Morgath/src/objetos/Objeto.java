package objetos;

import java.util.UUID;

public abstract class Objeto {

	private String id;
	private String nombre;
	private String descripcion;
	
	public Objeto(String nombre, String descripcion) {
		this.id = UUID.nameUUIDFromBytes(nombre.getBytes()).toString().replace("-", "");
		this.nombre = nombre;
		this.descripcion = descripcion;
		
		//System.out.println("Nombre:\t" + this.nombre + "\t\tId:\t" + this.id);
	}

	public String getId() {
		return id;
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
