package objetos;

import java.util.ArrayList;
import java.util.List;

public class Objeto_Contenedor extends Objeto {

	private List<Objeto> objetosContenidos;
	private Capacidad capacidad;
	
	public Objeto_Contenedor(String nombre, String descripcion, Capacidad capacidad) {
		super(nombre, descripcion);
		
		this.objetosContenidos = new ArrayList<>();
		this.capacidad = capacidad;
	}
	
	// Tamaño del contenedor.
	public enum Capacidad {
		BAJA, MEDIA, ALTA;
	}

	public List<Objeto> getObjetosContenidos() {
		return objetosContenidos;
	}

	public void setObjetosContenidos(List<Objeto> objetosContenidos) {
		this.objetosContenidos = objetosContenidos;
	}

}
