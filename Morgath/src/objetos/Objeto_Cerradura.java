package objetos;

import java.util.ArrayList;
import java.util.List;

public class Objeto_Cerradura extends Objeto {
	
	private boolean cerrado;
	private List<Objeto> objetosContenidos;

	public Objeto_Cerradura(String icono, String nombre, String descripcion, Rareza rareza, boolean deMision, Integer valorVenta, boolean cerrado, List<Objeto> objetosContenidos) {
		super(icono, nombre, descripcion, rareza, deMision, valorVenta);
		
		this.cerrado = cerrado;
		this.rareza = Rareza.COMUN;
		
		if(objetosContenidos != null) {
			this.objetosContenidos = new ArrayList<>(objetosContenidos);
		} else {
			this.objetosContenidos = new ArrayList<>();
		}
	}

	public boolean isCerrado() {
		return cerrado;
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}
	
	public List<Objeto> getObjetosContenidos() {
		return objetosContenidos;
	}

	public void setObjetosContenidos(List<Objeto> objetosContenidos) {
		this.objetosContenidos = objetosContenidos;
	}
	
}
