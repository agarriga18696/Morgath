package localizaciones;

import java.util.ArrayList;
import java.util.List;

import objetos.Objeto;

public class Habitacion {

	// Atributos.
	private int id;
	private String nombre;
	private String descripcion;
	private List<Objeto> objetos;

	// Constructor.
	public Habitacion(int id, String nombre, String descripcion) {
		
		this.objetos = new ArrayList<>();
		this.id = id;
		this.nombre = nombre;	
		this.descripcion = descripcion;

	}

	// Getters i setters.
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Objeto> getObjetos() {
        return objetos;
    }
	
	// Método para agregar un objeto a la habitación.
	public void agregarObjeto(Objeto objeto) {
		objetos.add(objeto);
	}
	

}
