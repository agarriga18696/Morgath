package objetos;

import java.util.ArrayList;
import java.util.List;

public class Objeto_Cerradura extends Objeto implements Cerradura {
	
	private boolean cerrado;
	private List<Objeto> objetosContenidos;

	public Objeto_Cerradura(String nombre, String descripcion, boolean cerrado, List<Objeto> objetosContenidos) {
		super(nombre, descripcion);
		
		this.cerrado = cerrado;
		
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

	
	@Override
	public void abrir(Objeto_Llave llave) {
		
		if(llave.getIdObjetoVinculado().equalsIgnoreCase(this.getId())) {
			this.setCerrado(false);
		}
		
	}
	
}
