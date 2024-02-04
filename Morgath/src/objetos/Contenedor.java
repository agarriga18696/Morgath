package objetos;

import java.util.ArrayList;
import java.util.List;

public class Contenedor extends Objeto {

	private List<Objeto> objetosContenidos;
	private Tipo tipo;
	
	public Contenedor(String nombre, String descripcion) {
		super(nombre, descripcion);
		
		this.objetosContenidos = new ArrayList<>();
	}
	
	// Tamaño del contenedor.
	public enum Tipo {
		PEQUENO, MEDIANO, GRANDE;
	}

	public List<Objeto> getObjetosContenidos() {
		return objetosContenidos;
	}

	public void setObjetosContenidos(List<Objeto> objetosContenidos) {
		this.objetosContenidos = objetosContenidos;
	}

}
