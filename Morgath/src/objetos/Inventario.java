package objetos;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
	
	private List<Objeto> objetos;

	public Inventario() {
		
		this.objetos = new ArrayList<>();

	}

	// Agregar un objeto al inventario.
	public void agregarObjeto(Objeto objeto) {
		
		objetos.add(objeto);

	}

}
