package objetos;

import java.util.ArrayList;
import java.util.List;

public class Objeto_Contenedor extends Objeto {

	private List<Objeto> objetosContenidos;
	private Capacidad capacidad;
	private int maxObjetosContenidos;

	public Objeto_Contenedor(String icono, String nombre, String descripcion, Rareza rareza, boolean deMision, Integer valorVenta, Capacidad capacidad, List<Objeto> objetosContenidos) {
		super(icono, nombre, descripcion, rareza, deMision, valorVenta);

		this.capacidad = capacidad;

		if(objetosContenidos != null) {
			this.objetosContenidos = new ArrayList<>(objetosContenidos);
		} else {
			this.objetosContenidos = new ArrayList<>();
		}

		// Capacidad del contenedor.
		switch(capacidad) {
		case BAJA:
			this.maxObjetosContenidos = 2;
			break;
		case MEDIA:
			this.maxObjetosContenidos = 4;
			break;
		case ALTA:
			this.maxObjetosContenidos = 7;
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
