package objetos;

import java.util.ArrayList;
import java.util.List;

public class Objeto_Contenedor extends Objeto {

	private List<Objeto> objetosContenidos;
	private Capacidad capacidad;
	private int maxObjetosContenidos;

	public Objeto_Contenedor(String icono, String nombre, String descripcion, Capacidad capacidad, List<Objeto> objetosContenidos) {
		super(icono, nombre, descripcion);

		this.capacidad = capacidad;

		if(objetosContenidos != null) {
			this.objetosContenidos = new ArrayList<>(objetosContenidos);
		} else {
			this.objetosContenidos = new ArrayList<>();
		}

		// Capacidad del contenedor.
		switch(capacidad) {
		case BAJA:
			this.maxObjetosContenidos = 4;
			break;
		case MEDIA:
			this.maxObjetosContenidos = 7;
			break;
		case ALTA:
			this.maxObjetosContenidos = 10;
			break;
		default:
			break;
		}

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

	public Capacidad getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Capacidad capacidad) {
		this.capacidad = capacidad;
	}

	public int getMaxObjetosContenidos() {
		return maxObjetosContenidos;
	}


}
